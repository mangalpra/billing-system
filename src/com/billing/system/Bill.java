package com.billing.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bill {
	public static void createBill(Scanner sc) {
		
		// product_id -> quantity
		Map<Integer, Integer> selectedProducts = new HashMap<>();
		
		System.out.print("Enter customer name: ");
		String customerName = sc.nextLine();
		
		// Step 1: Show product list
		Product.viewProducts();
		
		// Step 2: Product selection loop
		while(true) {
			System.out.print("Enter product ID to add to bill (0 to finish): ");
			int pid = sc.nextInt();
			
			if(pid == 0) break;
			
			System.out.print("Enter quantity: ");
			int qty = sc.nextInt();
			
			selectedProducts.put(pid, selectedProducts.getOrDefault(pid,0)+qty);
		}
		
		if(selectedProducts.isEmpty()) {
			System.out.println("No products selected.");
			return;
		}
		double totalAmount = 0.0;
		
		try(Connection con = DBConnection.getConnection()){
			// Print bill
			System.out.println("\n🧾 Final Bill");
            System.out.println("Customer: " + customerName);
            System.out.printf("%-15s %-10s %-8s %-12s\n","Product Name","Price","Qty","Total");
            System.out.println("--------------------------------------------");
			
			// Step 3: Calculate total amount
			for(Map.Entry<Integer, Integer> entry: selectedProducts.entrySet()) {
				int pid = entry.getKey();
				int qty = entry.getValue();
				
				String priceQuery = "SELECT name, price FROM Products WHERE id = ?";
				PreparedStatement ps = con.prepareStatement(priceQuery);
				ps.setInt(1, pid);
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
					String productName = rs.getString("name");
					double price = rs.getDouble("price");
					double total = price * qty;
					
					// Print product line
					System.out.printf("%-15s ₹%-9.2f %-8d ₹%-12.2f\n",productName, price, qty, total);
					totalAmount += total;
				}
			}
			
			// Step 4: Insert into Bills table
			String billSql = "Insert INTO Bills (customer_name, total_amount) VALUES (?,?)";
			PreparedStatement billStmt = con.prepareStatement(billSql, Statement.RETURN_GENERATED_KEYS);
			billStmt.setString(1, customerName);
			billStmt.setDouble(2, totalAmount);
			billStmt.executeUpdate();
			
			// Get bill ID
			ResultSet generatedKeys = billStmt.getGeneratedKeys();
			int billId = 0;
			if(generatedKeys.next()) {
				billId = generatedKeys.getInt(1);
			}
			
			// Step 5: Insert each item into Bill_Items (Save bill to DB)
			String itemSql = "INSERT INTO Bill_Items (bill_id, product_id, quantity) VALUES (?,?,?)";
			PreparedStatement itemStmt = con.prepareStatement(itemSql);
			
			for(Map.Entry<Integer, Integer> entry : selectedProducts.entrySet()) {
				itemStmt.setInt(1, billId);
				itemStmt.setInt(2, entry.getKey());
				itemStmt.setInt(3, entry.getValue());
				itemStmt.addBatch(); // Batch for performance
			}
			
			itemStmt.executeBatch();
			System.out.println("--------------------------------------------");
			System.out.printf("Total Amount: ₹%.2f\n",totalAmount);
            	
		}catch(Exception e) {
			System.out.println("Error generating bill:");
			e.printStackTrace();
		}
		
	}
}
