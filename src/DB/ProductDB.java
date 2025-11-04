/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;
import Information.Product;
/**
 *
 * @author Ronmar abalos
 */
public class ProductDB {
     public static void save(Product product){
    String query = "insert into product(txtName, txtCode, txtProduct, txtCategory) values('"+product.getName()+"', '"+product.getCode()+"', '"+product.getProduct()+"', '"+product.getCategory()+"')";
    DbOperations.setDataOrDelete(query, "Added Successfully!");
     }
}
