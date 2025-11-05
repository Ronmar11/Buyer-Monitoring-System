/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;
import Information.Client;
import Information.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Ronmar abalos
 */
public class TransactionDB {
      public static void save(Transaction transaction) {
        String query = "insert into transaction(buyID, Price, Quantity, clientName, productName, Date ) values ('"+transaction.getBuyID()+"','"+transaction.getPrice()+"','"+transaction.getQuantity()+"','"+transaction.getClientName()+"', '"+transaction.getProductName()+"','"+transaction.getDate()+"')";
        DbOperations.setDataOrDelete(query, "Added Successfully!");
    }
       
        public static void delete(Transaction transaction) {
        Connection conn = null;

        // The DELETE SQL only requires the table name and the WHERE clause
        String sql = "DELETE FROM transaction WHERE buyID = ?";

        try {
            conn = ConnectionProvider.getCon();

            if (conn == null) {
                throw new SQLException("Database connection failed. Check ConnectionProvider.");
            }

            try (PreparedStatement pst = conn.prepareStatement(sql)) {

                // Set the single parameter: clientID
                pst.setString(1, transaction.getBuyID());

                int rows = pst.executeUpdate();

                if (rows > 0) {
                    System.out.println("TransactionDB: Client " + transaction.getBuyID()+ " deleted successfully.");
                } else {
                    System.out.println("TransactionDB: Deletion failed for client " + transaction.getBuyID() + ". ID may not exist.");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(TransactionDB.class.getName()).log(Level.SEVERE, "Database Delete Error", ex);
            // Re-throw as a RuntimeException to be caught in the UI layer
            throw new RuntimeException("Error deleting transaction record: " + ex.getMessage(), ex);
        } finally {
            // Standard code to close the connection
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    Logger.getLogger(TransactionDB.class.getName()).log(Level.SEVERE, "Error closing connection", e);
                }
            }
        }
    }


}
