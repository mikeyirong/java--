package com.tvc.css.api.aliexpress.financial;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 财务记录
 * 
 * @author mike
 *
 */
@Entity
@Table(name = "aliexpress_cash_record")
public class AliexpressFinancialRecord {
	@Id
	private int id;
	private String shopName;//店铺名称
	private Date startTime; // 发生时间
	private String orderNum; // 订单号
	private Date paymentTime; // 订单支付时间
	private String productId; // 商品ID
	private String productName; // 商品名称
	private String orderAmount; // 订单金额
	private String returnAmount; // 包含退款金额
	private String platformCommission; // 扣除平台佣金
	private String affiliateCommission; // 扣除联盟佣金
	private String loanAmount; // 本次放款金额
	private String loanCurrency; // 本次放款币种
	private String isSpecialLending; // 是否特别放款订单
	private String frozenAmount; // 保证金冻结比例
	private String remark; // 备注

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
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

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(String returnAmount) {
		this.returnAmount = returnAmount;
	}

	public String getPlatformCommission() {
		return platformCommission;
	}

	public void setPlatformCommission(String platformCommission) {
		this.platformCommission = platformCommission;
	}

	public String getAffiliateCommission() {
		return affiliateCommission;
	}

	public void setAffiliateCommission(String affiliateCommission) {
		this.affiliateCommission = affiliateCommission;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getLoanCurrency() {
		return loanCurrency;
	}

	public void setLoanCurrency(String loanCurrency) {
		this.loanCurrency = loanCurrency;
	}

	public String getIsSpecialLending() {
		return isSpecialLending;
	}

	public void setIsSpecialLending(String isSpecialLending) {
		this.isSpecialLending = isSpecialLending;
	}

	public String getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(String frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "AliexpressFinancialRecord [startTime=" + startTime + ", orderNum=" + orderNum + ", paymentTime="
				+ paymentTime + ", productId=" + productId + ", productName=" + productName + ", orderAmount="
				+ orderAmount + ", returnAmount=" + returnAmount + ", platformCommission=" + platformCommission
				+ ", affiliateCommission=" + affiliateCommission + ", loanAmount=" + loanAmount + ", loanCurrency="
				+ loanCurrency + ", isSpecialLending=" + isSpecialLending + ", frozenAmount=" + frozenAmount
				+ ", remark=" + remark + "]";
	}
   public JSONObject toJSON(){
    	String json="{"
    			+""
    			+""
    			+""
    			+""
    			+"}";
    	
    	return JSON.parseObject(json);
    }
}
