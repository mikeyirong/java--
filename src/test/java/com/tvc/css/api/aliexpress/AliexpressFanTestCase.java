package com.tvc.css.api.aliexpress;

import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.tvc.be.db.PersistenceFactory;
import com.tvc.css.api.aliexpress.financial.AliExpressSellerAccount;
import com.tvc.css.api.aliexpress.financial.AliexpressFinancialCashTracker;

import junit.framework.TestCase;

public class AliexpressFanTestCase extends TestCase {
	public void testAliexpressTestCase() throws Exception {

		EbeanServer evb = PersistenceFactory.load("classpath:dbconfig.properties").getEbeanServer();
		List<AliExpressSellerAccount> tasks = evb.find(AliExpressSellerAccount.class).findList();
		int length = tasks.size();
		for (int i = 0; i < length; i++) {
			AliExpressSellerAccount seller = tasks.get(i);
			new AliexpressFinancialCashTracker().track(seller);
		}
	}
}
