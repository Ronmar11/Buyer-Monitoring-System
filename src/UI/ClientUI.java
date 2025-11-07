/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI;

import javax.swing.JOptionPane;
import Information.Client;
import DB.ClientDB;
import DB.ConnectionProvider;
import DB.DbOperations;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ronmar abalos
 */
public class ClientUI extends javax.swing.JFrame {

    /**
     * Creates new form Client
     */
    public ClientUI() {
        initComponents();
        btnSave.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        loadClientData();
        
    DefaultTableCellRenderer CenterAlign = new DefaultTableCellRenderer();
    CenterAlign.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    for (int i = 0; i < JTABLE.getColumnCount(); i++) {
    JTABLE.getColumnModel().getColumn(i).setCellRenderer(CenterAlign);
}
    }
    public void Clear(){
    clientID.setText("");
    firstName.setText("");
    midName.setText("");
    lastName.setText("");
    address.setText("");
    btnSave.setEnabled(false);
    }
   
    public void validateFields(){
    String id = clientID.getText();
    String Fname = firstName.getText();
    String Mname = midName.getText();
    String Lname = lastName.getText();
    String Address = address.getText();
    if(!id.equals("") && !Fname.equals("") && !Mname.equals("") && !Lname.equals("") && !Address.equals("")){
        btnSave.setEnabled(true);
    } else{
        btnSave.setEnabled(false);
    }
    if(!id.equals("")){
        btnUpdate.setEnabled(true);
    } else{
        btnUpdate.setEnabled(false);
    }
     if(!id.equals("")){
        btnDelete.setEnabled(true);
    } else{
        btnDelete.setEnabled(false);
    }
    
    }
    
    private void loadClientData() {
    DefaultTableModel model = (DefaultTableModel) JTABLE.getModel();
    model.setRowCount(0); 

    try (Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/bms", "root", "rrabalos");
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM client")) {

        while (rs.next()) {
            String id = rs.getString("clientID");
            String fname = rs.getString("fName");
            String mname = rs.getString("mName");
            String  lname = rs.getString("lName");
            String  address = rs.getString("address");
            String  gender = rs.getString("gender");
            
            model.addRow(new Object[]{id, fname, mname, lname, address, gender});
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
                "Error loading Client data: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
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
            String query = "SELECT * FROM client";

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
        jPanel8 = new javax.swing.JPanel();
        btnUpdate = new java.awt.Button();
        btnDelete = new java.awt.Button();
        button1 = new java.awt.Button();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        exit = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        firstName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        midName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lastName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        address = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        gender = new javax.swing.JComboBox<>();
        Clear = new java.awt.Button();
        btnSave = new java.awt.Button();
        jLabel12 = new javax.swing.JLabel();
        clientID = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        scrollPane1 = new java.awt.ScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTABLE = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jPanel8.setBackground(new java.awt.Color(0, 0, 51));

        btnUpdate.setBackground(new java.awt.Color(0, 0, 51));
        btnUpdate.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setLabel("UPDATE");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

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
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(button1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(0, 0, 51));

        jPanel3.setBackground(new java.awt.Color(0, 255, 255));

        jLabel1.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CLIENT");

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

        jPanel5.setBackground(new java.awt.Color(0, 0, 51));

        jLabel3.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("TRANSACTION");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

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

        javax.swing.GroupLayout exitLayout = new javax.swing.GroupLayout(exit);
        exit.setLayout(exitLayout);
        exitLayout.setHorizontalGroup(
            exitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exitLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );
        exitLayout.setVerticalGroup(
            exitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exitLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
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
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("First Name: ");
        jPanel9.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 70, 20));

        firstName.setBackground(new java.awt.Color(255, 255, 255));
        firstName.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        firstName.setForeground(new java.awt.Color(0, 0, 0));
        firstName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                firstNameKeyReleased(evt);
            }
        });
        jPanel9.add(firstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 280, 50));

        jLabel6.setFont(new java.awt.Font("Impact", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Mid Name: ");
        jPanel9.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 70, 20));

        midName.setBackground(new java.awt.Color(255, 255, 255));
        midName.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        midName.setForeground(new java.awt.Color(0, 0, 0));
        midName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                midNameKeyReleased(evt);
            }
        });
        jPanel9.add(midName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 280, 50));

        jLabel7.setFont(new java.awt.Font("Impact", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Last Name: ");
        jPanel9.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 70, 20));

        lastName.setBackground(new java.awt.Color(255, 255, 255));
        lastName.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lastName.setForeground(new java.awt.Color(0, 0, 0));
        lastName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lastNameKeyReleased(evt);
            }
        });
        jPanel9.add(lastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 280, 50));

        jLabel8.setFont(new java.awt.Font("Impact", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Address :");
        jPanel9.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 70, 20));

        address.setBackground(new java.awt.Color(255, 255, 255));
        address.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        address.setForeground(new java.awt.Color(0, 0, 0));
        address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressActionPerformed(evt);
            }
        });
        address.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                addressKeyReleased(evt);
            }
        });
        jPanel9.add(address, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 280, 80));

        jLabel9.setFont(new java.awt.Font("Impact", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Gender :");
        jPanel9.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 78, 38));

        gender.setBackground(new java.awt.Color(255, 255, 255));
        gender.setForeground(new java.awt.Color(0, 0, 51));
        gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        jPanel9.add(gender, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 400, 78, 38));

        Clear.setBackground(new java.awt.Color(0, 0, 51));
        Clear.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        Clear.setForeground(new java.awt.Color(255, 255, 255));
        Clear.setLabel("CLEAR");
        Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearActionPerformed(evt);
            }
        });
        jPanel9.add(Clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 400, 116, 39));

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
        jLabel12.setText("CLient ID:");
        jPanel9.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 70, 20));

        clientID.setBackground(new java.awt.Color(255, 255, 255));
        clientID.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        clientID.setForeground(new java.awt.Color(0, 0, 0));
        clientID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                clientIDKeyReleased(evt);
            }
        });
        jPanel9.add(clientID, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 280, 50));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        JTABLE.setBackground(new java.awt.Color(255, 255, 255));
        JTABLE.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Client ID", "First Name", "Middle Name", "Last Name", "Address", "Gender"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 11, Short.MAX_VALUE))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        Client client = new Client();
        
        client.setClientID(clientID.getText());
        client.setfName(firstName.getText());
        client.setmName(midName.getText());
        client.setlName(lastName.getText());
        client.setaddress(address.getText());
        client.setGender((String) gender.getSelectedItem());
        ClientDB.save(client);
        Clear();
        loadClientData();
                
    }//GEN-LAST:event_btnSaveActionPerformed

    private void ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearActionPerformed
        // TODO add your handling code here:
        Clear();
    }//GEN-LAST:event_ClearActionPerformed

    private void firstNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_firstNameKeyReleased
        // TODO add your handling code here:
        validateFields();
    }//GEN-LAST:event_firstNameKeyReleased

    private void midNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_midNameKeyReleased
        // TODO add your handling code here:
         validateFields();
    }//GEN-LAST:event_midNameKeyReleased

    private void lastNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lastNameKeyReleased
        // TODO add your handling code here:
         validateFields();
    }//GEN-LAST:event_lastNameKeyReleased

    private void addressKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addressKeyReleased
        // TODO add your handling code here:
         validateFields();
    }//GEN-LAST:event_addressKeyReleased

    private void addressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addressActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        ProductUI productUI = new ProductUI();
        productUI.setVisible(true);
        ClientUI.this.setVisible(false);
        
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
          TransactionUI transactionUI = new TransactionUI();
        transactionUI.setVisible(true);
        ClientUI.this.setVisible(false);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void clientIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clientIDKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_clientIDKeyReleased

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
       String id = clientID.getText();

    // 2. Validate that the ID is present
    if (id == null || id.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Client ID is required for deleting a record.", "Missing ID", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // 3. Optional: Add a confirmation dialog for safety
    int dialogResult = JOptionPane.showConfirmDialog(this,
        "Are you sure you want to delete client ID: " + id + "? This action cannot be undone.",
        "Confirm Deletion",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE);

    if (dialogResult == JOptionPane.NO_OPTION) {
        return; // Stop if the user cancels
    }

    // 4. Create a minimal Client object (only need the ID)
    Client client = new Client();
    client.setClientID(id);

    // 5. Call the new delete method
    try {
        ClientDB.delete(client); // <-- Calling the new delete method
        JOptionPane.showMessageDialog(this, "Client record for ID " + id + " deleted successfully.");

        Clear(); // Clear the form fields
        loadClientData(); // Reload the table data

    } catch (RuntimeException e) {
        // Catch the RuntimeException thrown by ClientDB on SQL failure
        JOptionPane.showMessageDialog(this, "Deletion failed: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        // Log the full exception
        Logger.getLogger(ClientUI.class.getName()).log(Level.SEVERE, "Client Deletion failed in UI", e);
    }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
 
        String id = clientID.getText();
        String fname = firstName.getText();
        String mname = midName.getText();
        String lname = lastName.getText();
        String addressValue = address.getText();
        String genderValue = (String) gender.getSelectedItem();

   
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Client ID is required for updating a record.", "Missing ID", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Client client = new Client();
        client.setClientID(id); 
        client.setfName(fname);
        client.setmName(mname);
        client.setlName(lname);
        client.setaddress(addressValue);
        client.setGender(genderValue);

        
        try {
            ClientDB.update(client); 
            JOptionPane.showMessageDialog(this, "Client record updated successfully.");

            Clear();
            loadClientData();

        } catch (RuntimeException e) {
            // Catch the RuntimeException thrown by ClientDB on SQL failure or connection failure
            JOptionPane.showMessageDialog(this, "Update failed: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            // You might still want to log the full exception for debugging
            Logger.getLogger(ClientUI.class.getName()).log(Level.SEVERE, "Client Update failed in UI", e);
        }
      
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
        exportData();
    }//GEN-LAST:event_button1ActionPerformed

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
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button Clear;
    private javax.swing.JTable JTABLE;
    private javax.swing.JTextField address;
    private java.awt.Button btnDelete;
    private java.awt.Button btnSave;
    private java.awt.Button btnUpdate;
    private java.awt.Button button1;
    private javax.swing.JTextField clientID;
    private javax.swing.JPanel exit;
    private javax.swing.JTextField firstName;
    private javax.swing.JComboBox<String> gender;
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
    private javax.swing.JTextField lastName;
    private javax.swing.JTextField midName;
    private java.awt.ScrollPane scrollPane1;
    // End of variables declaration//GEN-END:variables
}
