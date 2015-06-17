/*
 * Created on May 20, 2015
 *
 * $HeadURL$
 * $Date: May 20, 2015 $
 * $Author: liang.guisheng $
 * $Revision: 1.1 $
 */
package br.com.riskoffice.funds.ui.web.report.express;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.riskoffice.calendar.CalendarTools;
import br.com.riskoffice.client.report.ReportExport;
import br.com.riskoffice.entities.Book;
import br.com.riskoffice.entities.Fund;
import br.com.riskoffice.entities.User;
import br.com.riskoffice.funds.entities.FundDataSource;
import br.com.riskoffice.performance.period.entities.DefaultVariablePeriod;
import br.com.riskoffice.performance.rofs.entities.RofsBookCategory;
import br.com.riskoffice.risk.report.ReportDirectories;
import br.com.riskoffice.risk.report.excel.ReportFTPTransfer;
import br.com.riskoffice.risk.report.excel.ReportFTPTransferBean;
import br.com.riskoffice.risk.riskfactors.entities.RiskFactor;
import br.com.riskoffice.rosys.util.RosysJndi;
import br.com.riskoffice.ui.web.RosysDownloadHelper;
import br.com.riskoffice.ws.reports.stubs.Priority;
import br.com.riskoffice.ws.reports.stubs.ReportType;

/**
 * class used to generate or download fund's "Lâmina" report file
 * 
 * @author liang.guisheng (Last Modified By: $Author: liang.guisheng $)
 *
 * @version $Id: org.eclipse.jdt.ui.prefs,v 1.1 May 20, 2015 liang.guisheng Exp
 *          $
 */
@Name("fundExpressReportAction")
@Scope(ScopeType.EVENT)
public class FundExpressReportAction implements Serializable {

	private static final long serialVersionUID = 1585660783624216988L;

	@In(create = true)
	private ReportExport reportExport;

	@In(required = true)
	private EntityManager seamEm;

	@In
	private User currentUser;

	// default configurations
	private final static DefaultVariablePeriod PERIOD_TYPE = DefaultVariablePeriod.SIX_MONTHS;

	private final static ReportDirectories ftpDir = ReportDirectories.REPORT_FUND_EXPRESS;

	private final static FundDataSource SOURCE = FundDataSource.ANBID;

	private final static ReportType REPORT_TYPE = ReportType.PDF;

	private final static Priority PRIORITY = Priority.FAST;

	private final static String BENCHMARK_SYMBOL = "CDI";

	private final FundExpressReportXmlProcessor processor = new FundExpressReportXmlProcessor();

	private final ReportFTPTransfer ftp = RosysJndi.lookup(ReportFTPTransferBean.class);

	private final CalendarTools calendar = CalendarTools.getInstance();

	private Date reportDate;

// -------------------------------------------------------
	/**
	 * download report file if exists, or create it
	 * 
	 */
	public void download(Fund fund) {

		reportDate = getReportDate(fund);

		if (reportDate == null) {
			return;
		}

		exportPDFIfExistInFTP(fund);

		createPDFIfNotExistInFTP(fund);
	}

	/**
	 * download report pdf file if it exists in FTP server
	 * 
	 * @param fund
	 */
	private void exportPDFIfExistInFTP(Book fund) {

		byte[] contents = ftp.fetchReport(fund, ftpDir.getValue(), reportDate);

		if (contents != null && contents.length > 1) {
			RosysDownloadHelper.exportPdf(contents, getReportDownloadFileName(fund) + ".pdf");
			return;
		}
	}

	/**
	 * create report pdf file if it does not exist in FTP server
	 * 
	 * @param fund
	 */
	private void createPDFIfNotExistInFTP(Book fund) {

		FundExpressReport report = createFundExpressReport(fund);
		processor.setReport(report);

		if (!processor.validDownload() || processor.isProcessing()) {
			return;
		}

		int reportedGeneratedId = schedule(processor, fund, report);
		processor.setRepGeneratedId(reportedGeneratedId);
	}

	/**
	 * return an appropriate date for "Lâmina" report
	 * 
	 * 1. using the last working date of last month if it exists in
	 * RofsBookCategory
	 * 
	 * 2. or use the date in RofsBookCategory which is the closest date to last
	 * working date of last month
	 * 
	 */
	private Date getReportDate(Book fund) {

		Date reportDate = calendar.addDays(new Date(), Calendar.MONTH, -1);
		reportDate = calendar.lastWorkingDayOfMonth(reportDate);

		if (getLastWorkingDateIfExistsInRofs(reportDate, fund) != null) {

			return getLastWorkingDateIfExistsInRofs(reportDate, fund);
		} else {
			return getClosestRofsDateToLastWorkingDateOfLastMonth(reportDate, fund);
		}
	}

	private Date getLastWorkingDateIfExistsInRofs(Date lastWorkingDateOfLastMonth, Book fund) {

		String checkDateSql = "select distinct rbc from RofsBookCategory rbc "
				+ " where rbc.date = :date and rbc.fundCategory.source = :source and rbc.book = :book";

		Query query = seamEm.createQuery(checkDateSql);
		query.setParameter("date", lastWorkingDateOfLastMonth);
		query.setParameter("source", SOURCE);
		query.setParameter("book", fund);
		query.setMaxResults(1);

		try {
			RofsBookCategory rbc = (RofsBookCategory) query.getSingleResult();

			if (rbc != null) {
				return rbc.getDate();
			}
		} catch (NoResultException e) {
			return null;
		}

		return null;
	}

	private Date getClosestRofsDateToLastWorkingDateOfLastMonth(
			Date lastWorkingDateOfLastMonth, Book fund) {

		String adjustDateSql = "select distinct rbc from RofsBookCategory rbc where "
				+ " rbc.date <= :date and rbc.fundCategory.source = :source and rbc.book = :book  order by rbc.date desc";

		Query query = seamEm.createQuery(adjustDateSql);
		query.setParameter("date", lastWorkingDateOfLastMonth);
		query.setParameter("source", SOURCE);
		query.setParameter("book", fund);
		query.setMaxResults(1);

		try {
			RofsBookCategory rbc = (RofsBookCategory) query.getSingleResult();

			if (rbc != null) {
				return rbc.getDate();
			}
		} catch (NoResultException e) {
			return null;
		}

		return null;
	}

	private FundExpressReport createFundExpressReport(Book book) {

		RiskFactor rf = getReportDefaultBenchmark();
		return new FundExpressReport(currentUser, book, reportDate, SOURCE, PERIOD_TYPE, rf);
	}

	/**
	 * fetch the default RiskFactor instance which symbol is "CDI"
	 * 
	 * @return RiskFactor
	 */
	private RiskFactor getReportDefaultBenchmark() {

		List<RiskFactor> rfs = RiskFactor.QUERY_LIST_BY_SYMBOL.list(seamEm, BENCHMARK_SYMBOL);

		if (rfs.isEmpty()) {
			return null;
		}
		return rfs.get(0);
	}

	/**
	 * using fund's Id and report date to construct report file name in FTP
	 * server
	 * 
	 * @return filename in ftpServer: fundId_date
	 */
	private String getFTPServerFileName(Book fund) {

		return String.format("%d_%s", fund.getId(), formatDate());
	}

	/**
	 * using fund's name and report date to construct download file name
	 * 
	 * @return filename of download file: fundName_date
	 */
	private String getReportDownloadFileName(Book fund) {

		return String.format("%s_%s", fund.getName(), formatDate());
	}

	/**
	 * used to unify date value format
	 * 
	 * @return
	 */
	private String formatDate() {

		return new SimpleDateFormat("dd_MM_yyyy").format(reportDate);
	}

	/**
	 * create report pdf file in report system
	 * 
	 * @param contents
	 * @param filename
	 * @param report
	 * @return
	 */
	private int schedule(FundExpressReportXmlProcessor processor, Book fund,
			FundExpressReport report) {

		byte[] content = processor.loadXml();
		String filename = getFTPServerFileName(fund);
		String ftpDir = report.getFtpDir();
		String template = report.getTemplate();

		return reportExport.schedule(content, filename, ftpDir, template, PRIORITY,
				REPORT_TYPE);
	}

// ------------------getters used on fund/view.xhtml------------------------

	public boolean isProcessing() {

		return processor.isProcessing();
	}

	/**
	 * maybe used to control permission later, return <b>true</b> for now
	 * 
	 */
	public boolean canUse() {

		return processor.isAutorized();
	}

	public FundExpressReportXmlProcessor getProcessor() {

		return this.processor;
	}

}
