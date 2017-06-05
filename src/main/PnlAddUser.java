/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Phantom
 */
public class PnlAddUser extends javax.swing.JPanel {

    /**
     * Creates new form PnlUser
     */
    
    private Connection myConn = null;
    private PreparedStatement myStmt = null;
    private ResultSet myRs = null;
    
    public PnlAddUser(Connection conn) {
        myConn=conn;
        initComponents();
    }
    
    public void refresh(){
        userList(tblUserUserList1);
    }
    
    private void createUser() {
        try {
            // Prepare statement
            myStmt = myConn
                    .prepareStatement("INSERT INTO `akuntansi`.`user` (`username`, `password`) VALUES (?,?);");

            myStmt.setString(1, txtUserAddUsername.getText());
            myStmt.setString(2, txtUserAddPassword.getText());

            // Execute SQL query
            myStmt.executeUpdate();
            txtUserAddPassword.setText("");
            txtUserAddPassword.setText("");
            txtUserConfrimPassword.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(FrmMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void userList(JTable tbl) {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) tbl.getModel();
            for (int i = tbl.getRowCount() - 1; i >= 0; i--) {
                tableModel.removeRow(i);
            }

            myStmt = myConn.prepareStatement("select username from user;");

            // Execute SQL query
            myRs = myStmt.executeQuery();
            // Process result set
            if (myRs.isBeforeFirst()) {
                while (myRs.next()) {
                    String name = myRs.getString("username");
                    Object data[] = {name};
                    tableModel.addRow(data);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DlgLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void clear() {
        txtUserAddPassword.setText("");
        txtUserAddUsername.setText("");
        txtUserConfrimPassword.setText("");
    }
    
    private void confirmPassword(){
        if (txtUserAddPassword.getText().equals(txtUserConfrimPassword.getText())) {
            createUser();
            JOptionPane.showMessageDialog(this, "Insert Successful!");
            userList(tblUserUserList1);
            clear();
        } else {
            JOptionPane.showMessageDialog(this, "Password don't match!");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnUserCreateUser = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUserUserList1 = new javax.swing.JTable();
        txtUserConfrimPassword = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        txtUserAddPassword = new javax.swing.JPasswordField();
        txtUserAddUsername = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        btnUserCreateUser.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnUserCreateUser.setText("Create User");
        btnUserCreateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserCreateUserActionPerformed(evt);
            }
        });

        tblUserUserList1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblUserUserList1);

        txtUserConfrimPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserConfrimPasswordActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Confirm Password :");

        txtUserAddPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserAddPasswordActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Username :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Password :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(543, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(7, 7, 7)
                                    .addComponent(jLabel2)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtUserAddPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtUserAddUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtUserConfrimPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(89, 89, 89))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnUserCreateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(165, 165, 165)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(350, 350, 350))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(292, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txtUserAddUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtUserAddPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtUserConfrimPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(128, 128, 128)
                        .addComponent(btnUserCreateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(244, 244, 244))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(206, 206, 206))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtUserAddPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserAddPasswordActionPerformed
        confirmPassword();
    }//GEN-LAST:event_txtUserAddPasswordActionPerformed

    private void txtUserConfrimPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserConfrimPasswordActionPerformed
        confirmPassword();
    }//GEN-LAST:event_txtUserConfrimPasswordActionPerformed

    private void btnUserCreateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserCreateUserActionPerformed
        confirmPassword();
    }//GEN-LAST:event_btnUserCreateUserActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUserCreateUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUserUserList1;
    private javax.swing.JPasswordField txtUserAddPassword;
    private javax.swing.JTextField txtUserAddUsername;
    private javax.swing.JPasswordField txtUserConfrimPassword;
    // End of variables declaration//GEN-END:variables
}
