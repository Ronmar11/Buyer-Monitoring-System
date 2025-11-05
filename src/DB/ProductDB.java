/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;
import Information.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Ronmar abalos
 */
public class ProductDB {
     public static void save(Product product){
    String query = "insert into product(productID, txtName, txtCode, txtCategory) values('"+product.getProductID()+"', '"+product.getpName()+"', '"+product.getCode()+"', '"+product.getCategory()+"')";
    DbOperations.setDataOrDelete(query, "Added Successfully!");
     }
     public static void update(Product product) {
        Connection conn = null;
        List<String> setClauses = new ArrayList<>();
        List<String> parameters = new ArrayList<>();
        
        // 1. Build the dynamic SET clause and collect parameters
        if (product.getpName()!= null && !product.getpName().trim().isEmpty()) {
            setClauses.add("txtName = ?");
            parameters.add(product.getpName());
        }
        if (product.getCode()!= null && !product.getCode().trim().isEmpty()) {
            setClauses.add("txtCode = ?");
            parameters.add(product.getCode());
        }
        if (product.getCategory()!= null && !product.getCategory().trim().isEmpty()) {
            setClauses.add("txtCategory = ?");
            parameters.add(product.getCategory());
        }

        if (setClauses.isEmpty()) {
            JOptionPane.showMessageDialog(null,"ProductDB: No fields provided for update.");
            return;
        }

        String sql = "UPDATE product SET " + String.join(", ", setClauses) + " WHERE productID = ?";
        parameters.add(product.getProductID()); 
        
        try {
            conn = ConnectionProvider.getCon(); 

            if (conn == null) {
                throw new SQLException("Database connection failed. Check ConnectionProvider.");
            }
            
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
    
                for (int i = 0; i < parameters.size(); i++) {
                    pst.setString(i + 1, parameters.get(i));
                }

                int rows = pst.executeUpdate();

                if (rows > 0) {
                    System.out.println("ProductDB: product " + product.getProductID()+ " updated successfully.");
                } else {
                    System.out.println("ProductDB: Update failed for client " + product.getProductID() + ". ID may not exist.");
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ProductDB.class.getName()).log(Level.SEVERE, "Database Update Error", ex);
            throw new RuntimeException("Error updating client record: " + ex.getMessage(), ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    Logger.getLogger(ProductDB.class.getName()).log(Level.SEVERE, "Error closing connection", e);
                }
            }
        }
    }
     public static void delete(Product product) {
    Connection conn = null;

    String sql = "DELETE FROM product WHERE productID = ?";

    try {
        conn = ConnectionProvider.getCon();

        if (conn == null) {
            throw new SQLException("Database connection failed. Check ConnectionProvider.");
        }

        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, product.getProductID());

            int rows = pst.executeUpdate();

            if (rows > 0) {
                System.out.println("ProductDB: product " + product.getProductID()+ " deleted successfully.");
            } else {
                System.out.println("ProductDB: Deletion failed for client " + product.getProductID()+ ". ID may not exist.");
            }
        }

    } catch (SQLException ex) {
        Logger.getLogger(ProductDB.class.getName()).log(Level.SEVERE, "Database Delete Error", ex);
        // Re-throw as a RuntimeException to be caught in the UI layer
        throw new RuntimeException("Error deleting client record: " + ex.getMessage(), ex);
    } finally {
        // Standard code to close the connection
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(ProductDB.class.getName()).log(Level.SEVERE, "Error closing connection", e);
            }
        }
    }
}
     
}
