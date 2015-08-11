package org.czs.test;

import java.util.Arrays;

public class Enum {

	public enum Company {
		EBAY, PAYPAL, GOOGLE, YAHOO, ATT
	}
	
	public static void main(String[] args) {
		Company cName = Company.EBAY;
		System.out.println(cName);
		
		String str = "ahdgdbf";
		char[] strs = str.toCharArray();
		System.out.println(new String(strs));
		Arrays.sort(strs);
		System.out.println(new String(strs));
		
		char[] data={'a','b','c'}; 
		String s=new String(data); 
		System.out.println(s); 
		

	}

}
