package atm.transaction;

import atm.bean.CashDispenser;
import atm.exception.TransactionException;
import atm.model.Account;

import java.util.Arrays;
import java.util.List;

public class Withdraw implements Transaction {
    private static List<Double> availableWithdrawAmount = Arrays.asList(20.0, 40.0, 60.0, 100.0, 200.0);

    private CashDispenser cashDispenser;

    public Withdraw(CashDispenser cashDispenser) {
        this.cashDispenser = cashDispenser;
    }

    @Override
    public double execute(Account account, double amount) {
        if (!availableWithdrawAmount.contains(amount)) {
            throw new TransactionException("Invalid withdraw choise.");
        }
        if (amount > account.getBalance()) {
            throw new TransactionException("Withdraw failed");
        }
        cashDispenser.checkAvailability(amount);

        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);

        return newBalance;
    }
}