/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import javax.swing.JOptionPane;

/**
 *
 * @author duval
 */
public class ProductTables {
public static void main(String[] args) {
           try {
               String productTable = "create table product(productID int AUTO_INCREMENT primary key, txtName varchar(50), txtCode varchar(50), txtProduct varchar(50), txtCategory varchar(50))";
               DbOperations.setDataOrDelete(productTable, "Product Table Created Successfully!");
           } catch (Exception e) {
               JOptionPane.showMessageDialog(null, e);
           }
    }

}
