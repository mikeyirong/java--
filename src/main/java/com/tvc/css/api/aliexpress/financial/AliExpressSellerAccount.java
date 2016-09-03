package com.tvc.css.api.aliexpress.financial;

/**
 * 速卖通平台卖家账号
 * 
 * @author mclaren
 *
 */
public class AliExpressSellerAccount {
	private String principal;
	private String credentials;

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getCredentials() {
		return credentials;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
}
