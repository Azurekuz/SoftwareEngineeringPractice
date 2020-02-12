package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CentralBankTest {
    @Test
    void confirmCredentialsTest() {
        //TODO add more?
        CentralBank testBank = new CentralBank();
        testBank.createUserAccount("user1","pass1","a@b.com",1);
        assertFalse(testBank.confirmCredentials("usr1","pass")); //both wrong
        assertFalse(testBank.confirmCredentials("user1","1pass")); //password wrong
        assertFalse(testBank.confirmCredentials("1resu","pass1")); //user wrong
        assertTrue(testBank.confirmCredentials("user1","pass1"));


    }

    @Test
    void checkBalanceTest() throws NonExistentAccountException{
        CentralBank testBank = new CentralBank();
        testBank.createUser("A", "Password", "a@b.com", 1);
        testBank.createBankAccount(1, 100);
        assertEquals(100, testBank.checkBalance(1));

        testBank.createUser("B", "Password", "c@d.com", 2);
        testBank.createBankAccount(2, 0);
        assertEquals(0, testBank.checkBalance(2));
        assertNotEquals(0, testBank.checkBalance(2));

        testBank.createUser("C", "Password", "e@f.com", 3);
        testBank.createBankAccount(3, 1);
        assertEquals(1, testBank.checkBalance(3));
        assertNotEquals(1, testBank.checkBalance(2));
        assertNotEquals(1, testBank.checkBalance(1));
    }

    @Test
    void withdrawTest() throws IllegalArgumentException, InsufficientFundsException, NonExistentAccountException{
        CentralBank testBank = new CentralBank();
        testBank.createUser("A", "Password", "a@b.com", 1);
        testBank.createBankAccount(1, 100);

        testBank.withdraw(1, 10);
        assertEquals(90, testBank.checkBalance(1));

        testBank.withdraw(1, 1);
        assertEquals(89, testBank.checkBalance(1));

        testBank.withdraw(1, 0);
        assertEquals(89, testBank.checkBalance(1));

        testBank.withdraw(1, 10.5);
        assertEquals(78.5, testBank.checkBalance(1));

        testBank.withdraw(1, 10.50);
        assertEquals(68, testBank.checkBalance(1));

        testBank.withdraw(1, 10.500);
        assertEquals(57.5, testBank.checkBalance(1));

        assertThrows(IllegalArgumentException.class, ()->testBank.withdraw(1, 10.123));

        assertThrows(InsufficientFundsException.class, () -> testBank.withdraw(1, 1000));

        assertThrows(IllegalArgumentException.class, () -> testBank.withdraw(1, -1000));

    }

    @Test
    void depositTest() throws IllegalArgumentException, NonExistentAccountException, InsufficientFundsException{
        CentralBank testBank = new CentralBank();
        testBank.createUser("A", "Password", "a@b.com", 1);
        testBank.createBankAccount(1, 100);

        testBank.deposit(1, 10);
        assertEquals(110, testBank.checkBalance(1));

        testBank.deposit(1, 1);
        assertEquals(111, testBank.checkBalance(1));

        testBank.deposit(1, 0);
        assertEquals(111, testBank.checkBalance(1));

        testBank.deposit(1, 1.5);
        assertEquals(112.5, testBank.checkBalance(1));

        testBank.deposit(1, 1.55);
        assertEquals(114.05, testBank.checkBalance(1));

        assertThrows(IllegalArgumentException.class, () -> testBank.deposit(1, 1.555));

        assertThrows(IllegalArgumentException.class, () -> testBank.deposit(1, -1));
    }

    @Test
    void getHistoryTest() {

    }

    @Test
    void createBankAccountTest() throws NonExistentAccountException{
        CentralBank testBank = new CentralBank();
        testBank.createBankAccount(1, 100);
        assertEquals(100, testBank.checkBalance(1));

        testBank.createBankAccount(2, 1);
        assertEquals(1, testBank.checkBalance(2));

        testBank.createBankAccount(3, 1.000);
        assertEquals(0, testBank.checkBalance(3));

        testBank.createBankAccount(4, 0);
        assertEquals(0, testBank.checkBalance(4));

        assertThrows(IllegalArgumentException.class, ()-> testBank.createBankAccount(5, -1));
        assertThrows(IllegalArgumentException.class, ()-> testBank.createBankAccount(5, 1.234));
    }

    @Test
    void closeAccountTest() throws NonExistentAccountException{
        CentralBank testBank = new CentralBank();
        testBank.createBankAccount(1, 100);

        testBank.closeBankAccount(1);
        assertThrows(NonExistentAccountException.class, ()->testBank.checkBalance(1));
    }

    @Test
    void getSuspiciousAccountsTest() {

    }

    @Test
    void freezeAccountTest() {

    }

    @Test
    void unfreezeAccountTest() {

    }
}
