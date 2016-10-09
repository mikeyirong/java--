package com.tvc.css.api.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tvc.css.api.aliexpress.financial.AliExpressSellerAccount;
import com.tvc.css.api.aliexpress.financial.AliexpressDataJson;
import com.tvc.css.api.aliexpress.financial.AliexpressDetailRecord;
import com.tvc.css.api.aliexpress.financial.AliexpressRecord;

import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

public class FxWebkitFunctions {
	private final WebEngine engine;
	private Logger logger = LoggerFactory.getLogger(getClass());
	// private EbeanServer ebean =
	// PersistenceFactory.load("classpath:dbconfig.properties").getEbeanServer();

	public FxWebkitFunctions(WebEngine engine) {
		super();
		this.engine = engine;
	}

	public void shutdown() {
		Platform.exit();
	}

	public void log(String name) {
		logger.info(name);
	}

	public WebEngine getEngine() {
		return engine;
	}

	/**
	 * 包含外部JS
	 * 
	 * @param name
	 * @throws Exception
	 */
	public void includeJs(String url) throws Exception {
		String code = "";
		if (url.startsWith("http")) {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.connect();
			InputStream in = connection.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int len = -1;
			byte[] buf = new byte[1024];
			do {
				len = in.read(buf);
				if (len != -1) {
					out.write(buf, 0, len);
				}
			} while (len != -1);
			code = new String(out.toByteArray());
		} else {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(url);
			byte[] buf = new byte[in.available()];
			in.read(buf);
			in.close();
			code = new String(buf);
		}

		engine.executeScript(code);
	}

	boolean isEmpty(Object o) {
		return (o == null || "null".equals(o)) || o.toString().trim().length() < 1;
	}

	public void processDownloadData(JSObject R, String startToend, AliExpressSellerAccount seller) {
		try {
			int length = (int) R.getMember("length");
			byte[] buffer = new byte[length];
			for (int i = 0; i < length; i++) {
				int item = (Integer) R.getMember("" + i);
				buffer[i] = (byte) item;
			}

			/**
			 * 读EXCEL
			 */
			ByteArrayInputStream in = new ByteArrayInputStream(buffer);
			HSSFWorkbook workbook = new HSSFWorkbook(in);
			HSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			ArrayList<AliexpressDetailRecord> dataList = new ArrayList<AliexpressDetailRecord>();
			Hashtable<String, List<AliexpressDetailRecord>> dataTable = new Hashtable<String, List<AliexpressDetailRecord>>();

			for (int i = 0; i < rows; i++) {
				HSSFRow sheet1 = sheet.getRow(i);
				int columns = sheet1.getLastCellNum();
				if (sheet1 != null) {
					Inner: for (int j = 0; j < columns; j++) {
						Cell cell = sheet1.getCell(j);
						if (cell != null && cell.getStringCellValue().startsWith("20")) {
							AliexpressDetailRecord record = new AliexpressDetailRecord();
							String StartTime = cell.getStringCellValue();
							record.setCreateTime(StartTime);// 放款时间
							String ordernum = sheet1.getCell(j + 1).getStringCellValue().trim();
							record.setOrderNum(ordernum); // 订单号
							String PaymentTime = sheet1.getCell(j + 2).getStringCellValue();
							record.setPayTime(PaymentTime); // 订单支付时间
							String ProductId = sheet1.getCell(j + 3).getStringCellValue();
							record.setProductId(ProductId); // 产品id
							String ProductName = sheet1.getCell(j + 4).getStringCellValue();
							record.setProductName(ProductName);// 产品名称
							String orderamount = sheet1.getCell(j + 5).getStringCellValue();
							if (isEmpty(orderamount)) {
								record.setAmount("");
							} else {
								record.setAmount((String) ToMatcher.toMatcher(orderamount).get(1)); // 订单金额
							}
							String returnamount = sheet1.getCell(j + 6).getStringCellValue();
							if (isEmpty(returnamount)) {
								record.setRefundMoney("");
							} else {
								record.setRefundMoney((String) ToMatcher.toMatcher(returnamount).get(1));// 包含退款金额

							}
							String PlatformCommission = sheet1.getCell(j + 7).getStringCellValue();
							if (isEmpty(PlatformCommission)) {
								record.setPlatformCommission("");
							} else {
								record.setPlatformCommission((String) ToMatcher.toMatcher(PlatformCommission).get(1));// 扣除平台佣金
							}
							String AffiliateCommission = sheet1.getCell(j + 8).getStringCellValue();
							if (isEmpty(AffiliateCommission)) {
								record.setUnionCommission("");
							} else {
								record.setUnionCommission((String) ToMatcher.toMatcher(AffiliateCommission).get(1));// 扣除联盟佣金
							}
							String LoanAmount = sheet1.getCell(j + 9).getStringCellValue();
							if (isEmpty(LoanAmount)) {
								record.setLoanAmount("");
							} else {
								// record.setCurrency((String)
								// ToMatcher.toMatcher(LoanAmount).get(0));
								record.setLoanAmount((String) ToMatcher.toMatcher(LoanAmount).get(1)); // 本次放款金额
							}
							String LoanCurrency = sheet1.getCell(j + 10).getStringCellValue();
							record.setCurrency(LoanCurrency.equals("美元") ? "USD" : "CNY");// 放款币种
							String IsSpecialLending = sheet1.getCell(j + 11).getStringCellValue();
							record.setIsSpecialOrder(IsSpecialLending.equals("特别放款") ? "true" : "false");// 是否特别放款
							String FrozenAmount = sheet1.getCell(j + 12).getStringCellValue();
							try {
								String FrozenAmount1 = Integer.parseInt(FrozenAmount) + "";
								record.setSecurityRate(FrozenAmount1);// 保证金比例
							} catch (Exception e) {
								record.setSecurityRate("0");// 保证金比例
							}

							String Remark = sheet1.getCell(j + 13).getStringCellValue();
							record.setRemark(Remark); // 备注

							// Filter duplicated items
							// if (!dataList.contains(record)) {
							// dataList.add(record);
							// } else {
							// logger.info("Filter ::: {} => {}",
							// record.getOrderNum(), record.getAmount());
							// }

							List<AliexpressDetailRecord> collection = dataTable.get(record.getOrderNum());
							if (collection == null) {
								collection = new ArrayList<AliexpressDetailRecord>();
							}
							collection.add(record);
							dataTable.put(record.getOrderNum(), collection);
							break Inner;
						}
					}
				}
			}
			Iterator<String> iterator = dataTable.keySet().iterator();
			Hashtable<String, BigDecimal> totalAmount = new Hashtable<String, BigDecimal>();
			Hashtable<String, BigDecimal> loanAmounts = new Hashtable<String, BigDecimal>();

			while (iterator.hasNext()) {
				String orderNum = iterator.next();
				List<AliexpressDetailRecord> records = dataTable.get(orderNum);

				AliexpressDetailRecord pop = records.get(0);

				/**
				 * 核算订单总金额(单位：$)
				 */
				// totalAmount = totalAmount.add(new
				// BigDecimal(pop.getAmount()));

				/**
				 * 核算订单放款金额
				 */
				/**
				 * 第一步：按照币种分类
				 */
				Hashtable<String, List<AliexpressDetailRecord>> currencyGroupTable = new Hashtable<String, List<AliexpressDetailRecord>>();
				for (AliexpressDetailRecord record : records) {
					List<AliexpressDetailRecord> group = currencyGroupTable.get(record.getCurrency());
					if (group == null) {
						group = new ArrayList<AliexpressDetailRecord>();
					}

					group.add(record);
					currencyGroupTable.put(record.getCurrency(), group);
				}

				/**
				 * 适配成JSON对象准备发送给BOSS系统
				 */
				AliexpressDataJson js = new AliexpressDataJson();
				js.setAmount(pop.getAmount());
				js.setIsSpecialOrder(pop.getIsSpecialOrder());
				js.setOrderNum(pop.getOrderNum());
				js.setPayTime(pop.getPayTime());
				js.setRemark(pop.getRemark());
				js.setSecurityRate(pop.getSecurityRate());
				js.setStoreAccount(seller.getPrincipal());

				/**
				 * 分类求和 封装LIST
				 */
				Iterator<String> currentGroupIterator = currencyGroupTable.keySet().iterator();
				while (currentGroupIterator.hasNext()) {
					String currency = currentGroupIterator.next();
					BigDecimal loan = loanAmounts.get(currency);
					if (loan == null) {
						loan = new BigDecimal(0);
					}
					BigDecimal amount = totalAmount.get(currency);
					if (amount == null) {
						amount = new BigDecimal(0);
					}

					for (AliexpressDetailRecord loanInfo : currencyGroupTable.get(currency)) {
						/* 放款金额 */
						loan = loan.add(new BigDecimal(loanInfo.getLoanAmount()));
						amount = amount.add(new BigDecimal(loanInfo.getAmount()));
						AliexpressRecord alr = new AliexpressRecord();
						alr.setCreateTime(loanInfo.getCreateTime());
						alr.setCurrency(currency);
						alr.setLoanAmount(loanInfo.getLoanAmount());
						alr.setPlatformCommission(loanInfo.getPlatformCommission());
						alr.setProductId(loanInfo.getProductId());
						alr.setProductName(loanInfo.getProductName());
						alr.setRefundMoney(loanInfo.getRefundMoney());
						alr.setUnionCommission(loanInfo.getUnionCommission());
						js.getItems().add(alr);
					}

					totalAmount.put(currency, amount);
					loanAmounts.put(currency, loan);

				}
				JSONObject jsonMessage = JSON.parseObject(JSON.toJSONString(js));
				String url = "http://admin.sjlpj.cn/B2C/SyncAliFianceLoanInfo";
				String transJson = jsonMessage.toString();
				logger.info("传送数据"+transJson);
				//sendMessage(url, transJson);
			    Thread.sleep(1000);
			}

			logger.info("订单总金额：{}", totalAmount);
			logger.info("总放款金额为:{}", loanAmounts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送数据
	 */
	public void sendMessage(String url, String json) {
		/**
		 * 设置连接超时时间
		 */
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(8000)
				.setConnectionRequestTimeout(5000).setStaleConnectionCheckEnabled(true).build();
		HttpPost httpPost = new HttpPost(url);
		System.out.println("发送数据-----------》" + json);
		// CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();

		try {
			StringEntity se = new StringEntity(json, "utf-8");
			se.setContentEncoding("UTF-8");
			se.setContentType("application/json");

			httpPost.setEntity(se);
			CloseableHttpResponse result = httpClient.execute(httpPost);
			if (result.getStatusLine().getStatusCode() != 200) {
				logger.error("Method failed:" + result.getStatusLine().getStatusCode());
			} else {
				String sss = EntityUtils.toString(result.getEntity());
				logger.info("返回结果为" + sss);
			}
		} catch (Exception e) {
			logger.error("链接BOSS出错,重试", e);
			sendMessage(url, json);
		}
	}
}
