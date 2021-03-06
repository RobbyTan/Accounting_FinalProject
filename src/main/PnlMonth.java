package main;

import injection.Inject;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class PnlMonth extends javax.swing.JPanel {

    /**
     * Creates new form PnlMonth
     */
    
    private Inject inject;
    private JPanel panel;
    
    public PnlMonth(Inject inject) {
        initComponents();
        this.inject=inject;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    private void changeLayoutTo(JPanel panel){
        inject.getMain().changeLayout(panel);
        if(panel==inject.getViewJurnal()){
            inject.getViewJurnal().generateTable();
        }
        if(panel==inject.getEditJurnal()){
            inject.getEditJurnal().generateComboBoxJurnalNo();
        }
        if(panel==inject.getTrialBalance()){
            inject.getTrialBalance().generateTable();
        }
        if(panel==inject.getViewInventory()){
            inject.getViewInventory().generateTable();
        }
        if(panel==inject.getInsertInventory()){
            inject.getInsertInventory().generateTable();
        }
        if(panel==inject.getIncomeStatement()){
            inject.getIncomeStatement().generateTable();
        }
        if(panel==inject.getBalanceSheet()){
            inject.getIncomeStatement().generateTable();
            inject.getBalanceSheet().generateTable();
        }
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnMonthNext = new javax.swing.JButton();
        mtcMonth = new com.toedter.calendar.JMonthChooser();
        yrcYear = new com.toedter.calendar.JYearChooser();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Select Month : ");

        btnMonthNext.setText("Next");
        btnMonthNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMonthNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(249, 249, 249)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mtcMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(yrcYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnMonthNext, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(331, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(194, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yrcYear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(mtcMonth, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(82, 82, 82)
                .addComponent(btnMonthNext)
                .addGap(172, 172, 172))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnMonthNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMonthNextActionPerformed
        inject.setMonth(mtcMonth.getMonth()+1);
        inject.setYear(yrcYear.getYear());
        changeLayoutTo(panel);
    }//GEN-LAST:event_btnMonthNextActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMonthNext;
    private javax.swing.JLabel jLabel1;
    private com.toedter.calendar.JMonthChooser mtcMonth;
    private com.toedter.calendar.JYearChooser yrcYear;
    // End of variables declaration//GEN-END:variables
}
