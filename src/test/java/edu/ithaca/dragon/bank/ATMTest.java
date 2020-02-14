package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ATMTest {

    @Test //Because ATM just calls central bank's functions mostly, only limited testing needed here - see CentralbankTest
    void allTests() throws NonExistentAccountException, InsufficientFundsException, FrozenAccountException{
        CentralBank bank = new CentralBank();
        bank.createUser("A", "Password", "a@b.com", 1);
        bank.createBankAccount(1, 100);
        ATM atm = new ATM(bank);
        atm.confirmCredentials("A","Password"); //setting currentUser

        //Check balance
        assertEquals(100, atm.checkBalance(1, 0));
        assertThrows(NonExistentAccountException.class, ()-> atm.checkBalance(3,5));

        //ConfirmCred
        assertEquals(1,atm.confirmCredentials("A","Password").getUserID());
        assertThrows(NonExistentAccountException.class, ()-> atm.confirmCredentials("a","pass"));

        //withdraw
        atm.withdraw(0, 10);
        assertEquals(90,atm.checkBalance(1,0));
        assertThrows(InsufficientFundsException.class, ()-> atm.withdraw(0,1000));

        //deposit
        atm.deposit(0, 11);
        assertEquals(101, atm.checkBalance(1,0));

        //transfer
        bank.createUser("B", "Password2", "b@c.com", 2);
        bank.createBankAccount(2, 100);
        atm.transfer(1,0, 2, 0, 50);
        assertEquals(51, atm.checkBalance(1,0));
        atm.confirmCredentials("B","Password2");//Logging in to second account
        assertEquals(150, atm.checkBalance(2,0));

        //TODO transaction history tests if/when that's done
    }
}
