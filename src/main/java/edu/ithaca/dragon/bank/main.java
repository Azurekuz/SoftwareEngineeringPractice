import edu.ithaca.dragon.*;
public class main {
    public static void main(String[] args) {
        CentralBank testBank = new CentralBank();
        testBank.createUser("Eugene", "12345", "ekuznetsov@ithaca.edu", 0);
        testBank.createBankAccount(0, 100);
        ATM testATM = new ATM(testBank);
        ATM_UI testUI = new ATM_UI(testATM);

        testUI.initializeATM();
    }
}
