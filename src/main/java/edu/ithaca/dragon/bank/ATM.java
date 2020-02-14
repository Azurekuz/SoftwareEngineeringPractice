
package edu.ithaca.dragon.bank;

public class ATM implements BasicAPI {

    private UserAccount currentAccount;
    public CentralBank bank;

    public ATM(CentralBank bank){
        this.bank = bank;
    }
    public UserAccount confirmCredentials(String username, String password) throws NonExistentAccountException{

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
}