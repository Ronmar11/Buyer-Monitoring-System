/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;


import Information.Client;
import DB.ConnectionProvider;
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
public class ClientDB {
    public static void save(Client client){
        String query = "insert into client(clientID, fName, mName, lName, address, gender) values('"+client.getClientID()+"', '"+client.getfName()+"', '"+client.getmName()+"', '"+client.getlName()+"', '"+client.getaddress()+"', '"+client.getGender()+"')";
        DbOperations.setDataOrDelete(query, "Added Successfully!");   
    } 
    
    public static void update(Client client) {
        Connection conn = null;
        List<String> setClauses = new ArrayList<>();
        List<String> parameters = new ArrayList<>();
        

        if (client.getfName() != null && !client.getfName().trim().isEmpty()) {
            setClauses.add("fName = ?");
            parameters.add(client.getfName());
        }
        if (client.getmName() != null && !client.getmName().trim().isEmpty()) {
            setClauses.add("mName = ?");
            parameters.add(client.getmName());
        }
        if (client.getlName() != null && !client.getlName().trim().isEmpty()) {
            setClauses.add("lName = ?");
            parameters.add(client.getlName());
        }
        if (client.getaddress() != null && !client.getaddress().trim().isEmpty()) {
            setClauses.add("address = ?");
            parameters.add(client.getaddress());
        }
  
        if (client.getGender() != null && !client.getGender().trim().isEmpty()) {
            setClauses.add("gender = ?");
            parameters.add(client.getGender());
        }

        if (setClauses.isEmpty()) {
            System.out.println("ClientDB: No fields provided for update.");
            return;
        }

        String sql = "UPDATE client SET " + String.join(", ", setClauses) + " WHERE clientID = ?";
        parameters.add(client.getClientID()); 
        
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
                    System.out.println("ClientDB: Client " + client.getClientID() + " updated successfully.");
                } else {
                    System.out.println("ClientDB: Update failed for client " + client.getClientID() + ". ID may not exist.");
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientDB.class.getName()).log(Level.SEVERE, "Database Update Error", ex);
            throw new RuntimeException("Error updating client record: " + ex.getMessage(), ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    Logger.getLogger(ClientDB.class.getName()).log(Level.SEVERE, "Error closing connection", e);
                }
            }
        }
    }
    
    public static void delete(Client client) {
    Connection conn = null;
    
    // The DELETE SQL only requires the table name and the WHERE clause
    String sql = "DELETE FROM client WHERE clientID = ?";

    try {
        conn = ConnectionProvider.getCon();

        if (conn == null) {
            throw new SQLException("Database connection failed. Check ConnectionProvider.");
        }

        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            // Set the single parameter: clientID
            pst.setString(1, client.getClientID());

            int rows = pst.executeUpdate();

            if (rows > 0) {
                System.out.println("ClientDB: Client " + client.getClientID() + " deleted successfully.");
            } else {
                System.out.println("ClientDB: Deletion failed for client " + client.getClientID() + ". ID may not exist.");
            }
        }

    } catch (SQLException ex) {
        Logger.getLogger(ClientDB.class.getName()).log(Level.SEVERE, "Database Delete Error", ex);
        // Re-throw as a RuntimeException to be caught in the UI layer
        throw new RuntimeException("Error deleting client record: " + ex.getMessage(), ex);
    } finally {
        // Standard code to close the connection
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(ClientDB.class.getName()).log(Level.SEVERE, "Error closing connection", e);
            }
        }
    }
}
 
}


