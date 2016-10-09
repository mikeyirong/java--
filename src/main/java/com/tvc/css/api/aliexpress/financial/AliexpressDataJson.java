package com.tvc.css.api.aliexpress.financial;

import java.util.ArrayList;
import java.util.List;

/**
 * 发送数据实体类
 * 
 * @author mike
 *
 */
public class AliexpressDataJson {
	private String storeAccount;// 店铺名称
	private String orderNum;// 订单号
	private String payTime;// 支付时间
	private String amount;// 支付金额
	private String isSpecialOrder;// 是否特别放款：1,普通放款；2,特别放款
	private String securityRate;// 保证金冻结比例
	private String remark;// 备注
	private List<AliexpressRecord> items = new ArrayList<AliexpressRecord>();// 集合

	public String getStoreAccount() {
		return storeAccount;
	}

	public void setStoreAccount(String storeAccount) {
		this.storeAccount = storeAccount;
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

	public List<AliexpressRecord> getItems() {
		return items;
	}

	public void setItems(List<AliexpressRecord> items) {
		this.items = items;
	}
}
