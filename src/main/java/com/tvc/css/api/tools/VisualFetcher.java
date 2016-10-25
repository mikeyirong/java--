package com.tvc.css.api.tools;

import java.io.File;

import com.tvc.css.api.aliexpress.financial.AliExpressSellerAccount;

/**
 * 可视化抓取器
 * 
 * @author Administrator
 *
 */
public class VisualFetcher {
	/**
	 * 下载文件的保存目录
	 */
	private final File downloadDirectory;

	public VisualFetcher(File downloadDirectory) {
		super();
		this.downloadDirectory = downloadDirectory;
	}

	public void doWork(AliExpressSellerAccount seller, String url, String analyzer) {
		FxWebkit.analyzer = analyzer;
		FxWebkit.url = url;
		FxWebkit.downLoadDirectory = downloadDirectory;
		FxWebkit.seller = seller;
		FxWebkit.fire();
	}
}
