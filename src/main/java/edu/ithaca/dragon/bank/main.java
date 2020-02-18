package edu.ithaca.dragon.bank;

public class main {
    public static void main(String[] args) throws NonExistentAccountException{
        CentralBank testBank = new CentralBank();
        testBank.createUser("Bob", "12345", "bob@ithaca.edu", 0);
        testBank.createUser("Tom", "12345", "tom@ithaca.edu", 1);
        testBank.createBankAccount(0, 100);
        testBank.createBankAccount(0, 50);
        testBank.createBankAccount(1, 250);
        testBank.createBankAccount(1, 1000000);
        ATM testATM = new ATM(testBank);
        ATM_UI testUI = new ATM_UI(testATM);
        //testBank.freezeAccount(0, 0);
        //testBank.freezeUser(0);
        testUI.initializeATM();
    }
}
