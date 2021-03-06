package main;

import injection.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
public class PnlAccountChart extends javax.swing.JPanel {

    /**
     * Creates new form PanelAccountChart
     */
    private Connection myConn = null;
    private PreparedStatement myStmt = null;
    private ResultSet myRs = null;
    
    Inject inject;
    int row;

    public PnlAccountChart(Connection conn,Inject inject) {
        initComponents();
        myConn = conn;
        this.inject=inject;
        if (rdoAccountChartEditOff.isSelected()) {
            btnAccountChartSave.setVisible(false);
            btnAccountChartReset.setVisible(false);
        } else {
            btnAccountChartSave.setVisible(true);
            btnAccountChartReset.setVisible(true);
        }
        tableSelectionListener();
        
    }

    private void executeAccountChartDelete() {
        int x = SUtility.msq(this, "Are you sure?");
        if (x == 0) {
            try {
                // Prepare statement
                myStmt = myConn
                        .prepareStatement("delete from account_chart where chart_no=?");

                myStmt.setString(1, tblAccountChart.getValueAt(row, 0).toString());

                // Execute SQL query
                myStmt.executeUpdate();
                generateTable();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input");
                Logger.getLogger(DlgAddAccountChart.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void tableSelectionListener() {
        ListSelectionListener listener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                row = tblAccountChart.getSelectedRow();
                if (row >= 0) {
                    //txtId.setText(tblStudents.getValueAt(row, 0).toString());
                }
            }
        };
        tblAccountChart.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblAccountChart.getSelectionModel().addListSelectionListener(listener);
    }

    public void generateTable() {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) tblAccountChart.getModel();
            for (int i = tblAccountChart.getRowCount() - 1; i >= 0; i--) {
                tableModel.removeRow(i);
            }

            myStmt = myConn.prepareStatement("select * from account_chart;");
            // Execute SQL query
            myRs = myStmt.executeQuery();
            // Process result set
            if (myRs.isBeforeFirst()) {
                while (myRs.next()) {
                    Object data[] = {myRs.getString("chart_no"), myRs.getString("chart_name"), myRs.getString("type")};
                    tableModel.addRow(data);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DlgLogin.class.getName()).log(Level.SEVERE, null, ex);
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

        EditableOnOff = new javax.swing.ButtonGroup();
        btnAccountChartAdd = new javax.swing.JButton();
        btnAccountChartDelete = new javax.swing.JButton();
        rdoAccountChartEditOn = new javax.swing.JRadioButton();
        rdoAccountChartEditOff = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAccountChart = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnAccountChartSave = new javax.swing.JButton();
        btnAccountChartReset = new javax.swing.JButton();

        btnAccountChartAdd.setText("New");
        btnAccountChartAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccountChartAddActionPerformed(evt);
            }
        });

        btnAccountChartDelete.setText("Delete");
        btnAccountChartDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccountChartDeleteActionPerformed(evt);
            }
        });

        EditableOnOff.add(rdoAccountChartEditOn);
        rdoAccountChartEditOn.setText("On");
        rdoAccountChartEditOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoAccountChartEditOnActionPerformed(evt);
            }
        });

        EditableOnOff.add(rdoAccountChartEditOff);
        rdoAccountChartEditOff.setSelected(true);
        rdoAccountChartEditOff.setText("Off");
        rdoAccountChartEditOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoAccountChartEditOffActionPerformed(evt);
            }
        });

        tblAccountChart.setAutoCreateRowSorter(true);
        tblAccountChart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chart No", "Chart Name", "Type"
            }
        ));
        jScrollPane1.setViewportView(tblAccountChart);
        if (tblAccountChart.getColumnModel().getColumnCount() > 0) {
            tblAccountChart.getColumnModel().getColumn(0).setResizable(false);
            tblAccountChart.getColumnModel().getColumn(1).setResizable(false);
            tblAccountChart.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Edit :");

        btnAccountChartSave.setText("Save");
        btnAccountChartSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccountChartSaveActionPerformed(evt);
            }
        });

        btnAccountChartReset.setText("Reset");
        btnAccountChartReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccountChartResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(171, 171, 171)
                        .addComponent(btnAccountChartAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(jLabel1)
                        .addGap(30, 30, 30)
                        .addComponent(rdoAccountChartEditOn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoAccountChartEditOff)
                        .addGap(57, 57, 57)
                        .addComponent(btnAccountChartDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAccountChartReset, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAccountChartSave, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(162, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAccountChartAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAccountChartDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoAccountChartEditOn)
                    .addComponent(rdoAccountChartEditOff)
                    .addComponent(jLabel1)
                    .addComponent(btnAccountChartSave, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAccountChartReset, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rdoAccountChartEditOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoAccountChartEditOnActionPerformed
        btnAccountChartSave.setVisible(true);
        btnAccountChartReset.setVisible(true);
//        tblAccountChart.is
    }//GEN-LAST:event_rdoAccountChartEditOnActionPerformed

    private void rdoAccountChartEditOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoAccountChartEditOffActionPerformed
        btnAccountChartSave.setVisible(false);
        btnAccountChartReset.setVisible(false);
        generateTable();
    }//GEN-LAST:event_rdoAccountChartEditOffActionPerformed

    private void btnAccountChartDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccountChartDeleteActionPerformed
        executeAccountChartDelete();
    }//GEN-LAST:event_btnAccountChartDeleteActionPerformed

    private void btnAccountChartAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccountChartAddActionPerformed
        DlgAddAccountChart accountChart = new DlgAddAccountChart(this, true, myConn);
        accountChart.setVisible(true);
    }//GEN-LAST:event_btnAccountChartAddActionPerformed

    private void btnAccountChartSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccountChartSaveActionPerformed
        int x = SUtility.msq(this, "Are you sure?");
        if (x == 0) {
            try {
                myStmt = myConn.prepareStatement("delete from account_chart");

                // Execute SQL query
                myStmt.executeUpdate();
                System.out.println("delete");
                for (int i = 0; i < tblAccountChart.getRowCount(); i++) {
                    // Prepare statement

                    myStmt = myConn.prepareStatement("insert into account_chart values (?,?,?)");

                    myStmt.setString(1, tblAccountChart.getValueAt(i, 0).toString());
                    myStmt.setString(2, tblAccountChart.getValueAt(i, 1).toString());
                    myStmt.setString(3, tblAccountChart.getValueAt(i, 2).toString());

                    // Execute SQL query
                    myStmt.executeUpdate();
                    System.out.println("add");
                    
                }SUtility.msg(this, "Update Saved!");
            } catch (SQLException ex) {
                Logger.getLogger(PnlAccountChart.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnAccountChartSaveActionPerformed

    private void btnAccountChartResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccountChartResetActionPerformed
        generateTable();
    }//GEN-LAST:event_btnAccountChartResetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup EditableOnOff;
    private javax.swing.JButton btnAccountChartAdd;
    private javax.swing.JButton btnAccountChartDelete;
    private javax.swing.JButton btnAccountChartReset;
    private javax.swing.JButton btnAccountChartSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoAccountChartEditOff;
    private javax.swing.JRadioButton rdoAccountChartEditOn;
    private javax.swing.JTable tblAccountChart;
    // End of variables declaration//GEN-END:variables
}
