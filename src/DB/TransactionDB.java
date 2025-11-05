/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;
import Information.Transaction;
/**
 *
 * @author Ronmar abalos
 */
public class TransactionDB {
      public static void save(Transaction transaction) {
        String query = "insert into transaction(buyID, Price, Quantity, clientName, productName, Date ) values ('"+transaction.getBuyID()+"','"+transaction.getPrice()+"','"+transaction.getQuantity()+"','"+transaction.getClientName()+"', '"+transaction.getProductName()+"','"+transaction.getDate()+"')";
        DbOperations.setDataOrDelete(query, "Added Successfully!");
    }
    
}
