package com.tvc.css.api.aliexpress.financial;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 速卖通平台卖家账号
 * 
 * @author mclaren
 *
 */
@Entity
@Table(name="aliexpress_shop")
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

	@Override
	public String toString() {
		return "AliExpressSellerAccount [principal=" + principal + ", credentials=" + credentials + "]";
	}
	
}
