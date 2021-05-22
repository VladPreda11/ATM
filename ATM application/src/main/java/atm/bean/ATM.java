package atm.bean;

import atm.exception.CashDispenserException;
import atm.exception.TransactionException;
import atm.model.Account;
import atm.transaction.Deposit;
import atm.transaction.Transaction;
import atm.transaction.Withdraw;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class ATM {
    private Screen screen;
    private Keypad keypad;
    private BankDatabase database;
    private CashDispenser cashDispenser;

    private Account currentUser;

    public ATM() {
        this.screen = new Screen();
        this.keypad = new Keypad();
        this.database = new BankDatabase();
        this.cashDispenser = new CashDispenser();
    }

    public void run() {
        while (true) {
            if (currentUser == null) {
                login();
                continue;
            }

            displayMenu();

            int actionChosen = keypad.getInput();

            if (!executeAction(actionChosen)) {
                return;
            }
        }
    }

    private boolean executeAction(int actionChosen) {
        switch (actionChosen) {
            case 1:
                screen.displayMessage("Your balance is " + currentUser.getBalance());
                break;
            case 2:
                executeTransaction(new Deposit());
                break;
            case 3:
                showWithdrawSubmenu();
                executeTransaction(new Withdraw(cashDispenser));
                break;
            case 4:
                currentUser = null;
                break;
            case 5:
                return false;
            default:
                screen.displayMessage("Invalid command");
                break;
        }

        return true;
    }

    private void executeTransaction(Transaction transaction) {
        screen.displayMessage("Enter the amount!");
        double amount = keypad.getAmount();
        try {
            double newBalance = transaction.execute(currentUser, amount);
            screen.displayMessage("Your new balance is: " + newBalance);
        } catch (TransactionException | NullPointerException | CashDispenserException e) {
            screen.displayMessage(e.getMessage());
        }
    }

    private void login() {
        screen.displayMessage("Please insert your account number: ");
        int accountNumber = keypad.getInput();
        screen.displayMessage("Please insert your pin: ");
        String pin = keypad.getPin();

        byte[] hashedPin = pin.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            hashedPin = messageDigest.digest(hashedPin);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Account account = database.getAccount(accountNumber);
        if (account == null) {
            screen.displayMessage("Incorrect login info");
            return;
        }

        if (!account.checkPin(hashedPin)) {
            screen.displayMessage("Incorrect login info");
            return;
        }

        currentUser = account;
        screen.displayMessage("You are successfully authenticated!");
    }

    private void displayMenu() {
        screen.displayMessage("-----------------------------------------------");
        screen.displayMessage("Choose one of the following actions:");
        screen.displayMessage("1. View balance.");
        screen.displayMessage("2. Deposit.");
        screen.displayMessage("3. Withdraw.");
        screen.displayMessage("4. Logout.");
        screen.displayMessage("5. Exit.");
    }

    private void showWithdrawSubmenu() {
        screen.displayMessage("- 20$");
        screen.displayMessage("- 40$");
        screen.displayMessage("- 60$");
        screen.displayMessage("- 100$");
        screen.displayMessage("- 200$");
    }
}