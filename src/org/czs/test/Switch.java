package org.czs.test;

public class Switch {

	public static void main(String[] args) {
		
		int test_for_int = 0;
		
		switch( test_for_int){
		case 0:
			System.out.println("0 is run...");
		case 1:
			System.out.println("1 is run...");
			break;
		case 2:
			System.out.println("2 is run...");
			break;
		default:
			System.out.println("default is run...");
		}
	}

}
