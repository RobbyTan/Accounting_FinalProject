/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package injection;

import java.sql.Connection;
import main.FrmMain;
import main.PnlAccountChart;
import main.PnlAddUser;
import main.PnlBalanceSheetReport;
import main.PnlCreateJurnal;
import main.PnlDeleteUser;
import main.PnlEditJurnal;
import main.PnlGeneralLedger;
import main.PnlIncomeStatement;
import main.PnlInsertInventory;
import main.PnlTrialBalance;
import main.PnlViewInventory;
import main.PnlViewJurnal;

/**
 *
 * @author UPHM
 */
public class Inject {
    public PnlAddUser addUser;
    public PnlDeleteUser deleteUser;
    public PnlAccountChart accountChart;
    public PnlCreateJurnal createJurnal;
    public PnlViewJurnal viewJurnal ;
    public PnlGeneralLedger generalLedger;
    public PnlTrialBalance trialBalance;
    public PnlInsertInventory insertInventory;
    public PnlViewInventory viewInventory;
    public PnlBalanceSheetReport balanceSheet;
    public PnlIncomeStatement incomeStatement;
    public PnlEditJurnal editJurnal;
    public FrmMain main;
    
    private Connection myConn;

    public Inject(Connection myConn,FrmMain main) {
        addUser= new PnlAddUser(myConn,this);
        deleteUser=new PnlDeleteUser(myConn,this);
        accountChart=new PnlAccountChart(myConn,this);
        createJurnal=new PnlCreateJurnal(myConn,this);
        viewJurnal=new PnlViewJurnal(myConn,this);
        generalLedger=new PnlGeneralLedger(myConn,this);
        trialBalance=new PnlTrialBalance(myConn,this);
        insertInventory=new PnlInsertInventory(myConn,this);
        viewInventory=new PnlViewInventory(myConn,this);
        balanceSheet=new PnlBalanceSheetReport(myConn,this);
        incomeStatement=new PnlIncomeStatement(myConn,this);
        editJurnal=new PnlEditJurnal(myConn,this);
        this.main=main;
    }
    
}
