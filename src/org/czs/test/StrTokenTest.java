package org.czs.test;

import java.util.StringTokenizer;

public class StrTokenTest {

	public static void main(String[] args) {

		new StrTokenTest().StrToken();
	}

	public void StrToken(){
		String value = "zhans,lisi,wangwu,dj,dfaa,sds";
		StringTokenizer itr =  new StringTokenizer(value.toString(), ",");
		while(itr.hasMoreTokens()){
			System.out.println(itr.nextToken());
		}
	}
}
