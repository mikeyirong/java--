package com.tvc.css;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaje.ebean.EbeanServer;
import com.tvc.be.db.PersistenceFactory;
import com.tvc.css.api.aliexpress.financial.AliExpressSellerAccount;
import com.tvc.css.api.aliexpress.financial.AliexpressFinancialCashTracker;

public class Launcher {
	Logger logger = LoggerFactory.getLogger(getClass());

	public void start(int idx) throws Exception {
		EbeanServer evb = PersistenceFactory.load("classpath:dbconfig.properties").getEbeanServer();
		List<AliExpressSellerAccount> tasks = evb.find(AliExpressSellerAccount.class).findList();
		if (idx >= tasks.size()) {
			logger.info("Expected position must be less than accounts capacity");
			return;
		}
		AliExpressSellerAccount seller = tasks.get(idx);
		new AliexpressFinancialCashTracker().track(seller);
	}

	public static void main(String[] args) throws Exception {
		new Launcher().start(Integer.parseInt(args[0]));
	}
}
