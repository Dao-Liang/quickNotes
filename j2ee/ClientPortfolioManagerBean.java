/*
 * Created on Mar 29, 2007
 *
 * $RCSfile: org.eclipse.jdt.ui.prefs,v $
 * $Date: 2006/05/19 21:48:04 $
 * $Author: endo $
 * $Revision: 1.1 $
 * $State: Exp $
 */
package br.com.riskoffice.client.ui.web.portfolio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.captcha.Captcha;
import org.jboss.seam.core.Events;
import org.jboss.seam.ejb.SeamInterceptor;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.navigation.Pages;

import br.com.riskoffice.client.ClientQuotaTreatmentProcessor;
import br.com.riskoffice.client.entities.ClientPool;
import br.com.riskoffice.client.entities.ClientQuotaTreatment;
import br.com.riskoffice.domain.entities.Client;
import br.com.riskoffice.entities.Book;
import br.com.riskoffice.entities.User;
import br.com.riskoffice.portfolio.PQuotaTreeLoader;
import br.com.riskoffice.portfolio.PQuotaTreeNode;
import br.com.riskoffice.portfolio.PortfolioDAO;
import br.com.riskoffice.portfolio.PortfolioSummaryGen;
import br.com.riskoffice.portfolio.PortfolioUtils;
import br.com.riskoffice.portfolio.elements.entities.AbstractPElement;
import br.com.riskoffice.portfolio.elements.entities.PQuota;
import br.com.riskoffice.portfolio.elements.entities.PQuotaTreatment;
import br.com.riskoffice.portfolio.entities.ElementSummary;
import br.com.riskoffice.portfolio.entities.Portfolio;
import br.com.riskoffice.portfolio.entities.PortfolioLink;
import br.com.riskoffice.portfolio.entities.PortfolioSummary;
import br.com.riskoffice.portfolio.entities.PortfolioType;
import br.com.riskoffice.portfolio.entities.RosysPortfolioPools;
import br.com.riskoffice.portfolio.entities.SimulatedPortfolio;
import br.com.riskoffice.portfolio.xml.entities.PortfolioXml;
import br.com.riskoffice.portfolio.xml.upload.PortfolioXmlFileUpload;
import br.com.riskoffice.risk.computations.Computation;
import br.com.riskoffice.risk.computations.ComputationBean;
import br.com.riskoffice.risk.computations.entities.ProductMtM;
import br.com.riskoffice.risk.report.ui.web.excel.ReportLeverageXLSBuilder;
import br.com.riskoffice.risk.riskfactors.entities.Currency;
import br.com.riskoffice.risk.riskfactors.entities.RosysCurrrencies;
import br.com.riskoffice.rosys.util.RosysJndi;
import br.com.riskoffice.ui.web.RosysDownloadHelper;
import br.com.riskoffice.util.Log;

/**
 * @author endo (Last Modified By: $Author: endo $)
 * 
 * @version $Id: org.eclipse.jdt.ui.prefs,v 1.1 2006/05/19 21:48:04 endo Exp $
 */
@Stateful
@Name("clientPortfolioManager")
@Interceptors(SeamInterceptor.class)
public class ClientPortfolioManagerBean implements ClientPortfolioManager, Serializable {

	private static final long serialVersionUID = 8013961169874862440L;

	private static final transient Log log = new Log(ClientPortfolioManagerBean.class);

	@In(create = true)
	private EntityManager seamEm;

	@PersistenceContext(unitName = "rosys")
	private EntityManager em;

	@In
	private User currentUser;

	@In(required = true, scope = ScopeType.SESSION)
	private Client currentClient;

	private String viewId;

	private transient PQuotaTreeNode quotaTree;

	private transient List<PQuotaTreeNode> flatTree;

	@RequestParameter
	private Integer portfolioId;

	@In(required = false, scope = ScopeType.CONVERSATION)
	@Out(required = false, scope = ScopeType.CONVERSATION)
	private Portfolio portfolio;

	@In(required = false, scope = ScopeType.CONVERSATION)
	@Out(required = false, scope = ScopeType.CONVERSATION)
	private SimulatedPortfolio simulatedPortfolio;

	@In(required = false)
	@Out(required = false, scope = ScopeType.CONVERSATION)
	private ClientPortfoliosQuery clientPortfoliosQuery;

	@EJB
	private PortfolioSummaryGen portfolioSummaryGen;

	@EJB
	private PortfolioDAO portfolioDAO;

	@EJB
	private PortfolioXmlFileUpload portfolioXmlFileUpload;

	@DataModel
	private List<ElementSummary> elementSummaryList;

	private boolean editMode = false;

	@EJB
	private ClientQuotaTreatmentProcessor clientQuotaTreatmentProcessor;

	@EJB
	private PQuotaTreeLoader pQuotaTreeLoader;

	@RequestParameter
	private Integer bookId;

	@In(required = false, scope = ScopeType.CONVERSATION)
	@Out(required = false, scope = ScopeType.CONVERSATION)
	private ClientQuotaTreatment clientQuotaTreatment;

	private int numberOfLayers;

	private int leverageExportLevel;

	private boolean displayQuotaTreeOverride = false;

	@In(create = true)
	private Captcha captcha;

	private static final Integer LIMIT_BEFORE_CAPTCHA = 10;

	// Limite de cotas a serem exibidas na árvore
	private static final int PQUOTA_TREE_LIMIT = 50;

	@Override
	public void editQuotaTreatment() {
		Book book = seamEm.find(Book.class, bookId);

		clientQuotaTreatment = ClientQuotaTreatment.QUERY_FIND_BY_CLIENT_AND_BOOK.single(
				seamEm, currentClient, book);
	}

	@Override
	@Observer("clientQuotaTreatmentRefresh")
	public void refreshQuotaTree() {
		quotaTree = null;
		flatTree = null;
	}

	public String selectPortfolio() {
		portfolio = seamEm.find(Portfolio.class, portfolioId);

		viewId = Pages.getViewId(FacesContext.getCurrentInstance());

		log.info("User: %s has selected %s", currentUser.getLogin(), portfolio);

		updateSummaryMap();
		setElementSummaryList();
		reprocessOldPortfolioIfNeeded();

		return "portfolio selected";
	}

	/**
	 * Reprocessa um Portfolio antigo, caso o mesmo tenha tido suas cotas e
	 * elementos apagados.
	 */
	private void reprocessOldPortfolioIfNeeded() {
		if (portfolio.getType() != PortfolioType.XML) {
			log.debug("Not reprocessing portfolio type %s, id=%d (%tF)", portfolio.getType(),
					portfolio.getId(), portfolio.getDate());
			return;
		}

		PortfolioXml portfolioXml = (PortfolioXml) portfolio;
		if (!portfolioXml.isArchived()) {
			log.debug("Not archived portfolio id=%d (%tF), skipping reprocessing",
					portfolioXml.getId(), portfolioXml.getDate());
			return;
		}

		portfolioXml = seamEm.find(PortfolioXml.class, portfolio.getId());
		portfolioXmlFileUpload.reprocessOldPortfolio(portfolioXml);
	}

	private void createSummary() {
		portfolioSummaryGen.generateSummary(portfolio);
		PortfolioSummary summary = portfolio.getPortfolioSummary();
		seamEm.persist(summary);
		for (ElementSummary elementSummary : portfolio.getElementSummaryMap().values()) {
			seamEm.persist(elementSummary);
		}
	}

	private void updateSummaryMap() {
		PortfolioSummary summary = portfolio.getPortfolioSummary();

		if (summary == null) {
			createSummary();
		} else {
			portfolioSummaryGen.reloadSummary(portfolio);
		}
	}

	private void setElementSummaryList() {
		elementSummaryList = new ArrayList<ElementSummary>(portfolio.getElementSummaryMap()
				.values());
		Collections.sort(elementSummaryList);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public void removePortfolios() {
		List<Portfolio> portfolios = clientPortfoliosQuery.getResultList();
		for (Portfolio portfolio : portfolios) {
			if (portfolio.isSelected()) {
				try {
					portfolioDAO.removePortfolio(portfolio.getId(), null);
					FacesMessages.instance().addFromResourceBundle("portfolio-xml.removed",
							portfolio.getBook().getName(), portfolio.getDate());
				} catch (IllegalStateException e) {
					FacesMessages.instance().add(e.getMessage());
				}
			}
		}

		clientPortfoliosQuery.refresh();
		clientPortfoliosQuery.clearEx();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String removePortfoliosFromList() {
		removePortfolios();

		return "back";
	}

	public boolean isEditMode() {
		return editMode;
	}

	public String switchEditMode() {
		editMode = !editMode;
		return "edit mode switched";
	}

	public String updateValues() {
		seamEm.flush();

		updateSummaryMap();
		setElementSummaryList();

		return "values updated";
	}

	/*
	 * moe: razão para NOT_SUPPORTED => PQuotaTreatmentProcessor trabalha com
	 * outro EntityManager. Delegar transações para o Processor.
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String updateQuotaTreatment() {
		log.debug("Update quota treatment");

		clientQuotaTreatmentProcessor.getQuotaTreatments(portfolio, true);

		editMode = false;
		quotaTree = null;

		return "treatment updated";
	}

	public void updateReports() {
		// É necessário o clear pois quem muda o status do portfolio é um outro
		// entity manager
		seamEm.clear();

		// É necessário recarregar o portfolio, pois o clear() tornou ele
		// detached
		if (portfolio != null) {
			portfolio = seamEm.find(Portfolio.class, portfolio.getId());
		}
		if (simulatedPortfolio != null) {
			simulatedPortfolio = seamEm.find(SimulatedPortfolio.class,
					simulatedPortfolio.getId());
		}

		// Atualiza lista
		Events.instance().raiseEvent("portfolioReportStressQueryRefresh");
		Events.instance().raiseEvent("portfolioReportVaRsQueryRefresh");
		Events.instance().raiseEvent("portfolioReportBacktestQueryRefresh");
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PQuotaTreeNode getQuotaTree() {
		if (quotaTree == null) {
			quotaTree = pQuotaTreeLoader.getPQuotaTree(portfolio, portfolio.getDate());
		}

		return quotaTree;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PQuotaTreeNode> getFlatQuotaTree() {
		getQuotaTree();

		if (flatTree == null) {
			List<PQuotaTreeNode> flatTree = new LinkedList<PQuotaTreeNode>();
			doFlatTree(quotaTree, flatTree);

			this.flatTree = flatTree;
		}

		return flatTree;
	}

	/**
	 * Percorre recursivamente a árvore de cotas, adicionando em uma lista cada
	 * cota encontrada.
	 * 
	 * @param startNode
	 *            Nó de início da busca.
	 * @param flatTree
	 *            Referência para a lista de cotas da árvore.
	 */
	private void doFlatTree(PQuotaTreeNode startNode, List<PQuotaTreeNode> flatTree) {
		List<PQuotaTreeNode> childQuotas = startNode.getChildQuotas();

		// Fim da recursão
		if (childQuotas == null) {
			return;
		}

		// Adiciona ramos na lista, percorrendo recursivamente cada um
		for (PQuotaTreeNode childNode : childQuotas) {
			flatTree.add(childNode);
			doFlatTree(childNode, flatTree);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean isHasPQuotas() {
		return !portfolio.getQuotas().isEmpty();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean isDisplayQuotaTree() {
		int pQuotas = portfolio.getQuotas().size();

		if (pQuotas <= PQUOTA_TREE_LIMIT) {
			return true;
		}

		if (displayQuotaTreeOverride) {
			return true;
		}

		return false;
	}

	@Override
	public void switchDisplayQuotaTree() {
		this.displayQuotaTreeOverride = true;
	}

	@Override
	public void exportTree() {
		// Constroi o excel
		QuotaThreeXLSExporter exporter = new QuotaThreeXLSExporter(getQuotaTree(),
				portfolioDAO, numberOfLayers);
		byte[] bytes = exporter.export();
		RosysDownloadHelper.exportXls(bytes, "arvore.xls");
	}

	@Override
	public Date getPortfolioOriginalDate() {
		return portfolioDAO.getPortfolioOriginalDate(this.portfolio);
	}

	@Override
	public void linkXML() {
		PQuotaTreeNode rootTree = getQuotaTree();

		int count = parseNode(rootTree, numberOfLayers);

		FacesMessages.instance().addFromResourceBundle("client.quota_treatment.xmls-linked",
				count);
	}

	private int parseNode(PQuotaTreeNode node, int numberOfLayers) {
		int count = 0;

		List<PQuotaTreeNode> leafs = node.getChildQuotas();
		if (leafs == null) {
			return 0;
		}

		for (PQuotaTreeNode childNode : leafs) {
			Portfolio dependency = childNode.getTreatment().getDependency();
			if (dependency != null) {
				if (linkXMLDependency(dependency)) {
					count++;
				}
				if (numberOfLayers > 0) {
					count += parseNode(childNode, numberOfLayers - 1);
				}
			}
		}

		return count;
	}

	private boolean linkXMLDependency(Portfolio dependency) {
		dependency = em.find(Portfolio.class, dependency.getId());

		if (dependency.getPool().getName().equals(RosysPortfolioPools.BACKOFFICE.getName())) {
			ClientPool pool = currentClient.getPool();
			if (pool == null) {
				log.debug("Client pool is null. Client: %s", currentClient.getSymbol());
				return false;
			}
			// Verificar se já existe
			List<Portfolio> pfolios = Portfolio.QUERY_LIST_BY_BOOK_DATE_AND_POOL.list(em,
					dependency.getBook(), dependency.getDate(), pool);
			for (Portfolio portfolio : pfolios) {
				if (portfolio.isApproved()) {
					log.debug("Link already exists: %s [book id: %d] Pool: %s [id: %d]",
							dependency.getBook().getName(), dependency.getBook().getId(),
							pool.getName(), pool.getId());
					return false;
				}
			}
			if (pfolios.size() > 0) {
				Portfolio portfolio = pfolios.get(0);
				portfolio.setApproved(true);
				em.merge(portfolio);
				return true;
			}
			// Cria um link para o portfolio original da cesta do backoffice
			PortfolioLink pLink = new PortfolioLink(dependency, currentClient.getPool());
			pLink.setApproved(true);
			em.persist(pLink);
			FacesMessages.instance().addFromResourceBundle(
					"client.quota_treatment.xml-linked", dependency.getBook().getName());
			return true;
		}

		return false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void downloadPortfolioLeverageReport() {
		List<PQuotaTreeNode> flatQuotaTree = getFlatQuotaTree();
		List<Portfolio> portfolios = getPortfoliosForQuotaTree(flatQuotaTree,
				leverageExportLevel);

		ReportLeverageXLSBuilder builder = new ReportLeverageXLSBuilder(portfolios,
				currentClient, seamEm);

		HSSFWorkbook workbook = builder.getWorkbook();
		RosysDownloadHelper.exportXls(workbook, getReportLeverageFilename());
	}

	/**
	 * Prepara captcha pra uso
	 */
	public String prepareCaptcha() {
		if (getSelectedList().size() >= LIMIT_BEFORE_CAPTCHA) {
			captcha.init();
			captcha.setResponse(null);

			return "remove";
		}

		removePortfolios();
		return "";
	}

	/**
	 * @return Nome do arquivo XLS, relatório de alavancagem.
	 */
	private String getReportLeverageFilename() {
		StringBuilder filename = new StringBuilder();

		filename.append(currentClient.getName());
		filename.append(" ");
		filename.append(" Alavancagem ");
		filename.append(".xls");

		return filename.toString();
	}

	/**
	 * Extrai uma lista de relatórios para uma árvore de cotas da posição atual.
	 * 
	 * @param flatQuotaTree
	 *            Árvore de cotas da posição atual.
	 * @param exportLevel
	 *            Nível máximo de cotas a ser considerado.
	 */
	private List<Portfolio> getPortfoliosForQuotaTree(List<PQuotaTreeNode> flatQuotaTree,
			int exportLevel) {
		List<Portfolio> portfolios = new LinkedList<Portfolio>();

		// Nó-raiz
		portfolios.add(portfolio);

		for (PQuotaTreeNode node : flatQuotaTree) {
			// Nível de cota excluído da filtragem
			if (node.getLevel() > exportLevel) {
				continue;
			}

			PQuotaTreatment treatment = node.getTreatment();
			if (treatment == null) {
				continue;
			}

			Portfolio currentPortfolio = treatment.getDependency();
			if (currentPortfolio != null) {
				currentPortfolio = seamEm.find(Portfolio.class, currentPortfolio.getId());

				// Lazy
				currentPortfolio.getPortfolioSummary().getId();
				portfolios.add(currentPortfolio);
			}
		}

		return portfolios;
	}

	/**
	 * Recalcula mtms dos elementos e cotas da posição.
	 */
	public void recomputeMtMs() {
		if (portfolio.getCurrency() == null) {
			portfolio.setCurrency(Currency.QUERY_FIND_BY_SYMBOL.single(seamEm,
					RosysCurrrencies.BRL.getSymbol()));
		}
		Computation computation = RosysJndi.lookup(ComputationBean.class);
		recomputePElementsMtM(portfolio, computation);
		recomputePQuotasMtM(portfolio, computation);
		FacesMessages.instance().addFromResourceBundle("portfolio.updated-mtms");
	}

	/**
	 * Recalcula os produtos da posição.
	 * 
	 * @param pfolio
	 *            posição.
	 */
	private void recomputePElementsMtM(Portfolio pfolio, Computation computation) {
		for (AbstractPElement element : pfolio.getElements()) {
			if (element.getProductMtM() != null) {
				continue;
			}
			if (!element.isMatched()) {
				log.warn("Element [%s] not matched. ", element.toString());
				continue;
			}
			ProductMtM mtM = computation.computePElement(element, pfolio.getCurrency());
			if (mtM == null) {
				log.warn("Null mtm for %s", element.toString());
				continue;
			}
			element.setProductMtM(mtM);
			seamEm.merge(element);
		}
	}

	/**
	 * Recalcula as cotas da posição.
	 * 
	 * @param pfolio
	 *            posição.
	 */
	private void recomputePQuotasMtM(Portfolio pfolio, Computation computation) {
		for (PQuota quota : pfolio.getQuotas()) {
			if (!quota.isMatched()) {
				log.warn("Element [%s] not matched. ", quota.toString());
				continue;
			}
			Double value = computation.computePQuotaValue(quota, pfolio.getCurrency());
			if (value == null) {
				log.warn("Null value for [%s]. ", quota.toString());
				continue;
			}
			quota.setValue(value);
			seamEm.merge(quota);
		}
	}

	@End
	public String close() {
		return viewId;
	}

	@Remove
	public void destroy() {
		// Seam stateful session bean mandatory method
	}

	public int getNumberOfLayers() {
		return numberOfLayers;
	}

	public void setNumberOfLayers(int numberOfLayers) {
		this.numberOfLayers = numberOfLayers;
	}

	public int getLeverageExportLevel() {
		return leverageExportLevel;
	}

	public void setLeverageExportLevel(int leverageExportLevel) {
		this.leverageExportLevel = leverageExportLevel;
	}

	public List<Portfolio> getSelectedList() {
		List<Portfolio> portfolios = clientPortfoliosQuery.getResultList();

		return PortfolioUtils.getSelected(portfolios);
	}
}
