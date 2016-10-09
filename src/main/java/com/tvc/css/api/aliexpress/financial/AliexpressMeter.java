package com.tvc.css.api.aliexpress.financial;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author mike 放款明细表实体类
 *
 */
public class AliexpressMeter {
	private String productId;// 商品 id
	private String produnctName;// 商品名
	private String currency; // 币种
	private String refundMoney;// 包含退款金额
	private String platformCommission;// 扣除平台佣金
	private String unionCommission; // 扣除联盟佣金
	private String loanAmount;// 本次放款金额

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProdunctName() {
		return produnctName;
	}

	public void setProdunctName(String produnctName) {
		this.produnctName = produnctName;
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

	class Simple {
		private String ProductId;
		private String ProductName;
		private String Currency;
		private String RefundMoney;
		private String PlatformCommission;
		private String UnionCommission;
		private String LoanAmount;

		public String getCurrency() {
			return Currency;
		}

		public void setCurrency(String currency) {
			Currency = currency;
		}

		public String getProductId() {
			return ProductId;
		}

		public void setProductId(String productId) {
			ProductId = productId;
		}

		public String getProductName() {
			return ProductName;
		}

		public void setProductName(String productName) {
			ProductName = productName;
		}

		public String getRefundMoney() {
			return RefundMoney;
		}

		public void setRefundMoney(String refundMoney) {
			RefundMoney = refundMoney;
		}

		public String getPlatformCommission() {
			return PlatformCommission;
		}

		public void setPlatformCommission(String platformCommission) {
			PlatformCommission = platformCommission;
		}

		public String getUnionCommission() {
			return UnionCommission;
		}

		public void setUnionCommission(String unionCommission) {
			UnionCommission = unionCommission;
		}

		public String getLoanAmount() {
			return LoanAmount;
		}

		public void setLoanAmount(String loanAmount) {
			LoanAmount = loanAmount;
		}

	}

	public JSONObject toJson() {
		Simple simple = new Simple();
		simple.ProductId = productId;
		simple.ProductName = produnctName;
		simple.Currency = currency;
		simple.RefundMoney = refundMoney;
		simple.PlatformCommission = platformCommission;
		simple.UnionCommission = unionCommission;
		simple.LoanAmount = loanAmount;
		return JSON.parseObject(JSON.toJSONString(simple));
	}
}
