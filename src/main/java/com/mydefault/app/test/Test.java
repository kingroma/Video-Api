package com.mydefault.app.test;

import java.util.Date;

public class Test {

	public static void main(String[] args) {
		try {
			Date d = new Date();
			System.out.println(d);
			Thread.sleep(5000);
			
			System.out.println(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
