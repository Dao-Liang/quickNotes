/*
 * Created on May 29, 2015
 *
 * $HeadURL$
 * $Date: May 29, 2015 $
 * $Author: liang.guisheng $
 * $Revision: 1.1 $
 */
package br.com.riskoffice.ejb3dao;

import br.com.riskoffice.performance.rofs.entities.RofsIndicatorType;

/**
 * @author liang.guisheng (Last Modified By: $Author: liang.guisheng $)
 *
 * @version $Id: org.eclipse.jdt.ui.prefs,v 1.1 May 29, 2015 liang.guisheng Exp
 *          $
 */
public class WorkingDateSetTest {

	public static void main(String[] args) throws Exception {

		RofsIndicatorType a = RofsIndicatorType.valueOf(RofsIndicatorType.NET_ASSET_MOV_1M
				.name());
		System.out.println("--Enum value is: " + a);
	}
}
