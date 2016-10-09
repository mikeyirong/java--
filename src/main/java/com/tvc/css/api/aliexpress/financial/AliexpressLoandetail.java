package com.tvc.css.api.aliexpress.financial;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 放款明细类
 * 
 * @author mike
 *
 */
@Entity
@Table(name="aliexpress_cash_detail")
public class AliexpressLoandetail {
	@Id
	private int id;
	private String salesmanName; // 业务员
	private String storeAcounnt; // 店铺
	private String depositCurrency; // 币种
	private BigDecimal depositAmount; // 定金金额
	private BigDecimal refundAmount; // 包含退款金额
	private BigDecimal platformCommission;// 扣除平台佣金
	private BigDecimal affiliateCommission;//扣除联盟佣金
	private BigDecimal  loanAmount;//本次放款金额
    private String startToend;  //起止时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSalesmanName() {
		return salesmanName;
	}
	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}
	public String getStoreAcounnt() {
		return storeAcounnt;
	}
	public void setStoreAcounnt(String storeAcounnt) {
		this.storeAcounnt = storeAcounnt;
	}
	public String getDepositCurrency() {
		return depositCurrency;
	}
	public void setDepositCurrency(String depositCurrency) {
		this.depositCurrency = depositCurrency;
	}
	public BigDecimal getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	public BigDecimal getPlatformCommission() {
		return platformCommission;
	}
	public void setPlatformCommission(BigDecimal platformCommission) {
		this.platformCommission = platformCommission;
	}
	public BigDecimal getAffiliateCommission() {
		return affiliateCommission;
	}
	public void setAffiliateCommission(BigDecimal affiliateCommission) {
		this.affiliateCommission = affiliateCommission;
	}
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getStartToend() {
		return startToend;
	}
	public void setStartToend(String startToend) {
		this.startToend = startToend;
	}
	@Override
	public String toString() {
		return "AliexpressLoandetail [id=" + id + ", salesmanName=" + salesmanName + ", storeAcounnt=" + storeAcounnt
				+ ", depositCurrency=" + depositCurrency + ", depositAmount=" + depositAmount + ", refundAmount="
				+ refundAmount + ", platformCommission=" + platformCommission + ", affiliateCommission="
				+ affiliateCommission + ", loanAmount=" + loanAmount + ", startToend=" + startToend + "]";
	}

	
}
