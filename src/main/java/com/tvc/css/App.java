package com.tvc.css;

import java.math.BigDecimal;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		// USD 0.00
		BigDecimal b=new BigDecimal("0");
		BigDecimal a=new BigDecimal("20");
		System.out.println(b.add(a));
	}
	
}
