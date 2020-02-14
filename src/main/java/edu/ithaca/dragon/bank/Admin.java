package edu.ithaca.dragon.bank;

import java.util.Collection;
import java.util.Iterator;

public class Admin extends UserAccount implements AdminAPI{
    private CentralBank managedBank;

    public Admin(String username, String password, String email, int userID){
        super(username, password, email, userID);
    }

    public void setManagedBank(CentralBank bankToManage){
        managedBank = bankToManage;
    }

    public double calcTotalAssets(){
        return managedBank.calcTotalAssets();
    }

    public Collection<Integer> findAcctIdsWithSuspiciousActivity(){
        Collection<Integer> test = new Collection<Integer>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Integer> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Integer integer) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Integer> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }
        };
        return test;
    }

    public void freezeAccount(int userID, int acctId) throws NonExistentAccountException{
        managedBank.freezeAccount(userID, acctId);
    }

    public void unfreezeAccount(int userID, int acctId) throws NonExistentAccountException{
        managedBank.unfreezeAccount(userID, acctId);
    }
}
