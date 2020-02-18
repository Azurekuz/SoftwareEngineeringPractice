package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class AdminTest {
    @Test
    void calcTotalAssets() throws NonExistentAccountException{
        CentralBank testBank = new CentralBank();
        testBank.createUser("A", "B", "C@D.com", 0);
        testBank.createUser("B", "C", "D@E.com", 1);
        testBank.createUser("C", "D", "E@F.com", 2);
        testBank.createBankAccount(0, 100);
        testBank.createBankAccount(0, 100);
        testBank.createBankAccount(0, 100);
        testBank.createBankAccount(1, 150);
        testBank.createBankAccount(1, 150);
        testBank.createBankAccount(2, 200);
        testBank.createBankAccount(2, 200);
        Admin testmin = new Admin("Admin", "pass", "Admin@Bank.com", 4);
        testmin.setManagedBank(testBank);
        assertEquals(1000, testmin.calcTotalAssets());
    }

    @Test
    void findAcctIdsWithSuspiciousActivity(){

    }

    @Test
    void freezeAccount() throws FrozenAccountException, NonExistentAccountException, InsufficientFundsException{
        CentralBank testBank = new CentralBank();
        testBank.createUser("A", "B", "C@D.com", 0);
        testBank.createBankAccount(0, 100);
        Admin testmin = new Admin("Admin", "pass", "Admin@Bank.com", 4);
        testmin.setManagedBank(testBank);

        testBank.withdraw(0,0, 50);
        assertEquals(50, testBank.checkBalance(0,0));

        testmin.freezeAccount(0,0);
        assertThrows(FrozenAccountException.class, ()->testBank.withdraw(0,0, 50));

        testmin.unfreezeAccount(0,0);
        testBank.withdraw(0,0, 50);
        assertEquals(0, testBank.checkBalance(0,0));
    }
}
