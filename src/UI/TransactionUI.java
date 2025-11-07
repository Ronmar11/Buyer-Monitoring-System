/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI;

import javax.swing.JOptionPane;
import Information.Transaction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import DB.ConnectionProvider;
import java.util.logging.Level;
import java.util.logging.Logger;
import DB.TransactionDB;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ronmar abalos
 */
public class TransactionUI extends javax.swing.JFrame {

    /**
     * Creates new form Client
     */
    public TransactionUI() {
        initComponents();
        btnSave.setEnabled(false);
        
        loadClients();
        loadProducts();
        loadTransactionData();
        txtClientTransaction.setEnabled(true);
        txtProductTransaction.setEnabled(true);
         
        DefaultTableCellRenderer CenterAlign = new DefaultTableCellRenderer();
        CenterAlign.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        for (int i = 0; i < JTABLE.getColumnCount(); i++) {
        JTABLE.getColumnModel().getColumn(i).setCellRenderer(CenterAlign);
        
    }
    }
    public void Clear(){
    txtbuyID.setText("");
    txtQuantityTransaction.setText("");
    txtPriceTransaction.setText("");
    btnSave.setEnabled(false);
    
    }
   
public void validateFields(){
    String id = txtbuyID.getText();
    Object Fname = txtClientTransaction.getSelectedItem();
    Object Mname = txtProductTransaction.getSelectedItem();
    String Lname = txtQuantityTransaction.getText();
    String tran = txtPriceTransaction.getText();
    if(!id.equals("") && !Fname.equals("") && !Mname.equals("") && !Lname.equals("") && !tran.equals("")){
        btnSave.setEnabled(true);
    } else{
        btnSave.setEnabled(false);
    }
     if(!id.equals("")){
        btnDelete.setEnabled(true);
    } else{
        btnDelete.setEnabled(false);
    }
    }
private void loadTransactionData() {
    DefaultTableModel model = (DefaultTableModel) JTABLE.getModel();
    model.setRowCount(0); 

    try (Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/bms", "root", "rrabalos");
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM transaction")) {

        while (rs.next()) {
            String id = rs.getString("buyID");
            String clName = rs.getString("clientName");
            String pName = rs.getString("productName");
            int  quan = rs.getInt("Quantity");
            int  price = rs.getInt("price");
            String  date = rs.getString("Date");
            
            model.addRow(new Object[]{id, clName, pName, quan, price, date});
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
                "Error loading transaction data: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
    }
} 
public void loadClients() {
    txtClientTransaction.removeAllItems();

    try (Connection con = ConnectionProvider.getCon();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery("SELECT clientID, TRIM(CONCAT(fName, ' ', mName, ' ', lName)) as FullName FROM client")) {

        if (con == null) {
            throw new RuntimeException("Database connection failed.");
        }

        // Add the placeholder item (always at index 0)
        txtClientTransaction.addItem("Select Client..."); 

        // Populate the JComboBox
        while (rs.next()) {
            String clientID = rs.getString("clientID");
            String fullName = rs.getString("FullName");
            txtClientTransaction.addItem(fullName);
        }  
    } catch (Exception e) {
        System.err.println("Error loading clients: " + e.getMessage());
        // Assumes 'this' is a Component like a JFrame/JPanel
        JOptionPane.showMessageDialog(null, "Error loading client list: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}

public void loadProducts() {
    txtProductTransaction.removeAllItems();

    try (Connection con = ConnectionProvider.getCon();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery("SELECT productID, txtName FROM product")) {

        if (con == null) {
            throw new RuntimeException("Database connection failed.");
        }

        // Add the placeholder item (always at index 0)
        txtProductTransaction.addItem("Select Product..."); 

        // Populate the JComboBox
        while (rs.next()) {
            String productID = rs.getString("productID");
            String productName = rs.getString("txtName");
            txtProductTransaction.addItem( productName);
        }

    } catch (Exception e) {
        System.err.println("Error loading products: " + e.getMessage());
        // Assumes 'this' is a Component like a JFrame/JPanel
        JOptionPane.showMessageDialog(null, "Error loading product list: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}
private void exportData() {
        // chooser object allows user
        // to choose file location
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save CSV File");
        
        // setting automatic filename format when exporting
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new java.util.Date());
        chooser.setSelectedFile(new java.io.File("export_" + timestamp + ".csv"));
        int userSelection = chooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // selecting location to save
            File fileToSave = chooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
          
            // querying
            String query = "SELECT buyID, clientName,  productName, quantity, price, date FROM transaction";

            try (
                 // database connection
                 Connection conn = ConnectionProvider.getCon();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query);
                 PrintWriter pw = new PrintWriter(new FileWriter(filePath))
            ) {

                // data for getting column count
                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();

                // Write header
                for (int i = 1; i <= columnCount; i++) {
                    pw.print(meta.getColumnName(i));
                    if (i < columnCount) pw.print(",");
                }
                pw.println();

                // Write data rows
                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        pw.print(rs.getString(i));
                        if (i < columnCount) pw.print(",");
                    }
                    pw.println();
                }

                JOptionPane.showMessageDialog(this, "Data exported successfully to:\n" + filePath);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }


    
    
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        exit = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cid = new javax.swing.JLabel();
        pid = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtClientTransaction = new javax.swing.JComboBox<>();
        txtProductTransaction = new javax.swing.JComboBox<>();
        txtQuantityTransaction = new javax.swing.JTextField();
        txtPriceTransaction = new javax.swing.JTextField();
        btnSave = new java.awt.Button();
        jLabel12 = new javax.swing.JLabel();
        txtbuyID = new javax.swing.JTextField();
        txtDate = new com.toedter.calendar.JDateChooser();
        jPanel7 = new javax.swing.JPanel();
        scrollPane1 = new java.awt.ScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTABLE = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        btnDelete = new java.awt.Button();
        button1 = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 0, 51));

        jPanel3.setBackground(new java.awt.Color(0, 0, 51));

        jLabel1.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CLIENT");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(0, 0, 51));

        jLabel2.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("PRODUCT");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(0, 255, 255));

        jLabel3.setBackground(new java.awt.Color(102, 255, 255));
        jLabel3.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("TRANSACTION");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                .addContainerGap())
        );

        exit.setBackground(new java.awt.Color(0, 0, 51));
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exit(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 0, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("x");

        cid.setEnabled(false);

        pid.setEnabled(false);

        javax.swing.GroupLayout exitLayout = new javax.swing.GroupLayout(exit);
        exit.setLayout(exitLayout);
        exitLayout.setHorizontalGroup(
            exitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exitLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(cid, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exitLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pid, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        exitLayout.setVerticalGroup(
            exitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exitLayout.createSequentialGroup()
                .addGroup(exitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(exitLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(exitLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(cid)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(pid)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Impact", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("BuyID");
        jPanel9.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 90, 20));

        jLabel6.setFont(new java.awt.Font("Impact", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Product");
        jPanel9.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 50, -1));

        jLabel7.setFont(new java.awt.Font("Impact", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Quantity:");
        jPanel9.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 60, -1));

        jLabel8.setFont(new java.awt.Font("Impact", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Price :");
        jPanel9.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 50, 20));

        jLabel9.setFont(new java.awt.Font("Impact", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Date:");
        jPanel9.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 30, 30));

        txtClientTransaction.setBackground(new java.awt.Color(255, 255, 255));
        txtClientTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClientTransactionActionPerformed(evt);
            }
        });
        jPanel9.add(txtClientTransaction, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 280, 50));

        txtProductTransaction.setBackground(new java.awt.Color(255, 255, 255));
        txtProductTransaction.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        txtProductTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductTransactionActionPerformed(evt);
            }
        });
        jPanel9.add(txtProductTransaction, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 280, 50));

        txtQuantityTransaction.setBackground(new java.awt.Color(255, 255, 255));
        txtQuantityTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantityTransactionActionPerformed(evt);
            }
        });
        txtQuantityTransaction.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQuantityTransactionKeyReleased(evt);
            }
        });
        jPanel9.add(txtQuantityTransaction, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 280, 50));

        txtPriceTransaction.setBackground(new java.awt.Color(255, 255, 255));
        txtPriceTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriceTransactionActionPerformed(evt);
            }
        });
        txtPriceTransaction.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPriceTransactionKeyReleased(evt);
            }
        });
        jPanel9.add(txtPriceTransaction, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 280, 50));

        btnSave.setBackground(new java.awt.Color(0, 0, 51));
        btnSave.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setLabel("ADD");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel9.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 280, 40));

        jLabel12.setFont(new java.awt.Font("Impact", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Client");
        jPanel9.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 40, -1));

        txtbuyID.setBackground(new java.awt.Color(255, 255, 255));
        txtbuyID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuyIDActionPerformed(evt);
            }
        });
        txtbuyID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuyIDKeyReleased(evt);
            }
        });
        jPanel9.add(txtbuyID, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 280, 50));
        jPanel9.add(txtDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 400, 240, 40));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        JTABLE.setBackground(new java.awt.Color(255, 255, 255));
        JTABLE.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "BuyID", "Client Name", "Product Name", "Quantity", "Price", "Date"
            }
        ));
        JTABLE.setToolTipText("");
        jScrollPane1.setViewportView(JTABLE);

        scrollPane1.add(jScrollPane1);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(0, 0, 51));

        btnDelete.setBackground(new java.awt.Color(0, 0, 51));
        btnDelete.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setLabel("DELETE");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        button1.setBackground(new java.awt.Color(0, 0, 51));
        button1.setForeground(new java.awt.Color(255, 255, 255));
        button1.setLabel("Export Data");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 315, Short.MAX_VALUE)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(button1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exit(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exit
        // TODO add your handling code here:
        int a = JOptionPane.showConfirmDialog(null, "Do you really want to close application?", "Select", JOptionPane.YES_NO_OPTION);
        if(a == 0){
            System.exit(0);
        }
    }//GEN-LAST:event_exit

    private void txtClientTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClientTransactionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClientTransactionActionPerformed

    private void txtProductTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductTransactionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductTransactionActionPerformed

    private void txtPriceTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceTransactionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriceTransactionActionPerformed

    private void txtQuantityTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantityTransactionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantityTransactionActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        
        Transaction transaction = new Transaction();
        transaction.setBuyID(txtbuyID.getText());
        transaction.setClientName((String) txtClientTransaction.getSelectedItem());
        transaction.setProductName((String) txtProductTransaction.getSelectedItem());
  
       try {
            int quantity = Integer.parseInt(txtQuantityTransaction.getText().trim());
            transaction.setQuantity(quantity);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity number", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int price = Integer.parseInt(txtPriceTransaction.getText().trim());
            transaction.setPrice(price);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

            // Get date from JDateChooser
            Date selectedDate = txtDate.getDate();
            if (selectedDate == null) {
                JOptionPane.showMessageDialog(this, "Please select a date", "Missing Date", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Convert Date to String
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = sdf.format(selectedDate);
            transaction.setDate(dateString);

            TransactionDB.save(transaction);
            Clear();
            loadTransactionData();


    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtbuyIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuyIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuyIDActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
         ProductUI productUI = new ProductUI();
        productUI.setVisible(true);
        TransactionUI.this.setVisible(false);
        
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
         ClientUI clientUI = new ClientUI();
        clientUI.setVisible(true);
        TransactionUI.this.setVisible(false);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String id = txtbuyID.getText();

        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Transaction ID is required for deleting a record.", "Missing ID", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Optional: Add a confirmation dialog for safety
        int dialogResult = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete Transaction ID: " + id + "? This action cannot be undone.",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (dialogResult == JOptionPane.NO_OPTION) {
            return; // Stop if the user cancels
        }

        // 4. Create a minimal Client object (only need the ID)
        Transaction transaction = new Transaction();
        transaction.setBuyID(id);

        // 5. Call the new delete method
        try {
            TransactionDB.delete(transaction);
            JOptionPane.showMessageDialog(this, "Transaction record for ID " + id + " deleted successfully.");
            Clear();
            loadTransactionData();
        } catch (RuntimeException e) {
          
            JOptionPane.showMessageDialog(this, "Deletion failed: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            // Log the full exception
            Logger.getLogger(TransactionUI.class.getName()).log(Level.SEVERE, "Transaction Deletion failed in UI", e);
        }

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
        exportData();
    }//GEN-LAST:event_button1ActionPerformed

    private void txtbuyIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuyIDKeyReleased
        // TODO add your handling code here:
        validateFields();
    }//GEN-LAST:event_txtbuyIDKeyReleased

    private void txtQuantityTransactionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQuantityTransactionKeyReleased
        // TODO add your handling code here:
        validateFields();
    }//GEN-LAST:event_txtQuantityTransactionKeyReleased

    private void txtPriceTransactionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriceTransactionKeyReleased
        // TODO add your handling code here:
        validateFields();
    }//GEN-LAST:event_txtPriceTransactionKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TransactionUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransactionUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransactionUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransactionUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransactionUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTABLE;
    private java.awt.Button btnDelete;
    private java.awt.Button btnSave;
    private java.awt.Button button1;
    private javax.swing.JLabel cid;
    private javax.swing.JPanel exit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel pid;
    private java.awt.ScrollPane scrollPane1;
    private javax.swing.JComboBox<String> txtClientTransaction;
    private com.toedter.calendar.JDateChooser txtDate;
    private javax.swing.JTextField txtPriceTransaction;
    private javax.swing.JComboBox<String> txtProductTransaction;
    private javax.swing.JTextField txtQuantityTransaction;
    private javax.swing.JTextField txtbuyID;
    // End of variables declaration//GEN-END:variables
}
