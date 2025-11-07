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
               String clientTable = "create table client(clientID varchar(50) primary key not null, fName varchar(50) not null, mName varchar(50) not null, lName varchar(50) not null, address varchar(50) not null, gender varchar(50) not null)";
               DbOperations.setDataOrDelete(clientTable, "Client Table Created Successfully!");
               String productTable = "create table product(productID varchar(50) primary key not null, txtName varchar(50) not null, txtCode varchar(50) not null, txtCategory varchar(50) not null)";
               DbOperations.setDataOrDelete(productTable, "Product Table Created Successfully!");
               String transactionTable = "CREATE TABLE transaction ("
                      + "buyID varchar(50) not null, "
                      + "clientID varchar(50), "
                      + "productID varchar(50), "
                      + "productName varchar(50) not null, "
                      + "clientName varchar(50) not null, "
                      + "price DECIMAL(10,2) not null, "
                      + "quantity INT not null, "
                      + "date DATE not null, "
                      + "INDEX client_idx (clientID), "
                      + "INDEX product_idx (productID), "
                      + "FOREIGN KEY (clientID) REFERENCES client(clientID) ON UPDATE CASCADE ON DELETE CASCADE, "
                      + "FOREIGN KEY (productID) REFERENCES Product(productID) ON UPDATE CASCADE ON DELETE CASCADE"
                      + ")";
                DbOperations.setDataOrDelete(transactionTable, "Transaction Table Created Successfully" );    
              
           } catch (Exception e) {
               JOptionPane.showMessageDialog(null, e);
           }
    }
    
}
