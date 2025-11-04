/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import javax.swing.JOptionPane;

/**
 *
 * @author Ronmar abalos
 */
public class Tables {
       public static void main(String[] args) {
           try {
               String clientTable = "create table client(clientID int AUTO_INCREMENT primary key, fName varchar(50), mName varchar(50), lName varchar(50), address varchar(50), gender varchar(50))";
               DbOperations.setDataOrDelete(clientTable, "Client Table Created Successfully!");
               String productTable = "create table product(productID int AUTO_INCREMENT primary key, txtName varchar(50), txtCode varchar(50), txtProduct varchar(50), txtCategory varchar(50))";
               DbOperations.setDataOrDelete(productTable, "Product Table Created Successfully!");
               String transactionTable = "CREATE TABLE transaction ("
                      + "buyID INT AUTO_INCREMENT PRIMARY KEY, "
                      + "clientID int, "
                      + "productID int, "
                      + "price DECIMAL(10,2), "
                      + "quantity INT, "
                      + "date DATE, "
                      + "INDEX client_idx (clientID), " // Corrected INDEX syntax
                      + "INDEX product_idx (productID), " // Corrected INDEX syntax
                      + "FOREIGN KEY (clientID) REFERENCES client(clientID) ON UPDATE CASCADE ON DELETE CASCADE, " // Fixed foreign key column name
                      + "FOREIGN KEY (productID) REFERENCES Product(productID) ON UPDATE CASCADE ON DELETE CASCADE"
                      + ")";
                DbOperations.setDataOrDelete(transactionTable, "Transaction Table Created Successfully" );    
                
                
         
              
              
           } catch (Exception e) {
               JOptionPane.showMessageDialog(null, e);
           }
    }
    
}
