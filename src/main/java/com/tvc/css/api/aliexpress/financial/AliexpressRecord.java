package com.tvc.css.api.aliexpress.financial;

/**
 * 放款记录
 * 
 * @author mike
 *
 */
public class AliexpressRecord {
	private String productId;// 商品 id
	private String productName;// 商品名
	private String currency; // 币种
	private String createTime;// 放款时间
	private String refundMoney;// 包含退款金额
	private String platformCommission;// 扣除平台佣金
	private String unionCommission; // 扣除联盟佣金
	private String loanAmount;// 本次放款金额

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName == null ? null : productName.replaceAll("'", "\\'");
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(String refundMoney) {
		this.refundMoney = refundMoney;
	}

	public String getPlatformCommission() {
		return platformCommission;
	}

	public void setPlatformCommission(String platformCommission) {
		this.platformCommission = platformCommission;
	}

	public String getUnionCommission() {
		return unionCommission;
	}

	public void setUnionCommission(String unionCommission) {
		this.unionCommission = unionCommission;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

}
