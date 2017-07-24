package main;

import injection.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
public class PnlCreateJurnal extends javax.swing.JPanel {

    /**
     * Creates new form PanelCreateJurnal
     */
    private Connection myConn = null;
    private PreparedStatement myStmt = null;
    private ResultSet myRs = null;

    private Calendar cal = Calendar.getInstance();

    Inject inject;

    FrmMain main;
    boolean pass;

    public PnlCreateJurnal(Connection conn, Inject inject) {
        initComponents();
        myConn = conn;
        this.inject = inject;
    }

    public String getJurnalNumber() {
        return "J-" + txtCreateJurnalJurnalNo.getText();
    }

    public String getDateChooserJurnal() {

        try {
            String dateStr = dtcCreateJurnal.getDate().toString();
            DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            Date date = (Date) formatter.parse(dateStr);

            cal.setTime(date);
            String formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);
            return formatedDate;
        } catch (ParseException ex) {
            Logger.getLogger(PnlCreateJurnal.class.getName()).log(Level.SEVERE, null, ex);
        }
//        return dtcCreateJurnal.getDate().toString();
        return null;
    }

    private boolean checkJurnal() {

        try {
            getDateChooserJurnal();
            myStmt = myConn.prepareStatement("select * from jurnal_fulldata where extract(month from date)=? && extract"
                    + "(year from date)=? && jurnal_no=? ;");
            // Execute SQL query
            myStmt.setInt(1, cal.get(Calendar.MONTH) + 1);
            myStmt.setInt(2, cal.get(Calendar.YEAR));
            myStmt.setString(3, "J-" + txtCreateJurnalJurnalNo.getText());
            System.out.println((cal.get(Calendar.MONTH) + 1) + " " + cal.get(Calendar.YEAR));
            myRs = myStmt.executeQuery();
            pass = true;
            if (myRs.isBeforeFirst()) {
                pass = false;
            }
            return pass;
        } catch (SQLException ex) {
            Logger.getLogger(PnlCreateJurnal.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void generateTotal() {
        double tDebit = 0;
        double tKredit = 0;
        for (int row = 0; row < tblCreateJurnal.getRowCount(); row++) {
            tDebit += Double.valueOf(tblCreateJurnal.getValueAt(row, 2).toString());
            tKredit += Double.valueOf(tblCreateJurnal.getValueAt(row, 3).toString());
        }
        txtCreateJurnalTotalDebit.setText(String.valueOf((long)tDebit));
        txtCreateJurnalTotalKredit.setText(String.valueOf((long)tKredit));
    }

    public void addTableRow(String no, String name, double debit, double kredit) {
        DefaultTableModel tableModel = (DefaultTableModel) tblCreateJurnal.getModel();
        Object data[] = {no, name, debit, kredit};
        tableModel.addRow(data);
        generateTotal();
    }

    private void saveToMaster() {
        try {
            myStmt = myConn
                    .prepareStatement("INSERT INTO `akuntansi`.`jurnal_master` (`jurnal_no`,"
                            + " `date`, `description`) VALUES (?,?,?);");
            myStmt.setString(1, getJurnalNumber());
            myStmt.setString(2, getDateChooserJurnal());
            myStmt.setString(3, txaCreateJurnalDescription.getText());

            // Execute SQL query
            myStmt.executeUpdate();
            txtCreateJurnalJurnalNo.setText("");
            txaCreateJurnalDescription.setText("");
            DefaultTableModel tableModel = (DefaultTableModel) tblCreateJurnal.getModel();
            tableModel.setRowCount(0);
//            cara kebalikan dtc ke null
        } catch (SQLException ex) {
            Logger.getLogger(PnlCreateJurnal.class.getName()).log(Level.SEVERE, null, ex);
            SUtility.msg(this, "Invalid Input!");
        }
    }

    private void saveToDetail() {
        try {
            for (int i = 0; i < tblCreateJurnal.getRowCount(); i++) {
                // Prepare statement

                myStmt = myConn.prepareStatement("INSERT INTO `akuntansi`.`jurnal_detail` "
                        + "(`jurnal_no`, `chart_no`, `chart_name`, `debit`, `kredit`, `date`) VALUES"
                        + " (?,?,?,?,?,?);");
                myStmt.setString(1, "J-" + txtCreateJurnalJurnalNo.getText());
                myStmt.setString(2, tblCreateJurnal.getValueAt(i, 0).toString());
                myStmt.setString(3, tblCreateJurnal.getValueAt(i, 1).toString());
                myStmt.setString(4, tblCreateJurnal.getValueAt(i, 2).toString());
                myStmt.setString(5, tblCreateJurnal.getValueAt(i, 3).toString());
                myStmt.setString(6, getDateChooserJurnal());
                // Execute SQL query
                myStmt.executeUpdate();
                System.out.println("add");

            }
            SUtility.msg(this, "Update Saved!");
        } catch (SQLException ex) {
            Logger.getLogger(PnlCreateJurnal.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel4 = new javax.swing.JLabel();
        txtCreateJurnalJurnalNo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaCreateJurnalDescription = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCreateJurnal = new javax.swing.JTable();
        btnCreateJurnalAddTransaction = new javax.swing.JButton();
        btnCreateJurnalSave = new javax.swing.JButton();
        dtcCreateJurnal = new com.toedter.calendar.JDateChooser();
        txtCreateJurnalTotalKredit = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCreateJurnalTotalDebit = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Date :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Jurnal :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Description :");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("J-");

        txtCreateJurnalJurnalNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCreateJurnalJurnalNoActionPerformed(evt);
            }
        });

        txaCreateJurnalDescription.setColumns(20);
        txaCreateJurnalDescription.setRows(5);
        jScrollPane1.setViewportView(txaCreateJurnalDescription);

        tblCreateJurnal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chart No", "Chart Name", "Debit", "Kredit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Long.class, java.lang.Long.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblCreateJurnal);
        if (tblCreateJurnal.getColumnModel().getColumnCount() > 0) {
            tblCreateJurnal.getColumnModel().getColumn(0).setResizable(false);
            tblCreateJurnal.getColumnModel().getColumn(1).setResizable(false);
            tblCreateJurnal.getColumnModel().getColumn(2).setResizable(false);
            tblCreateJurnal.getColumnModel().getColumn(3).setResizable(false);
        }

        btnCreateJurnalAddTransaction.setText("Add Transaction");
        btnCreateJurnalAddTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateJurnalAddTransactionActionPerformed(evt);
            }
        });

        btnCreateJurnalSave.setText("Save");
        btnCreateJurnalSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateJurnalSaveActionPerformed(evt);
            }
        });

        dtcCreateJurnal.setDateFormatString("yyyy-MM-dd");

        txtCreateJurnalTotalKredit.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Total Kredit :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Total Debit :");

        txtCreateJurnalTotalDebit.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(246, 246, 246)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCreateJurnalJurnalNo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(btnCreateJurnalAddTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dtcCreateJurnal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(21, 21, 21)
                        .addComponent(txtCreateJurnalTotalDebit, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtCreateJurnalTotalKredit, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCreateJurnalSave, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 692, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(99, 99, 99))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(dtcCreateJurnal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(txtCreateJurnalJurnalNo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCreateJurnalAddTransaction))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel3))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtCreateJurnalTotalDebit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(txtCreateJurnalTotalKredit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCreateJurnalSave))
                .addGap(77, 77, 77))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtCreateJurnalJurnalNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCreateJurnalJurnalNoActionPerformed

    }//GEN-LAST:event_txtCreateJurnalJurnalNoActionPerformed

    private void btnCreateJurnalAddTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateJurnalAddTransactionActionPerformed
        if (!txtCreateJurnalJurnalNo.getText().trim().isEmpty() && !dtcCreateJurnal.getDate().toString().trim().isEmpty()) {
            DlgCreateJurnalAddJurnalTransaction addTransaction = new DlgCreateJurnalAddJurnalTransaction(this, true, myConn);
            addTransaction.setVisible(true);
        } else {
            SUtility.msg(this, "Jurnal number and Date must not be empty");
        }
    }//GEN-LAST:event_btnCreateJurnalAddTransactionActionPerformed

    private void btnCreateJurnalSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateJurnalSaveActionPerformed

        if (checkJurnal()) {
            generateTotal();
            if (txtCreateJurnalTotalDebit.getText().equals(txtCreateJurnalTotalKredit.getText())) {
                if (!txtCreateJurnalJurnalNo.getText().trim().isEmpty() && !getDateChooserJurnal().trim().isEmpty()) {
                    int x = SUtility.msq(this, "Are you sure?");
                    if (x == 0) {
                        saveToDetail();
                        saveToMaster();
                        txtCreateJurnalTotalDebit.setText("");
                        txtCreateJurnalTotalKredit.setText("");
                    }
                } else {
                    SUtility.msg(this, "Fill All Data!");
                }
            } else {
                SUtility.msg(this, "unbalanced total kredit and debit");
            }
        } else {
            SUtility.msg(this, "Jurnal No already exist!");
        }
    }//GEN-LAST:event_btnCreateJurnalSaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateJurnalAddTransaction;
    private javax.swing.JButton btnCreateJurnalSave;
    private com.toedter.calendar.JDateChooser dtcCreateJurnal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblCreateJurnal;
    private javax.swing.JTextArea txaCreateJurnalDescription;
    private javax.swing.JTextField txtCreateJurnalJurnalNo;
    private javax.swing.JTextField txtCreateJurnalTotalDebit;
    private javax.swing.JTextField txtCreateJurnalTotalKredit;
    // End of variables declaration//GEN-END:variables
}
