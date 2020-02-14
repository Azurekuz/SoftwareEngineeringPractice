package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;
    private int userID; //same userID data is used for UserAccounts
    private boolean flagged;
    private boolean frozen;

    /**
     * @post The constructor of the BankAccount class
     * @param email a String denoting the email address of the account owner.
     * @param startingBalance a double denoting the amount of initial money in the account.
     * @throws IllegalArgumentException if email or starting balance is invalid
     */
    public BankAccount(String email, double startingBalance, int userID) {
        if(!isAmountValid(startingBalance)){
            throw new IllegalArgumentException("Starting balance: " + startingBalance + " is invalid, cannot create account");
        }
        if (isEmailValid(email)) {
            this.email = email;
            this.balance = startingBalance;
            this.userID = userID;
        }else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance() {
        /**
         * @post
         * This function simply returns the current amount of money in the account. On it's own, it should
         * not care about its validity as it is presently structured as a getter-type of function.
         * @return a double which denotes the balance or money left on the current account.
         */
        return balance;
    }

    /**
     * @post A getter for the email data member.
     * @return a string denoting the email associated with the current account.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @post A getter for the userID data member.
     * @return an int denoting the userID associated with the current account.
     */
    public int getUserID(){ return userID; }

    /**
     * Only used when the associated UserAccount's userID is changed (shouldn't be changed from here)
     * @post this account's userID is updated with new userID input param
     * @param userID int //TODO decide on standardized userID length/format
     */
    public void updateUserID(int userID){
        this.userID=userID;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative, non-zero (of course) and smaller or equal to balance.
     * Allows withdrawals of numeric values with up to two decimal places. Anything beyond is not valid.
     * @throws InsufficientFundsException if there are not enough funds for a given withdrawal.
     */
    public void withdraw (double amount) throws InsufficientFundsException, IllegalArgumentException, FrozenAccountException{
        if(frozen){
            throw new FrozenAccountException("This account is currently frozen");
        }
        if(!isAmountValid(amount)){
            throw new IllegalArgumentException("Illegal amount entered");
        }
        if (amount <= balance) {
             balance -= amount;
        }else {
            throw new InsufficientFundsException("Not enough money");
        }
    }

    /**
     * @post a function to check if the amount of provided money is a valid input
     * @param amount how much money is provided as an input to a given function.
     * @return true or false depending on if the amount follows the limitations appropriately.
     */
    public boolean isAmountValid(double amount){
        if(amount < 0){
            return false;
        }else if((amount * 100)%1 != 0 && String.valueOf(amount).substring(String.valueOf(amount).indexOf(".") + 1).length() > 2){
            String why = String.valueOf(amount).substring(String.valueOf(amount).indexOf("."));
            return false;
        }else{
            return true;
        }
    }

    /**
     * @post A function for moving money from this account, to another account.
     * @param amount how much money should be transferred out of this current account.
     * @param otherAccount the account to which the amount is being transferred to.
     * @throws IllegalArgumentException if invalid amount is provided.
     * @throws InsufficientFundsException if not enough money is being withdrawn.
     */
    public void transfer(double amount, BankAccount otherAccount) throws InsufficientFundsException, IllegalArgumentException, FrozenAccountException{
        if(frozen){
            throw new FrozenAccountException("This account is currently frozen");
        }
        withdraw(amount);
        otherAccount.deposit(amount);
    }

    /**
     * @post A function to add money to the present account.
     * @param amount how much money to add to present account object.
     * @throws IllegalArgumentException if invalid amount is provided. Must be non-negative
     * with only up to two decimal places if they're nonzero.
     */
    public void deposit(double amount) throws IllegalArgumentException, FrozenAccountException{
        if(frozen){
            throw new FrozenAccountException("This account is currently frozen");
        }
        if(!isAmountValid(amount)){
            throw new IllegalArgumentException("Illegal amount provided");
        }else{
            balance += amount;
        }
    }

    public static boolean isEmailValid(String email) {
        return email.matches("(\\w)+((_|\\.|-)+\\w+)?@(\\w)+((_|\\.|-)+\\w+)?\\.\\w{2,}$");
    }

    public static boolean prefixCheck(String subString1) {
        int i = 0;
        if (subString1.contains("_") == true) {
            int checking = subString1.indexOf("_") + 1;
            return charCheck((subString1.charAt(checking)));

        }

        if (subString1.contains("-") == true) {
            int checking = subString1.indexOf("-") + 1;
            return charCheck((subString1.charAt(checking)));

        }
        if (subString1.contains(".") == true) {
            int checking = subString1.indexOf(".") + 1;
            return charCheck((subString1.charAt(checking)));

        }
        while (i < subString1.length()) {
            char x1 = subString1.charAt(i);
            if ((((int) x1 >= 48 && (int) x1 < 58) || ((int) x1 >= 65 && (int) x1 < 91) || ((int) x1 >= 97 && (int) x1 < 123)) == true) {
                i = i + 1;

            } else {
                return false;
            }

        }
        return true;


    }

    public static boolean charCheck(char cCheck) {
        if ((((int) cCheck >= 48 && (int) cCheck < 58) || ((int) cCheck >= 65 && (int) cCheck < 91) || ((int) cCheck >= 97 && (int) cCheck < 123)) == true) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean endCheck(String subString3) {
        String end1 = "cc";
        String end2 = "com";
        String end3 = "org";
        if (subString3.equals(end1)) {
            return true;
        }
        if (subString3.equals(end2)) {
            return true;
        }
        if (subString3.equals(end3)) {
            return true;
        }

        return false;


    }

    public void setFrozen(boolean isFrozen){
        frozen = isFrozen;
    }

}