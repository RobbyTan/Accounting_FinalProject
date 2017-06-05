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
public class PnlDeleteUser extends javax.swing.JPanel {

    /**
     * Creates new form PnlDeleteUser
     */
    private Connection myConn = null;
    private PreparedStatement myStmt = null;
    private ResultSet myRs = null;
    
    public PnlDeleteUser( Connection conn) {
        initComponents();
        myConn=conn;
    }
    
    public void refresh(){
        userList(tblUserUserList);
    }
    
    private void executeDeleteUser() {

        int row = tblUserUserList.getSelectedRow();
        DefaultTableModel tableModel = (DefaultTableModel) tblUserUserList.getModel();

        try {
            // Prepare statement
            myStmt = myConn
                    .prepareStatement("delete from user where username=?;");

            myStmt.setString(1, tblUserUserList.getValueAt(row, 0).toString());

            // Execute SQL query
            myStmt.executeUpdate();
            tableModel.removeRow(row);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnUserDeleteUser = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUserUserList = new javax.swing.JTable();

        btnUserDeleteUser.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnUserDeleteUser.setText("Delete");
        btnUserDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserDeleteUserActionPerformed(evt);
            }
        });

        tblUserUserList.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblUserUserList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(493, 493, 493)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(552, 552, 552)
                        .addComponent(btnUserDeleteUser, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(828, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(224, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(btnUserDeleteUser, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(186, 186, 186))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnUserDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserDeleteUserActionPerformed
        int x = JOptionPane.showConfirmDialog(this, "Are You sure?", "warning", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            executeDeleteUser();
            userList(tblUserUserList);
        }
    }//GEN-LAST:event_btnUserDeleteUserActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUserDeleteUser;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblUserUserList;
    // End of variables declaration//GEN-END:variables
}
