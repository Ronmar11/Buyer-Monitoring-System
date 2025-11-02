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
               String clientTable = "create table client(clientID int AUTO_INCREMENT PRIMARY KEY, fName varchar(50), mName varchar(50), lName varchar(50), )";
           } catch (Exception e) {
               JOptionPane.showMessageDialog(null, e);
           }
    }
    
}
