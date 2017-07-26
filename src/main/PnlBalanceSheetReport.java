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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Phantom
 */
public class PnlBalanceSheetReport extends javax.swing.JPanel {

    /**
     * Creates new form PnlBalanceSheetReport
     */
    private Connection myConn = null;
    private PreparedStatement myStmt = null;
    private ResultSet myRs = null;
    private double tAsset = 0;
    private double tLiability = 0;
    private double tCapital = 0;
    private double opening = 0;
    Inject inject;

    public PnlBalanceSheetReport(Connection conn, Inject inject) {
        initComponents();
        myConn = conn;
        this.inject = inject;
    }

    public void generateTable() {
        tAsset = 0;
        tLiability = 0;
        tCapital = 0;
        generateAssetTable();
        generateCapitalTable();
        generateLiabilityTable();
        generateTotalAsset();
        generateTotalLiabilityCapital();
        txtPeriod.setText(inject.getYear() + "-" + inject.getMonth());
    }

    private void generateAssetTable() {
        DefaultTableModel tableModel = (DefaultTableModel) tblBalanceSheetAsset.getModel();
        for (int i = tblBalanceSheetAsset.getRowCount() - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
        try {
            myStmt = myConn.prepareStatement("select chart_name from jurnal_fulldata where type='asset' &&"
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
                generateAssetRow(a);
            }
            generateEndingInventoryRow();
        } catch (SQLException ex) {
            Logger.getLogger(PnlBalanceSheetReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateAssetRow(String chart_name) {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) tblBalanceSheetAsset.getModel();

            myStmt = myConn.prepareStatement("select * from jurnal_fulldata where chart_name=? &&"
                    + " extract(month from date)=? && extract(year from date)=? ;");
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
            Logger.getLogger(PnlBalanceSheetReport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void generateLiabilityTable() {
        DefaultTableModel tableModel = (DefaultTableModel) tblBalanceSheetLiability.getModel();
        for (int i = tblBalanceSheetLiability.getRowCount() - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
        try {
            myStmt = myConn.prepareStatement("select chart_name from jurnal_fulldata where type='liability' &&"
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
                generateLiabilityRow(a);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PnlBalanceSheetReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateLiabilityRow(String chart_name) {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) tblBalanceSheetLiability.getModel();

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
            Logger.getLogger(PnlBalanceSheetReport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void generateCapitalTable() {
        DefaultTableModel tableModel = (DefaultTableModel) tblBalanceSheetCapital.getModel();
        for (int i = tblBalanceSheetCapital.getRowCount() - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
        try {
            myStmt = myConn.prepareStatement("select chart_name from jurnal_fulldata where type='capital' &&"
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
                generateCapitalRow(a);
            }
            System.out.println("aaa");
            generateRetainedEarning();
        } catch (SQLException ex) {
            Logger.getLogger(PnlBalanceSheetReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateCapitalRow(String chart_name) {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) tblBalanceSheetCapital.getModel();

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
            Logger.getLogger(PnlBalanceSheetReport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void generateTotalAsset() {
//        double tSales = 0;
        for (int row = 0; row < tblBalanceSheetAsset.getRowCount(); row++) {
            tAsset += Double.valueOf(tblBalanceSheetAsset.getValueAt(row, 1).toString());
        }
        txtBalanceSheetAsset.setText(String.format("%,.02f", tAsset));
    }

    public void generateTotalLiabilityCapital() {
//        double tSales = 0;
        for (int row = 0; row < tblBalanceSheetLiability.getRowCount(); row++) {
            tLiability += Double.valueOf(tblBalanceSheetLiability.getValueAt(row, 1).toString());
        }
        for (int row = 0; row < tblBalanceSheetCapital.getRowCount(); row++) {
            tCapital += Double.valueOf(tblBalanceSheetCapital.getValueAt(row, 1).toString());
        }

        double totalLiabilityCapital = 0;
        totalLiabilityCapital = tLiability + tCapital;
        txtBalanceSheetLiabilityCapital.setText(String.format("%,.02f", totalLiabilityCapital));
    }

    private void generateRetainedEarning() {
        DefaultTableModel tableModel = (DefaultTableModel) tblBalanceSheetCapital.getModel();
        Object data[] = {"Retained Earning", inject.getIncomeStatement().getNetProfit()};
        System.out.println("here");
        tableModel.addRow(data);
    }

    private void generateEndingInventoryRow() {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) tblBalanceSheetAsset.getModel();

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
                Object data[] = {"Ending inventory", (total)};
                tableModel.addRow(data);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PnlIncomeStatement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateTableTemporary(String chartNo) {
        try {

            myStmt = myConn.prepareStatement("select * from jurnal_fulldata where chart_no=? &&"
                    + " extract(month from date)=? && extract(year from date)=?;");
            // Execute SQL query
            myStmt.setString(1, chartNo);
            myStmt.setInt(2, inject.getMonth());
            myStmt.setInt(3, inject.getYear());
            myRs = myStmt.executeQuery();

            double debit = 0;
            double kredit = 0;
            double ending = 0;
            String chartName = "";
            // Process result set
            if (myRs.isBeforeFirst()) {
                while (myRs.next()) {
                    debit += myRs.getDouble("debit");
                    kredit += myRs.getDouble("kredit");
                    chartName = myRs.getString("chart_name");
                }
                ending = opening + debit - kredit;
                System.out.println(ending);
                saveToDatabase(chartNo, chartName, ending);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PnlTrialBalance.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {

        }
    }

    public void generateChartNoTableTemporary() {
        try {

            myStmt = myConn.prepareStatement("select * from account_chart");
            // Execute SQL query
            myRs = myStmt.executeQuery();
            ArrayList<String> chartNo = new ArrayList<>();

            // Process result set
            if (myRs.isBeforeFirst()) {
                while (myRs.next()) {
                    chartNo.add(myRs.getString("chart_no"));
                }
            }
            for (String c : chartNo) {
//                generateTableTemporaryLastMonth(c);
                generateTableTemporary(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DlgLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {

        }
    }

    private void saveToDatabase(String chart_no,String chart_name,double ending) {
        try {

            myStmt = myConn.prepareStatement("INSERT INTO `akuntansi`.`tutup_buku` (`date`, `chart_no`,"
                    + "`ending`) VALUES (?,?,?);");
            myStmt.setString(1,inject.getYear() + "-" + inject.getMonth()+"-28" );
            myStmt.setString(2, chart_no);
            myStmt.setDouble(3, ending);
            // Execute SQL query
            myStmt.executeUpdate();
            System.out.println("add");

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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBalanceSheetAsset = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBalanceSheetLiability = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblBalanceSheetCapital = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtBalanceSheetLiabilityCapital = new javax.swing.JTextField();
        txtBalanceSheetAsset = new javax.swing.JTextField();
        txtPeriod = new javax.swing.JTextField();
        btnTutupBuku = new javax.swing.JButton();

        jLabel1.setText("Period :");

        jLabel2.setText("Asset :");

        tblBalanceSheetAsset.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chart name", "Price"
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
        jScrollPane1.setViewportView(tblBalanceSheetAsset);
        if (tblBalanceSheetAsset.getColumnModel().getColumnCount() > 0) {
            tblBalanceSheetAsset.getColumnModel().getColumn(0).setResizable(false);
            tblBalanceSheetAsset.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel3.setText("Liability :");

        tblBalanceSheetLiability.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chart name", "Price"
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
        jScrollPane2.setViewportView(tblBalanceSheetLiability);
        if (tblBalanceSheetLiability.getColumnModel().getColumnCount() > 0) {
            tblBalanceSheetLiability.getColumnModel().getColumn(0).setResizable(false);
            tblBalanceSheetLiability.getColumnModel().getColumn(1).setResizable(false);
        }

        tblBalanceSheetCapital.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chart name", "Price"
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
        jScrollPane3.setViewportView(tblBalanceSheetCapital);
        if (tblBalanceSheetCapital.getColumnModel().getColumnCount() > 0) {
            tblBalanceSheetCapital.getColumnModel().getColumn(0).setResizable(false);
            tblBalanceSheetCapital.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel4.setText("Capital :");

        jLabel5.setText("Total Liability and Capital :");

        jLabel6.setText("Total Asset :");

        txtPeriod.setEditable(false);
        txtPeriod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPeriodActionPerformed(evt);
            }
        });

        btnTutupBuku.setText("Tutup Buku");
        btnTutupBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTutupBukuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(118, 118, 118)
                        .addComponent(jLabel3)
                        .addGap(237, 237, 237)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBalanceSheetAsset))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(89, 89, 89)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(223, 223, 223)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBalanceSheetLiabilityCapital, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(btnTutupBuku)
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(txtPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(txtBalanceSheetLiabilityCapital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBalanceSheetAsset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTutupBuku))
                .addContainerGap(60, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtPeriodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPeriodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPeriodActionPerformed

    private void btnTutupBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTutupBukuActionPerformed
        generateChartNoTableTemporary();
    }//GEN-LAST:event_btnTutupBukuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTutupBuku;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblBalanceSheetAsset;
    private javax.swing.JTable tblBalanceSheetCapital;
    private javax.swing.JTable tblBalanceSheetLiability;
    private javax.swing.JTextField txtBalanceSheetAsset;
    private javax.swing.JTextField txtBalanceSheetLiabilityCapital;
    private javax.swing.JTextField txtPeriod;
    // End of variables declaration//GEN-END:variables
}
