/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Information;

import java.util.Date;

/**
 *
 * @author Ronmar abalos
 */
public class Transaction {
    
   private String buyID;
   private int Price;
   private int Quantity;
   private String clientName;
   private String productName;
   private String Date;

    public String getBuyID() {
        return buyID;
    }

    public void setBuyID(String buyID) {
        this.buyID = buyID;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDate(){
        return Date;
    }
    
    public void setDate(String Date){
        this.Date = Date;
    }
 
}
