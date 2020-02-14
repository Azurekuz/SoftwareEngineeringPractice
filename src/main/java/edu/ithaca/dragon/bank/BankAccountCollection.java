package edu.ithaca.dragon.bank;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

public class BankAccountCollection {
    private HashMap<Integer, ArrayList<BankAccount>> collection;
    BankAccountCollection(){
        collection = new HashMap<Integer, ArrayList<BankAccount>>();
    }

    void addBankAccount(BankAccount newAccount){
        if(!collection.containsKey(newAccount.getUserID())){
            collection.put(newAccount.getUserID(), new ArrayList<BankAccount>());

        }
        collection.get(newAccount.getUserID()).add(newAccount);
    }

    void removeBankAccount(int userID, int bankAccountIndex) throws NonExistentAccountException, IllegalArgumentException{
        if(!collection.containsKey(userID)){
            throw new NonExistentAccountException("The requested account does not exist!");
        }else{
            collection.get(userID).remove(collection.get(userID).get(bankAccountIndex));
        }
    }

    BankAccount retrieveAccount(int userID, int bankAccountIndex) throws NonExistentAccountException, IllegalArgumentException{
        if(!collection.containsKey(userID)){
            throw new NonExistentAccountException("The requested user account does not exist!");
        }else if(userID < 0 || bankAccountIndex < 0){
            throw new IllegalArgumentException("You have provided an invalid userID or account index!");
        }else if(bankAccountIndex > (collection.get(userID).size() - 1)){
            throw new NonExistentAccountException("You are attempting to access a non-existent BankAccount index.");
        }else{
            return collection.get(userID).get(bankAccountIndex);
        }
    }

    int getNumAccounts(int userID){
        return collection.get(userID).size();
    }

    double sumAll(){
        double totalSum = 0;
        for(Integer user: collection.keySet()){
            for(int bankAccount = 0;bankAccount < collection.get(user).size();bankAccount++){
                totalSum = totalSum + collection.get(user).get(bankAccount).getBalance();
            }
        }
        return totalSum;
    }
}
