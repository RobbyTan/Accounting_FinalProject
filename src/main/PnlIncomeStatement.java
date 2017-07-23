/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import injection.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Phantom
 */
public class PnlIncomeStatement extends javax.swing.JPanel {

    /**
     * Creates new form PnlIncomeStatement
     */
    private Connection myConn = null;
    private PreparedStatement myStmt = null;
    private ResultSet myRs = null;

    Inject inject;
    double tOperational = 0;
    double tSales = 0;
    double tCOGS = 0;

    public PnlIncomeStatement(Connection conn, Inject inject) {
        initComponents();
        myConn = conn;
        this.inject = inject;
    }

    public void generateTable() {

        generateSalesRevenueTable();
        generateOperationalTable();
        generateCOGSTable();
        txtPeriod.setText(inject.getMonth() + "-" + inject.getYear());
        generateProfit();
    }

    public void generateProfit() {
        double grossprofit = 0;
        double netprofit = 0;
        grossprofit = tSales-tCOGS;
        txtGrossProfit.setText(String.valueOf(grossprofit));
        netprofit = grossprofit - tOperational;
        txtNetProfit.setText(String.valueOf(netprofit));
    }

    public void generateTotalSales() {
//        double tSales = 0;
        for (int row = 0; row < tblSalesRevenue.getRowCount(); row++) {
            tSales += Double.valueOf(tblSalesRevenue.getValueAt(row, 1).toString());
        }
        txtTotalSalesRevenue.setText(String.valueOf(tSales));
    }

    public void generateTotalCOGS() {
//        double tCOGS = 0;
        for (int row = 0; row < tblCOGS.getRowCount(); row++) {
            tCOGS += Double.valueOf(tblCOGS.getValueAt(row, 1).toString());
        }
        txtTotalCOGS.setText(String.valueOf(tCOGS));
    }

    public void generateTotalOperational() {
//        double tOperational = 0;
        for (int row = 0; row < tblOperational.getRowCount(); row++) {
            tOperational += Double.valueOf(tblOperational.getValueAt(row, 1).toString());
        }
        txtTotalOperational.setText(String.valueOf(tOperational));
    }

    private void generateSalesRevenueTable() {
        DefaultTableModel tableModel = (DefaultTableModel) tblSalesRevenue.getModel();
        for (int i = tblSalesRevenue.getRowCount() - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
        try {
            myStmt = myConn.prepareStatement("select chart_name from jurnal_fulldata where type='sales' &&"
                    + " extract(month from date)=? && extract(year from date)=? group by chart_name;");
            // Execute SQL query
            myStmt.setInt(1, inject.getMonth());
            myStmt.setInt(2, inject.getYear());
            myRs = myStmt.executeQuery();

            ArrayList<String> chart_name = new ArrayList<>();

            System.out.println(myRs.toString());
            if (myRs.isBeforeFirst()) {
                while (myRs.next()) {
                    chart_name.add(myRs.getString("chart_name"));
                }
            }
            for (String a : chart_name) {
                generateSalesRevenueRow(a);
            }
            generateTotalSales();
        } catch (SQLException ex) {
            Logger.getLogger(PnlIncomeStatement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateOperationalTable() {
        DefaultTableModel tableModel = (DefaultTableModel) tblOperational.getModel();
        for (int i = tblOperational.getRowCount() - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
        try {
            myStmt = myConn.prepareStatement("select chart_name from jurnal_fulldata where type='operational' &&"
                    + " extract(month from date)=? && extract(year from date)=? group by chart_name;");
            // Execute SQL query
            myStmt.setInt(1, inject.getMonth());
            myStmt.setInt(2, inject.getYear());
            myRs = myStmt.executeQuery();

            ArrayList<String> chart_name = new ArrayList<>();

            System.out.println(myRs.toString());
            if (myRs.isBeforeFirst()) {
                while (myRs.next()) {
                    chart_name.add(myRs.getString("chart_name"));
                }
            }
            for (String a : chart_name) {
                generateOperationalRow(a);
            }
            generateTotalOperational();
        } catch (SQLException ex) {
            Logger.getLogger(PnlIncomeStatement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double getNetProfit() {
        return Double.valueOf(txtNetProfit.getText());
    }

    private void generateCOGSTable() {
        DefaultTableModel tableModel = (DefaultTableModel) tblCOGS.getModel();
        for (int i = tblCOGS.getRowCount() - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
        try {
            myStmt = myConn.prepareStatement("select chart_name from jurnal_fulldata where type='cost of good sold' &&"
                    + " extract(month from date)=? && extract(year from date)=? group by chart_name;");
            // Execute SQL query
            myStmt.setInt(1, inject.getMonth());
            myStmt.setInt(2, inject.getYear());
            myRs = myStmt.executeQuery();

            ArrayList<String> chart_name = new ArrayList<>();

            System.out.println(myRs.toString());
            if (myRs.isBeforeFirst()) {
                while (myRs.next()) {
                    chart_name.add(myRs.getString("chart_name"));
                }
            }
            for (String a : chart_name) {
                generateCOGSRow(a);
            }

            generateBeginingInventoryRow();
            generateEndingInventoryRow();
            generateTotalCOGS();
        } catch (SQLException ex) {
            Logger.getLogger(PnlIncomeStatement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateBeginingInventoryRow() {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) tblCOGS.getModel();

            myStmt = myConn.prepareStatement("select opening,price from inventory where"
                    + " extract(month from date)=? && extract(year from date)=?;");
            // Execute SQL query
            myStmt.setInt(1, inject.getMonth());
            myStmt.setInt(2, inject.getYear());
            myRs = myStmt.executeQuery();

            double total = 0;
            // Process result set
            if (myRs.isBeforeFirst()) {
                while (myRs.next()) {
                    total += (myRs.getDouble("opening") * myRs.getDouble("price"));
                }
                Object data[] = {"Beginning inventory", total};
                tableModel.addRow(data);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PnlIncomeStatement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateEndingInventoryRow() {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) tblCOGS.getModel();

            myStmt = myConn.prepareStatement("select value from inventory where"
                    + " extract(month from date)=? && extract(year from date)=?;");
            // Execute SQL query
            myStmt.setInt(1, inject.getMonth());
            myStmt.setInt(2, inject.getYear());
            myRs = myStmt.executeQuery();

            double total = 0;
            // Process result set
            if (myRs.isBeforeFirst()) {
                while (myRs.next()) {
                    total += myRs.getDouble("value");
                }
                Object data[] = {"Ending inventory", (-total)};
                tableModel.addRow(data);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PnlIncomeStatement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateSalesRevenueRow(String chart_name) {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) tblSalesRevenue.getModel();

            myStmt = myConn.prepareStatement("select * from jurnal_fulldata where chart_name=? &&"
                    + " extract(month from date)=? && extract(year from date)=?;");
            // Execute SQL query
            myStmt.setString(1, chart_name);
            myStmt.setInt(2, inject.getMonth());
            myStmt.setInt(3, inject.getYear());
            myRs = myStmt.executeQuery();

            double debit = 0,
                    kredit = 0,
                    ending = 0;
            String chartName = "";
            // Process result set
            if (myRs.isBeforeFirst()) {
                while (myRs.next()) {
                    debit += myRs.getDouble("debit");
                    kredit += myRs.getDouble("kredit");
                    chartName = myRs.getString("chart_name");
                }
                ending = kredit - debit;
                Object data[] = {chartName, ending};
                tableModel.addRow(data);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PnlIncomeStatement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateOperationalRow(String chart_name) {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) tblOperational.getModel();

            myStmt = myConn.prepareStatement("select * from jurnal_fulldata where chart_name=? &&"
                    + " extract(month from date)=? && extract(year from date)=?;");
            // Execute SQL query
            myStmt.setString(1, chart_name);
            myStmt.setInt(2, inject.getMonth());
            myStmt.setInt(3, inject.getYear());
            myRs = myStmt.executeQuery();

            double debit = 0,
                    kredit = 0,
                    ending = 0;
            String chartName = "";
            // Process result set
            if (myRs.isBeforeFirst()) {
                while (myRs.next()) {
                    debit += myRs.getDouble("debit");
                    kredit += myRs.getDouble("kredit");
                    chartName = myRs.getString("chart_name");
                }
                ending = debit - kredit;
                Object data[] = {chartName, ending};
                tableModel.addRow(data);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PnlIncomeStatement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void generateCOGSRow(String chart_name) {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) tblCOGS.getModel();

            myStmt = myConn.prepareStatement("select * from jurnal_fulldata where chart_name=? &&"
                    + " extract(month from date)=? && extract(year from date)=?;");
            // Execute SQL query
            myStmt.setString(1, chart_name);
            myStmt.setInt(2, inject.getMonth());
            myStmt.setInt(3, inject.getYear());
            myRs = myStmt.executeQuery();

            double debit = 0,
                    kredit = 0,
                    ending = 0;
            String chartName = "";
            // Process result set
            if (myRs.isBeforeFirst()) {
                while (myRs.next()) {
                    debit += myRs.getDouble("debit");
                    kredit += myRs.getDouble("kredit");
                    chartName = myRs.getString("chart_name");
                }
                ending = debit - kredit;
                Object data[] = {chartName, ending};
                tableModel.addRow(data);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PnlIncomeStatement.class.getName()).log(Level.SEVERE, null, ex);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSalesRevenue = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCOGS = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblOperational = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtGrossProfit = new javax.swing.JTextField();
        txtNetProfit = new javax.swing.JTextField();
        txtPeriod = new javax.swing.JTextField();
        txtTotalSalesRevenue = new javax.swing.JTextField();
        txtTotalCOGS = new javax.swing.JTextField();
        txtTotalOperational = new javax.swing.JTextField();

        jLabel1.setText("Period :");

        jLabel2.setText("Sales Revenue :");

        tblSalesRevenue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chart Name", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblSalesRevenue);
        if (tblSalesRevenue.getColumnModel().getColumnCount() > 0) {
            tblSalesRevenue.getColumnModel().getColumn(0).setResizable(false);
            tblSalesRevenue.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel3.setText("Total sales revenue :");

        tblCOGS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chart Name", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblCOGS);
        if (tblCOGS.getColumnModel().getColumnCount() > 0) {
            tblCOGS.getColumnModel().getColumn(0).setResizable(false);
            tblCOGS.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel4.setText("Cost of good sold :");

        jLabel5.setText("Total Cost of good sold :");

        tblOperational.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chart Name", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblOperational);
        if (tblOperational.getColumnModel().getColumnCount() > 0) {
            tblOperational.getColumnModel().getColumn(0).setResizable(false);
            tblOperational.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel6.setText("Operational Expenditure :");

        jLabel7.setText("Total Expenditure :");

        jLabel8.setText("Gross Profit (Loss) :");

        jLabel9.setText("Net Profit (Loss) :");

        txtGrossProfit.setEditable(false);
        txtGrossProfit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGrossProfitActionPerformed(evt);
            }
        });

        txtNetProfit.setEditable(false);
        txtNetProfit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNetProfitActionPerformed(evt);
            }
        });

        txtPeriod.setEditable(false);
        txtPeriod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPeriodActionPerformed(evt);
            }
        });

        txtTotalSalesRevenue.setEditable(false);
        txtTotalSalesRevenue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalSalesRevenueActionPerformed(evt);
            }
        });

        txtTotalCOGS.setEditable(false);
        txtTotalCOGS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalCOGSActionPerformed(evt);
            }
        });

        txtTotalOperational.setEditable(false);
        txtTotalOperational.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalOperationalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalSalesRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtGrossProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotalCOGS, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotalOperational, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(36, 36, 36))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNetProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(txtPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtTotalSalesRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtTotalCOGS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtTotalOperational, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtNetProfit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtGrossProfit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(56, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtGrossProfitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGrossProfitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGrossProfitActionPerformed

    private void txtNetProfitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNetProfitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNetProfitActionPerformed

    private void txtPeriodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPeriodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPeriodActionPerformed

    private void txtTotalSalesRevenueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalSalesRevenueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalSalesRevenueActionPerformed

    private void txtTotalCOGSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalCOGSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalCOGSActionPerformed

    private void txtTotalOperationalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalOperationalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalOperationalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblCOGS;
    private javax.swing.JTable tblOperational;
    private javax.swing.JTable tblSalesRevenue;
    private javax.swing.JTextField txtGrossProfit;
    private javax.swing.JTextField txtNetProfit;
    private javax.swing.JTextField txtPeriod;
    private javax.swing.JTextField txtTotalCOGS;
    private javax.swing.JTextField txtTotalOperational;
    private javax.swing.JTextField txtTotalSalesRevenue;
    // End of variables declaration//GEN-END:variables
}
