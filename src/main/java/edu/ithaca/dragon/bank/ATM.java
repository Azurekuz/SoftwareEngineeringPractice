
package edu.ithaca.dragon.bank;

public class ATM implements BasicAPI {

    private UserAccount currentAccount = null;
    public CentralBank bank;

    public ATM(CentralBank bank){
        this.bank = bank;
    }
    public UserAccount confirmCredentials(String username, String password) throws NonExistentAccountException, FrozenAccountException{

        currentAccount = bank.confirmCredentials(username,password);
        return currentAccount;
    }

    public double checkBalance(int userId, int acctId) throws NonExistentAccountException{
        //ignores userId param
        return bank.checkBalance(currentAccount.getUserID(), acctId);

    }
    public void withdraw(int acctId, double amount) throws InsufficientFundsException, NonExistentAccountException, FrozenAccountException{
        bank.withdraw(currentAccount.getUserID(), acctId ,amount);
    }

    public void deposit(int acctId, double amount) throws InsufficientFundsException, NonExistentAccountException, FrozenAccountException{
        bank.deposit(currentAccount.getUserID(),acctId,amount);
    }

    public void transfer(int userIDFrom, int acctIdToWithdrawFrom, int userIDTo, int acctIdToDepositTo, double amount) throws InsufficientFundsException, NonExistentAccountException, FrozenAccountException{
        bank.transfer(userIDFrom, acctIdToWithdrawFrom, userIDTo, acctIdToDepositTo,amount);
    }

    public String transactionHistory(int acctId){
        return null;
    }

    public void logout(){
        if(currentAccount != null){
            currentAccount = null;
        }
    }

    public boolean isLoggedIn(){
        return (currentAccount != null);
    }

    public String getUsername(){
        if(currentAccount != null){
            return currentAccount.getUsername();
        }
        return "[ERROR]";
    }

    public int fetchNumBankAccounts(){
        return bank.fetchNumBankAccounts(currentAccount.getUserID());
    }

    public double fetchAccountBalance(int accountID) throws NonExistentAccountException{
        return checkBalance(currentAccount.getUserID(), accountID);
    }

    public void userTransfer(int acct1, int acct2, double transferAmt) throws InsufficientFundsException, NonExistentAccountException, FrozenAccountException{
        transfer(currentAccount.getUserID(), acct1, currentAccount.getUserID(), acct2, transferAmt);
    }

    public void internalTransfer(int acct1, String username, double transferAmt) throws InsufficientFundsException, NonExistentAccountException, FrozenAccountException{
        transfer(currentAccount.getUserID(), acct1, bank.searchByUsername(username).getUserID(), 0, transferAmt);
    }
}