/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Information;

/**
 *
 * @author Ronmar abalos
 */
public class Client {
   
    private int clientID;
    private String fName;
    private String mName;
    private String lName;
    private String address;
    private String gender;
   
    public void setclientID(int clientID) {
        this.clientID = clientID;
    }

    public int getclientID(){
        return clientID;
    }
    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }
    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public Object[] toTableRow() {
        return new Object[]{clientID, fName, mName, lName, address, gender};
    }
    
    
}
