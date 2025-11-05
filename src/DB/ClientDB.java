/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;


import Information.Client;
import java.sql.PreparedStatement;
import DB.ConnectionProvider; // Import your ConnectionProvider class
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Ronmar abalos
 */
public class ClientDB {
    public static void save(Client client){
        String query = "insert into client(fName, mName, lName, address, gender) values('"+client.getfName()+"', '"+client.getmName()+"', '"+client.getlName()+"', '"+client.getaddress()+"', '"+client.getGender()+"')";
        DbOperations.setDataOrDelete(query, "Added Successfully!");   
    } 
}
