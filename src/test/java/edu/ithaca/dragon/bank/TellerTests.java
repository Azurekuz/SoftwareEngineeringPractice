package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TellerTests {

    @Test
    public void createCloseAccountTests() throws NonExistentAccountException, FrozenAccountException{
        CentralBank bank = new CentralBank();

        Teller teller = new Teller(bank);
        teller.createUserAccount("A", "Password", "a@b.com", 1);
        teller.createBankAccount(1, 100);

        //insuring the new accounts exist by doing things to them
        assertEquals(1, bank.confirmCredentials("A","Password").getUserID());
        assertEquals(100, bank.checkBalance(1, 0));

        teller.closeBankAccount(1, 0);
        assertThrows(NonExistentAccountException.class, ()-> bank.checkBalance(1,0));
        assertThrows(NonExistentAccountException.class, ()-> bank.deposit(1,0, 100));

        teller.closeUserAccount(1);
        assertThrows(NonExistentAccountException.class, ()-> bank.confirmCredentials("A","Password"));

        //TODO not implemented yet, closing user should also close its bank accounts (and test it)
    }

}
