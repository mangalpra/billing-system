package com.billing.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Product {
	
	//Add a product to the database
	public static void addProduct(Scanner sc) {
//		Scanner sc = new Scanner(System.in);
		System.out.print("Enter product name: ");
		String name = sc.nextLine();
		
		System.out.print("Enter product price: ");
		double price = sc.nextDouble();
		
		String sql = "INSERT INTO Products (name, price) VALUES (?, ?)";
		
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, name);
			stmt.setDouble(2, price);
			int rows = stmt.executeUpdate();
			if(rows>0) {
				System.out.println("Product added successfully.");
			}else {
				System.out.println("Failed to add product.");
			}
			
		}catch(Exception e) {
			System.out.println("Error while adding product: ");
			e.printStackTrace();
		}
		
//		sc.close();
	}
	
	//View all products
	public static void viewProducts() {
		String sql = "Select * from Products";
		
		try(Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql)){
			
			System.out.println("\\n🛒 Available Products:");
			System.out.printf("%-5s %-15s %-10s%n","ID","Name","Price");
			System.out.println("-------------------------------");
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				
				System.out.printf("%-5d %-15s %-10.2f%n", id, name, price);
			}
		}catch(Exception e) {
			System.out.println("Error while fetching products: ");
			e.printStackTrace();
		}
	}
}
