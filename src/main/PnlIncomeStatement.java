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
public class PnlIncomeStatement extends javax.swing.JPanel {

    /**
     * Creates new form PnlIncomeStatement
     */
    private Connection myConn = null;
    private PreparedStatement myStmt = null;
    private ResultSet myRs = null;

    Inject inject;

    public PnlIncomeStatement(Connection conn, Inject inject) {
        initComponents();
        myConn = conn;
        this.inject = inject;
    }

    public void generateTable() {
        
        generateSalesRevenueTable();
        generateOperationalTable();
    }

    private void generateSalesRevenueTable() {
        DefaultTableModel tableModel = (DefaultTableModel) tblSalesRevenue.getModel();
            for (int i = tblSalesRevenue.getRowCount() - 1; i >= 0; i--) {
                tableModel.removeRow(i);
            }
        try {
            myStmt = myConn.prepareStatement("select chart_name from jurnal_fulldata where type='sales' &&"
                    + " extract(month from date)=? && extract(year from date)=?;");
            // Execute SQL query
            myStmt.setInt(1, inject.getMonth());
            myStmt.setInt(2, inject.getYear());
            myRs = myStmt.executeQuery();
            
            ArrayList<String> chart_name=new ArrayList<>();
            
            System.out.println(myRs.toString());
            if (myRs.isBeforeFirst()) {
                while (myRs.next()) {
                    chart_name.add(myRs.getString("chart_name"));
                }
            }
            for(String a:chart_name){
                generateSalesRevenueRow(a);
            }
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
                    + " extract(month from date)=? && extract(year from date)=?;");
            // Execute SQL query
            myStmt.setInt(1, inject.getMonth());
            myStmt.setInt(2, inject.getYear());
            myRs = myStmt.executeQuery();
            
            ArrayList<String> chart_name=new ArrayList<>();
            
            System.out.println(myRs.toString());
            if (myRs.isBeforeFirst()) {
                while (myRs.next()) {
                    chart_name.add(myRs.getString("chart_name"));
                }
            }
            for(String a:chart_name){
                generateOperationalRow(a);
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
                ending = debit-kredit;
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
        jTable2 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblOperational = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

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

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel1))
                            .addComponent(jLabel3))
                        .addGap(120, 120, 120)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(84, 84, 84))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addGap(39, 39, 39)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addContainerGap(36, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


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
    private javax.swing.JTable jTable2;
    private javax.swing.JTable tblOperational;
    private javax.swing.JTable tblSalesRevenue;
    // End of variables declaration//GEN-END:variables
}
