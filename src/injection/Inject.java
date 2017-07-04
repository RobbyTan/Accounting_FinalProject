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
import main.PnlGeneralLedgerMonth;
import main.PnlIncomeStatement;
import main.PnlInsertInventory;
import main.PnlMonth;
import main.PnlTrialBalance;
import main.PnlViewInventory;
import main.PnlViewJurnal;

/**
 *
 * @author UPHM
 */
public class Inject {
    private final PnlAddUser addUser;
    private final PnlDeleteUser deleteUser;
    private final PnlAccountChart accountChart;
    private final PnlCreateJurnal createJurnal;
    private final PnlViewJurnal viewJurnal ;
    private final PnlGeneralLedger generalLedger;
    private final PnlGeneralLedgerMonth generalLedgerMonth;
    private final PnlTrialBalance trialBalance;
    private final PnlInsertInventory insertInventory;
    private final PnlViewInventory viewInventory;
    private final PnlBalanceSheetReport balanceSheet;
    private final PnlIncomeStatement incomeStatement;
    private final PnlEditJurnal editJurnal;
    private final FrmMain main;
    private final PnlMonth monthSelect;
    
    private int month;
    private int year;
    
    private Connection myConn;

    public Inject(Connection myConn,FrmMain main) {
        addUser= new PnlAddUser(myConn,this);
        deleteUser=new PnlDeleteUser(myConn,this);
        accountChart=new PnlAccountChart(myConn,this);
        createJurnal=new PnlCreateJurnal(myConn,this);
        viewJurnal=new PnlViewJurnal(myConn,this);
        generalLedger=new PnlGeneralLedger(myConn,this);
        generalLedgerMonth=new PnlGeneralLedgerMonth(myConn, this);
        trialBalance=new PnlTrialBalance(myConn,this);
        insertInventory=new PnlInsertInventory(myConn,this);
        viewInventory=new PnlViewInventory(myConn,this);
        balanceSheet=new PnlBalanceSheetReport(myConn,this);
        incomeStatement=new PnlIncomeStatement(myConn,this);
        editJurnal=new PnlEditJurnal(myConn,this);
        monthSelect=new PnlMonth(this);
        
        this.main=main;
    }

    public PnlAddUser getAddUser() {
        return addUser;
    }

    public PnlDeleteUser getDeleteUser() {
        return deleteUser;
    }

    public PnlAccountChart getAccountChart() {
        return accountChart;
    }

    public PnlCreateJurnal getCreateJurnal() {
        return createJurnal;
    }

    public PnlViewJurnal getViewJurnal() {
        return viewJurnal;
    }

    public PnlGeneralLedger getGeneralLedger() {
        return generalLedger;
    }
    
    public PnlGeneralLedgerMonth getGeneralLedgerMonth() {
        return generalLedgerMonth;
    }

    public PnlTrialBalance getTrialBalance() {
        return trialBalance;
    }

    public PnlInsertInventory getInsertInventory() {
        return insertInventory;
    }

    public PnlViewInventory getViewInventory() {
        return viewInventory;
    }

    public PnlBalanceSheetReport getBalanceSheet() {
        return balanceSheet;
    }

    public PnlIncomeStatement getIncomeStatement() {
        return incomeStatement;
    }

    public PnlEditJurnal getEditJurnal() {
        return editJurnal;
    }

    public FrmMain getMain() {
        return main;
    }
    
    public int getMonth() {
        return month;
    }

    public PnlMonth getMonthSelect() {
        return monthSelect;
    }  

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    
    
}
