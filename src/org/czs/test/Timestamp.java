package org.czs.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Timestamp {

	public static void main(String[] args) {
		
		long t1=1434267090080l;
		Date date=new Date(t1);

		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str=format.format(date);
		System.out.println(str);
	}

}
