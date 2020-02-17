package edu.ithaca.dragon.bank;

public class savingsAccount extends BankAccount {

    /**
     * @param email           a String denoting the email address of the account owner.
     * @param startingBalance a double denoting the amount of initial money in the account.
     * @param userID
     * @throws IllegalArgumentException if email or starting balance is invalid
     * @post The constructor of the BankAccount class
     */
    private boolean flagged =false;
    private boolean frozen =false;
    private double withdrawLimit = balance;
    public double interest;
    public savingsAccount(String email, double startingBalance, int userID) {
        super(email, startingBalance, userID);
    }
    public  void ApplyInterest(String userId,String email){
        double interest=this.balance*this.interest;
        this.balance= this.balance+interest;

    }
    public void WithdrawLimit(double amount) throws InsufficientFundsException {
        if(amount>this.withdrawLimit){
            throw new InsufficientFundsException("You request exceeded the withdraw limit");

        }

    }
    public void setInterest(double interestToSet){
        this.interest=interestToSet;
    }
}
