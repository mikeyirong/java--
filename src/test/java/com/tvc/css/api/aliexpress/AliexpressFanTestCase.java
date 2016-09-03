package com.tvc.css.api.aliexpress;

import com.tvc.css.api.aliexpress.financial.AliExpressSellerAccount;
import com.tvc.css.api.aliexpress.financial.AliexpressFinancialCashTracker;

import junit.framework.TestCase;

public class AliexpressFanTestCase extends TestCase {
	public void testAliexpressTestCase() throws Exception {
		AliExpressSellerAccount seller = new AliExpressSellerAccount();
		seller.setPrincipal("stella@inc.tvc-tech.com");
		seller.setCredentials("tvc20150720");
		new AliexpressFinancialCashTracker().track(seller);
	}
}
