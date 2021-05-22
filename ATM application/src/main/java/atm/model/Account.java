package atm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@AllArgsConstructor
public class Account {
    @Getter
    private int accountNumber;

    private byte[] pin;

    @Getter @Setter
    private double balance;

    public boolean checkPin(byte[] pin) {
        return Arrays.equals(this.pin, pin);
    }

    @Override public String toString() {
        return accountNumber + "-" + pin + "-" + balance;
    }
}