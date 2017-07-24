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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USER
 */
public class PnlTrialBalance extends javax.swing.JPanel {

    /**
     * Creates new form PnlTrialBalance
     */
    private Connection myConn = null;
    private PreparedStatement myStmt = null;
    private ResultSet myRs = null;
    double opening=0;
    Inject inject;

    public PnlTrialBalance(Connection conn, Inject inject) {
        initComponents();
        myConn = conn;
        this.inject = inject;
    }

    public void generateTableTemporary(String chartNo) {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) tblTrialBalance.getModel();

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
                ending = debit - kredit;
                Object data[] = {chartNo, chartName, opening, debit, kredit, ending};
                tableModel.addRow(data);
                System.out.println(opening);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PnlTrialBalance.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {

        }
    }

     public void generateTableTemporaryLastMonth(String chartNo) {
        try {
            int month=0;
            int year=0;
            if(inject.getMonth()>1){
            month=inject.getMonth()-1;
            year=inject.getYear();
            }else{
            month=12;
            year=inject.getYear()-1;
            }
            DefaultTableModel tableModel = (DefaultTableModel) tblTrialBalance.getModel();

            myStmt = myConn.prepareStatement("select * from jurnal_fulldata where chart_no=? &&"
                    + " extract(month from date)=? && extract(year from date)=?;");
            // Execute SQL query
            myStmt.setString(1, chartNo);
            myStmt.setInt(2, month);
            myStmt.setInt(3, year);
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
                }
                opening = debit - kredit;
                System.out.println("1");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DlgLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {

        }
    }
    public void generateTable() {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) tblTrialBalance.getModel();
            for (int i = tblTrialBalance.getRowCount() - 1; i >= 0; i--) {
                tableModel.removeRow(i);
            }
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
            generateTotal();
            txtTrialBalancePeriod.setText(String.valueOf(inject.getYear()) + "-" + String.valueOf(inject.getMonth()));
        } catch (SQLException ex) {
            Logger.getLogger(DlgLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {

        }
    }

      private void generateTotal(){
        double tDebit=0;
        double tKredit=0;
        double tEnding=0;
        double tOpening=0;
        for(int row=0;row<tblTrialBalance.getRowCount();row++){
            tDebit+=Double.valueOf(tblTrialBalance.getValueAt(row,3).toString());
            tKredit+=Double.valueOf(tblTrialBalance.getValueAt(row,4).toString());
            tEnding+=Double.valueOf(tblTrialBalance.getValueAt(row,5).toString());
            tOpening+=Double.valueOf(tblTrialBalance.getValueAt(row,2).toString());
        }
        txtTrialBalanceTotalDebit.setText(String.valueOf((long)tDebit));
        txtTrialBalanceTotalKredit.setText(String.valueOf((long)tKredit));
        txtTrialBalanceEnding.setText(String.valueOf((long)tEnding));
        txtTrialBalanceOpening.setText(String.valueOf((long)tOpening));
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTrialBalance = new javax.swing.JTable();
        txtTrialBalanceTotalDebit = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTrialBalanceTotalKredit = new javax.swing.JTextField();
        txtTrialBalanceEnding = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTrialBalanceOpening = new javax.swing.JTextField();
        txtTrialBalancePeriod = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Period :");

        tblTrialBalance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chart No", "Chart Name", "Opening", "Debit", "Kredit", "Ending"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblTrialBalance);
        if (tblTrialBalance.getColumnModel().getColumnCount() > 0) {
            tblTrialBalance.getColumnModel().getColumn(0).setResizable(false);
            tblTrialBalance.getColumnModel().getColumn(1).setResizable(false);
            tblTrialBalance.getColumnModel().getColumn(2).setResizable(false);
            tblTrialBalance.getColumnModel().getColumn(3).setResizable(false);
            tblTrialBalance.getColumnModel().getColumn(4).setResizable(false);
            tblTrialBalance.getColumnModel().getColumn(5).setResizable(false);
        }

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Total Debit :");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Total Kredit :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Opening :");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Ending :");

        txtTrialBalancePeriod.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(160, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 643, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
            .addGroup(layout.createSequentialGroup()
                .addGap(224, 224, 224)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTrialBalancePeriod, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtTrialBalanceTotalKredit, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTrialBalanceTotalDebit, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtTrialBalanceEnding, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtTrialBalanceOpening, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(106, 106, 106))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTrialBalancePeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTrialBalanceOpening, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtTrialBalanceEnding, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTrialBalanceTotalDebit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtTrialBalanceTotalKredit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(77, 77, 77))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTrialBalance;
    private javax.swing.JTextField txtTrialBalanceEnding;
    private javax.swing.JTextField txtTrialBalanceOpening;
    private javax.swing.JTextField txtTrialBalancePeriod;
    private javax.swing.JTextField txtTrialBalanceTotalDebit;
    private javax.swing.JTextField txtTrialBalanceTotalKredit;
    // End of variables declaration//GEN-END:variables
}
