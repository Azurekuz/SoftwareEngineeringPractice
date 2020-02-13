package edu.ithaca.dragon.bank;

//API to be used by ATMs
public interface BasicAPI {

    UserAccount confirmCredentials(String username, String password) throws NonExistentAccountException;

    double checkBalance(int userID, int acctId) throws NonExistentAccountException;

    void withdraw(int acctId, double amount) throws InsufficientFundsException, NonExistentAccountException;

    void deposit(int acctId, double amount) throws InsufficientFundsException, NonExistentAccountException;

    void transfer(int userIDFrom, int acctIdToWithdrawFrom, int userIDTo, int acctIdToDepositTo, double amount) throws InsufficientFundsException, NonExistentAccountException;

    String transactionHistory(int acctId);

}
