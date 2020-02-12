package edu.ithaca.dragon.bank;

import java.util.Collection;

public class CentralBank implements AdvancedAPI, AdminAPI {

    private UserArrayList userAccounts;
    private BankAccountCollection bankAccountCollection;

    public CentralBank(){
        userAccounts = new UserArrayList();
        bankAccountCollection = new BankAccountCollection();
    }

    //----------------- BasicAPI methods -------------------------//

    public boolean confirmCredentials(int acctId, String password) {
        return false;
    }

    public double checkBalance(int userID) throws NonExistentAccountException{
        return bankAccountCollection.retrieveAccount(userID, 0).getBalance();
    }

    public double checkBalance(int userID, int acctId) throws NonExistentAccountException{
        return bankAccountCollection.retrieveAccount(userID, acctId).getBalance();
    }

    public void withdraw(int userId, double amount) throws InsufficientFundsException, NonExistentAccountException{
        bankAccountCollection.retrieveAccount(userId, 0).withdraw(amount);
    }

    public void withdraw(int userId, int bankAccID, double amount) throws InsufficientFundsException, NonExistentAccountException {
        bankAccountCollection.retrieveAccount(userId, bankAccID).withdraw(amount);
    }

    public void deposit(int userId, double amount) throws InsufficientFundsException, NonExistentAccountException{
        bankAccountCollection.retrieveAccount(userId, 0).deposit(amount);
    }

    public void deposit(int userId, int bankAccID, double amount) throws InsufficientFundsException, NonExistentAccountException {
        bankAccountCollection.retrieveAccount(userId, bankAccID).deposit(amount);
    }

    public void transfer(int userIDFrom, int acctIdToWithdrawFrom, int userIDTo, int acctIdToDepositTo, double amount) throws InsufficientFundsException, NonExistentAccountException {
        //TODO
        bankAccountCollection.retrieveAccount(userIDFrom, acctIdToWithdrawFrom).transfer(amount,bankAccountCollection.retrieveAccount(userIDTo,acctIdToDepositTo));
    }

    public String transactionHistory(int userId) {
        return null;
    }


    //----------------- AdvancedAPI methods -------------------------//

    public void createUser(String userName, String password, String email, int userID){
        userAccounts.addAccount(new UserAccount(userName, password, email, userID));
    }

    public void createAccount(int userId, double startingBalance) throws NonExistentAccountException{
        UserAccount currentAccount = userAccounts.findAccount(userId);
        BankAccount newAccount = new BankAccount(currentAccount.getEmail(), startingBalance, userId);
        bankAccountCollection.addBankAccount(newAccount);
    }

    public void closeAccount(int userId, int accID) throws NonExistentAccountException{
        bankAccountCollection.removeBankAccount(userId, accID);
    }


    //------------------ AdminAPI methods -------------------------//

    public double calcTotalAssets() {
        return 0;
    }

    public Collection<Integer> findAcctIdsWithSuspiciousActivity() {
        return null;
    }

    public void freezeAccount(int userId) {

    }

    public void unfreezeAcct(int userId) {

    }

}
