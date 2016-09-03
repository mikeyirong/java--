package com.tvc.css.api.aliexpress.financial;

import java.io.File;

import com.tvc.css.api.tools.VisualFetcher;

/**
 * 速卖通平台资金跟踪抓取工具
 * 
 * @author mclaren
 *
 */
public class AliexpressFinancialCashTracker {
	public void track(AliExpressSellerAccount seller) throws Exception {
		File downloadDirectory = new File("temp");
		if (!downloadDirectory.exists()) {
			downloadDirectory.mkdirs();
		}

		VisualFetcher fetcher = new VisualFetcher(downloadDirectory);
		fetcher.doWork(seller, "http://fund.aliexpress.com/exportBill.htm", "aliexpress-fan.js");
	}
}
