package edu.ithaca.dragon.bank;

import java.util.ArrayList;
import java.util.Collection;

public class CentralBank implements AdvancedAPI, AdminAPI {

    private UserArrayList userAccounts;
    private BankAccountCollection bankAccountCollection;

    public CentralBank(){
        //TODO need to only have one of each collection across bank/atm/teller
        userAccounts = new UserArrayList();
        bankAccountCollection = new BankAccountCollection();
    }

    //----------------- BasicAPI methods -------------------------//

    public UserAccount confirmCredentials(String username, String password) throws NonExistentAccountException {
        try {
            UserAccount account = userAccounts.findAccount(username);
            //If username is wrong it will go to the catch and return false
            if (account.getPassword().equals(password)) return account;
        }
        catch(Exception NonExistentAccountException){
            throw new NonExistentAccountException("Account not found");
        }


        throw new NonExistentAccountException("Credentials incorrect");
    }

    public double checkBalance(int userID) throws NonExistentAccountException{
        return bankAccountCollection.retrieveAccount(userID, 0).getBalance();
    }

    public double checkBalance(int userID, int acctId) throws NonExistentAccountException{
        return bankAccountCollection.retrieveAccount(userID, acctId).getBalance();
    }

    public void withdraw(int userId, double amount) throws InsufficientFundsException, NonExistentAccountException, FrozenAccountException{
        bankAccountCollection.retrieveAccount(userId, 0).withdraw(amount);
    }

    public void withdraw(int userId, int bankAccID, double amount) throws InsufficientFundsException, NonExistentAccountException, FrozenAccountException {
        bankAccountCollection.retrieveAccount(userId, bankAccID).withdraw(amount);
    }

    public void deposit(int userId, double amount) throws NonExistentAccountException, FrozenAccountException{
        bankAccountCollection.retrieveAccount(userId, 0).deposit(amount);
    }

    public void deposit(int userId, int bankAccID, double amount) throws InsufficientFundsException, NonExistentAccountException, FrozenAccountException {
        bankAccountCollection.retrieveAccount(userId, bankAccID).deposit(amount);
    }

    public void transfer(int userIDFrom, int acctIdToWithdrawFrom, int userIDTo, int acctIdToDepositTo, double amount) throws InsufficientFundsException, NonExistentAccountException, FrozenAccountException {
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

    public void createAdministrator(String userName, String password, String email, int userID){
        Admin newAdmin = new Admin(userName, password, email, userID);
        newAdmin.setManagedBank(this);
        userAccounts.addAccount(newAdmin);
    }

    public void createBankAccount(int userId, double startingBalance) throws NonExistentAccountException{
        UserAccount currentAccount = userAccounts.findAccount(userId);
        BankAccount newAccount = new BankAccount(currentAccount.getEmail(), startingBalance, userId);
        bankAccountCollection.addBankAccount(newAccount);
    }

    public void closeBankAccount(int userId, int acctID) throws NonExistentAccountException{
        bankAccountCollection.removeBankAccount(userId, acctID);
    }

    public void createUserAccount(String username, String password, String email, int userID){
        UserAccount newAccount = new UserAccount(username, password, email, userID);
        userAccounts.addAccount(newAccount);
    }

    public void closeUserAccount(int userId) throws IllegalArgumentException, NonExistentAccountException{
        UserAccount removedAccount = userAccounts.findAccount(userId);
        userAccounts.removeAccount(removedAccount);

    }

    public int fetchNumBankAccounts(int userID){
        return bankAccountCollection.getNumAccounts(userID);
    }


    //------------------ AdminAPI methods -------------------------//

    public double calcTotalAssets() {
        return bankAccountCollection.sumAll();
    }

    public Collection<Integer> findAcctIdsWithSuspiciousActivity() {
        return null;
    }

    public void freezeAccount(int userId, int accID) throws NonExistentAccountException{
        bankAccountCollection.retrieveAccount(userId, accID).setFrozen(true);
    }

    public void unfreezeAccount(int userId, int accID)  throws NonExistentAccountException{
        bankAccountCollection.retrieveAccount(userId, accID).setFrozen(false);
    }

    public UserAccount searchByUsername(String username) throws NonExistentAccountException{
        return userAccounts.findAccount(username);
    }
}
