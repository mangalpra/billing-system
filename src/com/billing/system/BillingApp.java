package com.billing.system;

import java.util.Scanner;

public class BillingApp {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			while(true) {
				System.out.println("===== Billing System =====");
				System.out.print("\n1. Add Product \n2. View Products \n3. Create Bill "
						+ "\n4. Exit \nEnter your choice: ");
				int choice = sc.nextInt();
				sc.nextLine();
				switch(choice) {
					case 1:
						Product.addProduct(sc);
						break;
					case 2:
						Product.viewProducts();
						break;
					case 3:
						Bill.createBill(sc);
						break;
					case 4:
						System.out.println("Existing...");
						return;
					default:
						System.out.println("Invalid choice!");
				}
			}
		}finally {
			sc.close();
		}
		
	}
}
