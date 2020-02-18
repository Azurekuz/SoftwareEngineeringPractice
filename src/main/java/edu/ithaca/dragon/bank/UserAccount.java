package edu.ithaca.dragon.bank;

public class UserAccount {

    private String username;
    private String password;
    private String email;
    private int userID;
    boolean frozen = false;

    /**
     * Constructor
     * @param username the account's unique username, generally an actual name
     * @param password the account's password //TODO security?
     * @param email the account's unique email
     * @param userID the account's unique userID
     *        Both email and userID are used for the user's BankAccounts
     */
    public UserAccount(String username, String password, String email, int userID) {
        this.username = username;   //TODO check UserCollection to make sure username is unique
        this.password = password;   //
        this.email = email;         //TODO find a way to reuse isEmailValid without just copy/paste
        this.userID = userID;
    }

    //TODO
    /**
     * Finds all of this user's bank accounts, going through AccountCollection
     * @return collection of this user's BankAccounts
     */
    public BankAccount[] findBankAccounts(){
        return null;
    }

    /**
     *  A getter for the username data member.
     * @return a string denoting the username associated with the current account.
     */
    public String getUsername() {
        return username;
    }
    /**
     *  A getter for the password data member.
     * @return a string denoting the password associated with the current account.
     */
    public String getPassword() {
        return password;
    }
    /**
     *  A getter for the email data member.
     * @return a string denoting the email associated with the current account.
     */
    public String getEmail() {
        return email;
    }
    /**
     *  A getter for the userID data member.
     * @return an int denoting the userID associated with the current account.
     */
    public int getUserID() {
        return userID;
    }
    /**
     *  A setter for the username data member.
     * @param username a string with the new username
     * @post username is changed to input parameter
     */
    public void changeUsername(String username) {
        this.username = username;
    }
    /**
     *  A setter for the password data member.
     * @param password a string with the new password
     * @post password is changed to input parameter
     */
    public void changePassword(String password) {
        this.password = password;
    }
    /**
     *  A setter for the email data member.
     * @param email a string with the new username
     * @post email is changed to input parameter
     */
    public void changeEmail(String email) {
        this.email = email;
    }
    /**
     *  A setter for the userID data member.
     * @param userID an int with the new userID
     * @post userID is changed to input parameter
     */
    public void changeUserID(int userID) {
        this.userID = userID;
        //TODO update associated BankAccounts
    }
    /**
     * @return A string with all of the data in this UserAccoun
     */
    @Override
    public String toString() {
        return "UserAccount{" + "username = " + username + ", password = " + password +
                ", email = " + email + ", userID = " + userID + '}';
    }

    public void setFreeze(boolean frozen){
        this.frozen = frozen;
    }

    public boolean getFreeze(){
        return frozen;
    }
}
