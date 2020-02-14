package edu.ithaca.dragon.bank;

import java.util.Collection;

public interface AdminAPI {

    public double calcTotalAssets();

    public Collection<Integer> findAcctIdsWithSuspiciousActivity();

    public void freezeAccount(int userID, int acctId) throws NonExistentAccountException;

    public void unfreezeAccount(int userID, int acctId) throws NonExistentAccountException;

}
