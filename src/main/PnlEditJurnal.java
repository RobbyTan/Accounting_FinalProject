package main;

import injection.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import util.SUtility;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USER
 */
public class PnlEditJurnal extends javax.swing.JPanel {

    /**
     * Creates new form PanelCreateJurnal
     */
    private Connection myConn = null;
    private PreparedStatement myStmt = null;
    private ResultSet myRs = null;

    Inject inject;

    public PnlEditJurnal(Connection conn, Inject inject) {
        initComponents();
        myConn = conn;
        this.inject = inject;
        generateComboBoxJurnalNo();
    }

    public void generateTable() {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) tblEditJurnal.getModel();
            for (int i = tblEditJurnal.getRowCount() - 1; i >= 0; i--) {
                tableModel.removeRow(i);
            }

            myStmt = myConn.prepareStatement("select * from jurnal_fulldata where jurnal_no=?;");
            // Execute SQL query
            myStmt.setString(1, cboJurnalList.getSelectedItem().toString());
            myRs = myStmt.executeQuery();
            // Process result set
            if (myRs.isBeforeFirst()) {
                while (myRs.next()) {
                    Object data[] = {myRs.getString("chart_no"), myRs.getString("chart_name"), myRs.getDouble("debit"), myRs.getDouble("kredit")};
                    tableModel.addRow(data);
                    txtEditJurnalDate.setText(myRs.getString("date"));
                    txaCreateJurnalDescription.setText(myRs.getString("description"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DlgLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateComboBoxJurnalNo() {
        try {
            cboJurnalList.removeAllItems();
            myStmt = myConn.prepareStatement("select * from jurnal_fulldata group by jurnal_no");
            // Execute SQL query
            myRs = myStmt.executeQuery();
            ArrayList<String> jurnalNo = new ArrayList<>();
            // Process result set
            while (myRs.next()) {
                jurnalNo.add(myRs.getString("jurnal_no"));
            }
            for (String a : jurnalNo) {
                cboJurnalList.addItem(a);
            }
            generateTable();
        } catch (SQLException ex) {
            Logger.getLogger(DlgCreateJurnalAddJurnalTransaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void editTableDetail() {
        try {
            myStmt = myConn.prepareStatement("delete from jurnal_detail where jurnal_no=?");
            myStmt.setString(1, cboJurnalList.getSelectedItem().toString());

            // Execute SQL query
            myStmt.executeUpdate();
            System.out.println("delete");
            for (int i = 0; i < tblEditJurnal.getRowCount(); i++) {
                // Prepare statement

                myStmt = myConn.prepareStatement("INSERT INTO `akuntansi`.`jurnal_detail` "
                        + "(`jurnal_no`, `chart_no`, `chart_name`, `debit`, `kredit`, `date`) VALUES"
                        + " (?,?,?,?,?,?);");
                myStmt.setString(1, cboJurnalList.getSelectedItem().toString());
                myStmt.setString(2, tblEditJurnal.getValueAt(i, 0).toString());
                myStmt.setString(3, tblEditJurnal.getValueAt(i, 1).toString());
                myStmt.setString(4, tblEditJurnal.getValueAt(i, 2).toString());
                myStmt.setString(5, tblEditJurnal.getValueAt(i, 3).toString());
                myStmt.setString(6, txtEditJurnalDate.getText());
                // Execute SQL query
                myStmt.executeUpdate();
                System.out.println("add");

            }
            generateTable();
            SUtility.msg(this, "Update Saved!");
        } catch (SQLException ex) {
            Logger.getLogger(PnlAccountChart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void editTableMaster() {
        try {
            myStmt = myConn.prepareStatement("delete from jurnal_master where jurnal_no=?");
            myStmt.setString(1, cboJurnalList.getSelectedItem().toString());

            // Execute SQL query
            myStmt.executeUpdate();
            System.out.println("delete");
            for (int i = 0; i < tblEditJurnal.getRowCount(); i++) {
                // Prepare statement

                myStmt = myConn.prepareStatement("INSERT INTO `akuntansi`.`jurnal_master` (`jurnal_no`, "
                        + "`date`, `description`) VALUES (?,?,?);");
                myStmt.setString(1, cboJurnalList.getSelectedItem().toString());
                myStmt.setString(2, txtEditJurnalDate.getText());
                myStmt.setString(3, txaCreateJurnalDescription.getText());

                // Execute SQL query
                myStmt.executeUpdate();
                System.out.println("add");

            }
        } catch (SQLException ex) {
            Logger.getLogger(PnlAccountChart.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaCreateJurnalDescription = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEditJurnal = new javax.swing.JTable();
        btnCreateJurnalSave = new javax.swing.JButton();
        cboJurnalList = new javax.swing.JComboBox<>();
        txtEditJurnalDate = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Date :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Jurnal :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Description :");

        txaCreateJurnalDescription.setColumns(20);
        txaCreateJurnalDescription.setRows(5);
        jScrollPane1.setViewportView(txaCreateJurnalDescription);

        tblEditJurnal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chart No", "Chart Name", "Debit", "Kredit"
            }
        ));
        jScrollPane2.setViewportView(tblEditJurnal);
        if (tblEditJurnal.getColumnModel().getColumnCount() > 0) {
            tblEditJurnal.getColumnModel().getColumn(0).setResizable(false);
            tblEditJurnal.getColumnModel().getColumn(1).setResizable(false);
            tblEditJurnal.getColumnModel().getColumn(2).setResizable(false);
            tblEditJurnal.getColumnModel().getColumn(3).setResizable(false);
        }

        btnCreateJurnalSave.setText("Save Jurnal");
        btnCreateJurnalSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateJurnalSaveActionPerformed(evt);
            }
        });

        cboJurnalList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboJurnalListActionPerformed(evt);
            }
        });

        txtEditJurnalDate.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(387, 387, 387)
                .addComponent(btnCreateJurnalSave)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboJurnalList, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEditJurnalDate, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 692, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtEditJurnalDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(50, 50, 50)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboJurnalList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCreateJurnalSave)
                .addGap(26, 26, 26))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateJurnalSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateJurnalSaveActionPerformed
        int x = SUtility.msq(this, "Are you sure?");
        if (x == 0) {
            editTableMaster();
            editTableDetail();
        }
    }//GEN-LAST:event_btnCreateJurnalSaveActionPerformed

    private void cboJurnalListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboJurnalListActionPerformed
        generateTable();
    }//GEN-LAST:event_cboJurnalListActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateJurnalSave;
    private javax.swing.JComboBox<String> cboJurnalList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblEditJurnal;
    private javax.swing.JTextArea txaCreateJurnalDescription;
    private javax.swing.JTextField txtEditJurnalDate;
    // End of variables declaration//GEN-END:variables
}
