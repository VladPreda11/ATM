package atm.transaction;

import atm.exception.TransactionException;
import atm.model.Account;

public class Deposit implements Transaction {
    @Override
    public double execute(Account account, double amount) {
        if (amount < 0) {
            throw new TransactionException("Deposit failed!");
        }

        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);

        return newBalance;
    }
}
