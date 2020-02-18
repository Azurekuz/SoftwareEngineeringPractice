package edu.ithaca.dragon.bank;
import java.util.InputMismatchException;
import java.util.Scanner;
public class ATM_UI {
    private ATM attachedATM;
    private Scanner userInput = new Scanner(System.in);
    ATM_UI(BasicAPI atm){
        attachedATM = (ATM) atm;
    }


    void promptLogin(){
        while(!attachedATM.isLoggedIn()){
            System.out.println("----------[WELCOME, GUEST]----------");
            System.out.println("Please kindly log in by providing your \n username and password.");
            System.out.print("[USERNAME]> ");
            String username = userInput.next();
            try {
                System.out.print("[PASSWORD]> ");
                String password = userInput.next();
                confirmCredentials(username, password);
            }catch(InputMismatchException e){
                System.out.println("[ERROR][Illegal argument provided!");
                userInput.next();
            }catch(FrozenAccountException e){
                promptFrozenAccountError();
            }catch(NonExistentAccountException e){
                System.out.println("[ERROR][Either the provided credentials are incorrect \n " +
                        "or the account does not exist.]");
            }
        }
    }

    void promptMainMenu(){
        while(attachedATM.isLoggedIn()){
            int menuChoice = -1;
            System.out.println("----------[WELCOME," + attachedATM.getUsername() + "]----------");
            System.out.println("-----[TOTAL FUNDS: $" + getTotalFunds() +" ]-----");
            System.out.println("-----[Available services are as follows]---");
            System.out.println(" [1] Check Balance \n [2] Withdraw \n [3] Deposit \n [4] Transfer \n [5] LOG OUT");
            while(menuChoice < 1 || menuChoice > 4){
                System.out.print("[CHOOSE]> ");
                try {
                    menuChoice = userInput.nextInt();
                }catch(InputMismatchException e){
                    System.out.println("[ERROR][Invalid input entered!]");
                    userInput.next();
                }
                switch(menuChoice){
                    case 1:
                        retrieveBankAccounts();
                        break;
                    case 2:
                        promptWithdraw();
                        break;
                    case 3:
                        promptDeposit();
                        break;
                    case 4:
                        promptTransfer();
                        break;
                    case 5:
                        logout();
                        break;
                    default:
                        System.out.println("[ERROR][Invalid menu choice! Please try again.]");
                        break;
                }
                break;
            }
        }
    }

    double getTotalFunds(){
        double sum = 0;
        for(int i = 0; i < attachedATM.fetchNumBankAccounts(); i++){
            try {
                sum = sum + attachedATM.fetchAccountBalance(i);
            }catch(NonExistentAccountException e){
                System.out.println("[ERROR][An internal error has occurred.]");
            }catch(InputMismatchException e){
                System.out.println("[ERROR][Invalid input entered!]");
                userInput.next();
            }
        }
        return sum;
    }

    void promptFrozenAccountError(){
        System.out.println("----------[ERROR]----------");
        System.out.println("Unfortunately, either your user account or" +
                "\n bank account has been frozen. Please contact \n" +
                "1-888-555-1212 for further details. Thank you.");
    }

    void chooseAccount(int userChoice){

    }

    void promptDeposit(){
        System.out.println("----------[DEPOSIT]----------");
        retrieveBankAccounts();
        int bankAccount = -1;
        double depositAmount = -1;
        while(attachedATM.fetchNumBankAccounts() > 0 && (bankAccount < 0 || bankAccount > attachedATM.fetchNumBankAccounts())){
            System.out.print("[CHOOSE ACCOUNT]>");
            try {
                bankAccount = userInput.nextInt() - 1;
            }catch(InputMismatchException e){
                System.out.println("[ERROR][Invalid input entered!]");
                userInput.next();
            }
            if(bankAccount < 0 || bankAccount > attachedATM.fetchNumBankAccounts()){
                System.out.println("[ERROR][Invalid account id entered!]");
                return;
            }
        }
        try {
            System.out.print("[ACCOUNT " + (bankAccount + 1) + " (" + attachedATM.fetchAccountBalance(bankAccount) + ") SELECTED]");
        }catch(NonExistentAccountException e){
            System.out.println("[ERROR][An internal error has occurred.]");
        }catch(InputMismatchException e){
            System.out.println("[ERROR][Illegal argument provided!");
            userInput.next();
        }

        try {
            System.out.print("[DEPOSIT]> ");
            depositAmount = userInput.nextDouble();
            attachedATM.deposit(bankAccount, depositAmount);
        }catch (IllegalArgumentException e){
            System.out.println("[ERROR][Illegal argument provided!");
        }catch (InsufficientFundsException e){
            System.out.println("[ERROR][Insufficient funds!]");
        }catch(FrozenAccountException e){
            System.out.println("[ERROR][We're sorry but this bank account has been frozen. \n" +
                    "Please contact customer service at 1-888-555-1212. \n" +
                    "We're sorry for the inconvenience.]");
        }catch(NonExistentAccountException e){
            System.out.println("[ERROR][An internal error has occurred.]");
        }catch(InputMismatchException e){
            System.out.println("[ERROR][Illegal argument provided!");
            userInput.next();
        }
    }

    void promptTransfer(){
        System.out.println("----------[TRANSFER]----------");
        int menuChoice = -1;
        System.out.println("What kind of transfer are you doing?");
        System.out.println("[1][User][Transfers within one's own account)");
        System.out.println("[2][Internal][Transfers to another account within this bank)");
        while(menuChoice < 1 || menuChoice > 2){
            try {
                menuChoice = userInput.nextInt();
            }catch(InputMismatchException e){
                System.out.println("[ERROR][Invalid input entered!]");
                userInput.next();
            }
            if(menuChoice < 1 || menuChoice > 2){
                System.out.println("[ERROR][Invalid argument provided!]");
            }
        }
        retrieveBankAccounts();
        switch (menuChoice){
            case 1:
                transferSelf();
                break;
            case 2:
                transferOther();
                break;
        }

    }



    void transferSelf(){
        int bankAccountA = -1;
        int bankAccountB = -1;
        double transferAmount = -1;
        while(attachedATM.fetchNumBankAccounts() > 0 && (bankAccountA < 0 || bankAccountA > attachedATM.fetchNumBankAccounts())){
            System.out.print("[CHOOSE ACCOUNT FROM]>");
            try {
                bankAccountA = userInput.nextInt() - 1;
            }catch(InputMismatchException e){
                System.out.println("[ERROR][Invalid input entered!]");
                userInput.next();
            }
            if(bankAccountA < 0 || bankAccountA > attachedATM.fetchNumBankAccounts()){
                System.out.println("[ERROR][Invalid account id entered!]");
                return;
            }
        }
        while(attachedATM.fetchNumBankAccounts() > 0 && (bankAccountB < 0 || bankAccountB > attachedATM.fetchNumBankAccounts())){
            System.out.print("[CHOOSE ACCOUNT TO][" + (bankAccountA + 1) + ">>> ?)>");
            try {
                bankAccountB = userInput.nextInt() - 1;
            }catch(InputMismatchException e){
                System.out.println("[ERROR][Invalid input entered!]");
                userInput.next();
            }
            if(bankAccountB < 0 || bankAccountB > attachedATM.fetchNumBankAccounts()){
                System.out.println("[ERROR][Invalid account id entered!]");
                return;
            }else if(bankAccountB == bankAccountA){
                System.out.println("[ERROR][You've selected the exact same account!]");
                return;
            }
        }

        try {
            System.out.print("[TRANSFER][" + (bankAccountA + 1) + ">>>" + (bankAccountB + 1) + ")> ");
            transferAmount = userInput.nextDouble();
            attachedATM.userTransfer(bankAccountA, bankAccountB, transferAmount);
        }catch( InsufficientFundsException e){
            System.out.println("[ERROR][Insufficient funds in bank account!]");
        }catch(NonExistentAccountException e){
            System.out.println("[ERROR][A provided bank account does not exist!]");
        }catch(FrozenAccountException e){
            System.out.println("[ERROR][We're sorry but this bank account has been frozen. \n" +
                    "Please contact customer service at 1-888-555-1212. \n" +
                    "We're sorry for the inconvenience.]");
        }catch(InputMismatchException e){
            System.out.println("[ERROR][Invalid input entered!]");
            userInput.next();
        }
    }

    void transferOther(){
        int bankAccountA = -1;
        String userTo;
        double transferAmount = -1;
        while(attachedATM.fetchNumBankAccounts() > 0 && (bankAccountA < 0 || bankAccountA > attachedATM.fetchNumBankAccounts())){
            System.out.print("[CHOOSE ACCOUNT FROM]>");
            try {
                bankAccountA = userInput.nextInt() - 1;
            }catch(InputMismatchException e){
                System.out.println("[ERROR][Invalid input entered!]");
                userInput.next();
            }
            if(bankAccountA < 0 || bankAccountA > attachedATM.fetchNumBankAccounts()){
                System.out.println("[ERROR][Invalid account id entered!]");
                return;
            }
        }
        System.out.print("[To whom are you transferring to?]> ");
        userTo = userInput.next();

        try {
            System.out.print("[TRANSFER][" + (bankAccountA + 1) + ">>>" + userTo + ")> ");
            transferAmount = userInput.nextDouble();
            attachedATM.internalTransfer(bankAccountA, userTo, transferAmount);
        }catch( InsufficientFundsException e){
            System.out.println("[ERROR][Insufficient funds in bank account!]");
        }catch(NonExistentAccountException e){
            System.out.println("[ERROR][A provided bank account or user does not exist!]");
        }catch(FrozenAccountException e){
            System.out.println("[ERROR][We're sorry but one of the bank accounts has been frozen. \n" +
                    "If it is yours then please contact customer service at 1-888-555-1212. \n" +
                    "We're sorry for the inconvenience.]");
        }catch(InputMismatchException e){
            System.out.println("[ERROR][Invalid input entered!]");
            userInput.next();
        }
    }

    void promptWithdraw() {
        System.out.println("----------[WITHDRAW]----------");
        retrieveBankAccounts();
        int bankAccount = -1;
        double withdrawAmount = -1;
        while(attachedATM.fetchNumBankAccounts() > 0 && (bankAccount < 0 || bankAccount > attachedATM.fetchNumBankAccounts())){
            System.out.print("[CHOOSE ACCOUNT]>");
            try {
                bankAccount = userInput.nextInt() - 1;
            }catch(InputMismatchException e){
                System.out.println("[ERROR][Invalid input entered!]");
                userInput.next();
            }
            if(bankAccount < 0 || bankAccount > attachedATM.fetchNumBankAccounts()){
                System.out.println("[ERROR][Invalid account id entered!]");
                return;
            }
        }
        try {
            System.out.print("[ACCOUNT " + (bankAccount + 1) + " (" + attachedATM.fetchAccountBalance(bankAccount) + ") SELECTED]");
        }catch(NonExistentAccountException e){
            System.out.println("[ERROR][An internal error has occurred.]");
        }catch(InputMismatchException e){
            System.out.println("[ERROR][Illegal argument provided!");
            userInput.next();
        }
        try {
            System.out.print("[WITHDRAW]> ");
            withdrawAmount = userInput.nextDouble();
            attachedATM.withdraw(bankAccount, withdrawAmount);
        }catch (IllegalArgumentException e){
            System.out.println("[ERROR][Illegal argument provided!");
        }catch (InsufficientFundsException e){
            System.out.println("[ERROR][Insufficient funds!]");
        }catch(FrozenAccountException e){
            System.out.println("[ERROR][We're sorry but this bank account has been frozen. \n" +
                    "Please contact customer service at 1-888-555-1212. \n" +
                    "We're sorry for the inconvenience.]");
        }catch(NonExistentAccountException e){
            System.out.println("[ERROR][An internal error has occurred.]");
        }catch(InputMismatchException e){
            System.out.println("[ERROR][Illegal argument provided!");
            userInput.next();
        }
    }

    void confirmCredentials(String username, String password) throws NonExistentAccountException, FrozenAccountException{
        attachedATM.confirmCredentials(username, password);
    }

    void initializeATM(){
        while(true){
            promptLogin();
            promptMainMenu();
        }
    }

    void retrieveBankAccounts(){
        System.out.println("-----[YOUR BANK ACCOUNTS]-----");
        System.out.println("Here are your accounts.");
        for(int i = 0; i < attachedATM.fetchNumBankAccounts(); i++){
            try {
                System.out.println("[" + (i + 1) + "][ACCOUNT " + (i + 1) + "][ $" + attachedATM.fetchAccountBalance(i));
            }catch(NonExistentAccountException e){
                System.out.println("[ERROR][An internal error has occurred.]");
            }
        }
        System.out.println("------------------------------");
    }

    void logout(){
        attachedATM.logout();
    }
}