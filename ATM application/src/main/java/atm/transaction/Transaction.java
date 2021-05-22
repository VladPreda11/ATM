package atm.transaction;

import atm.model.Account;

public interface Transaction {
    double execute(Account account, double amount);
}
