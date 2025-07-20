# 🧾 Console-Based Billing System (Java + MySQL)

This is a simple console-based Billing System that allows you to:
- Add Products to a MySQL database
- View all available products
- Create bills for customers by selecting product and quantity
- Print a well-formatted bill summary

---

## ✅ Features

- Add new products
- View products
- Generate bills dynamically
- Connects to MySQL database using JDBC

---

## 🧪 Sample Bill Output

🛒 Available Products:
ID Name Price
1 Pen 10.00
2 Note Book 50.00
3 Book 70.00
4 Bag 400.00

🧾 Final Bill
Customer: Mangal
Product Name Price 	  Qty   Total
-------------------------------------
Pen 		 ₹10.00   2 	₹20.00
Note Book 	 ₹50.00   2 	₹100.00
Book 		 ₹70.00   1 	₹70.00
Bag 		 ₹400.00  1 	₹400.00
-------------------------------------
Total Amount: ₹590.00


---

## 💻 Technologies Used

- Java
- JDBC
- MySQL
- Console (CLI)

---

## ⚙️ Setup Instructions

1. **Clone this repo** or download the source code:
```bash
git clone https://github.com/mangalpra/billing-system-java.git

2. Create MySQL Database and Table
CREATE DATABASE billing;
USE billing;

CREATE TABLE product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    price DOUBLE
);

3. Update JDBC credentials in your Java code:
String url = "jdbc:mysql://localhost:3306/billing";
String username = "root";
String password = "your-password";

4. Compile and Run:
javac BillingSystem.java
java BillingSystem

===== Billing System =====

1. Add Product 
2. View Products 
3. Create Bill 
4. Exit 
Enter your choice:

🧑‍💻 Author
Mangal Yadav