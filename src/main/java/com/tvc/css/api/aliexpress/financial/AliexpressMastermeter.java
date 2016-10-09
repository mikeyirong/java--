package com.tvc.css.api.aliexpress.financial;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

//放款明细主表实体
public class AliexpressMastermeter {
	private String shopName;// 店铺名称
	private String currency; // 币种
	private String relaseDate;// 放款时间
	private String orderNum;// 订单号
	private String payDate;// 支付时间
	private String payAmoun;// 支付金额
	private String isRelase;// 是否特别放款：1,普通放款；2,特别放款
	private String proportion;// 保证金冻结比例
	private String remark;// 备注

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getRelaseDate() {
		return relaseDate;
	}

	public void setRelaseDate(String relaseDate) {
		this.relaseDate = relaseDate;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getPayAmoun() {
		return payAmoun;
	}

	public void setPayAmoun(String payAmoun) {
		this.payAmoun = payAmoun;
	}

	public String getIsRelase() {
		return isRelase;
	}

	public void setIsRelase(String isRelase) {
		this.isRelase = isRelase;
	}

	public String getProportion() {
		return proportion;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	class Simple {
		private String StoreAccount;
		private String Currency;
		private String CreateTime;
		private String OrderNum;
		private String PayTime;
		private String Amount;
		private String IsSpecialOrder;
		private String securityRate;
		private String Remark;

		public String getStoreAccount() {
			return StoreAccount;
		}

		public void setStoreAccount(String storeAccount) {
			StoreAccount = storeAccount;
		}

		public String getCurrency() {
			return Currency;
		}

		public void setCurrency(String currency) {
			Currency = currency;
		}

		public String getCreateTime() {
			return CreateTime;
		}

		public void setCreateTime(String createTime) {
			CreateTime = createTime;
		}

		public String getOrderNum() {
			return OrderNum;
		}

		public void setOrderNum(String orderNum) {
			OrderNum = orderNum;
		}

		public String getPayTime() {
			return PayTime;
		}

		public void setPayTime(String payTime) {
			PayTime = payTime;
		}

		public String getAmount() {
			return Amount;
		}

		public void setAmount(String amount) {
			Amount = amount;
		}

		public String getIsSpecialOrder() {
			return IsSpecialOrder;
		}

		public void setIsSpecialOrder(String isSpecialOrder) {
			IsSpecialOrder = isSpecialOrder;
		}

		public String getSecurityRate() {
			return securityRate;
		}

		public void setSecurityRate(String securityRate) {
			this.securityRate = securityRate;
		}

		public String getRemark() {
			return Remark;
		}

		public void setRemark(String remark) {
			Remark = remark;
		}

	}

	public JSONObject toJson() {
		Simple simple = new Simple();
		simple.StoreAccount = shopName;
		simple.Currency = currency;
		simple.CreateTime = relaseDate;
		simple.OrderNum = orderNum;
		simple.PayTime = payDate;
		simple.Amount = payAmoun;
		simple.IsSpecialOrder = isRelase;
		simple.securityRate = proportion;
		simple.Remark = remark;
		return JSON.parseObject(JSON.toJSONString(simple));
	}
}
