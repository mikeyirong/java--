package com.tvc.css.api.aliexpress.financial;

import java.math.BigDecimal;

public class Amount {
	private final String currency;

	private final BigDecimal value;

	public Amount(String currency, BigDecimal value) {
		this.currency = currency;
		this.value = value;
	}

	public String getCurrency() {
		return currency;
	}

	public BigDecimal getValue() {
		return value;
	}

}
