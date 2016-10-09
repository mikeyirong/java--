package com.tvc.css.api.aliexpress.financial;

/**
 * 放款详细记录实体类
 * 
 * @author mike
 * 
 *
 */
public class AliexpressDetailRecord {
	private String orderNum;// 订单号
	private String payTime;// 支付时间
	private String amount;// 支付金额
	private String isSpecialOrder;// 是否特别放款：1,普通放款；2,特别放款
	private String securityRate;// 保证金冻结比例
	private String remark;// 备注

	private String productId;// 商品 id
	private String productName;// 商品名
	private String currency; // 币种
	private String createTime;// 放款时间
	private String refundMoney;// 包含退款金额
	private String platformCommission;// 扣除平台佣金
	private String unionCommission; // 扣除联盟佣金
	private String loanAmount;// 本次放款金额

	public boolean equals(Object incoming) {
		if (incoming instanceof AliexpressDetailRecord) {
			return ((AliexpressDetailRecord) incoming).getOrderNum().equals(this.getOrderNum());
		}
		return false;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getIsSpecialOrder() {
		return isSpecialOrder;
	}

	public void setIsSpecialOrder(String isSpecialOrder) {
		this.isSpecialOrder = isSpecialOrder;
	}

	public String getSecurityRate() {
		return securityRate;
	}

	public void setSecurityRate(String securityRate) {
		this.securityRate = securityRate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
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
