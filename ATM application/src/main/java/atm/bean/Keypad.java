package atm.bean;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Keypad {
    private final Scanner scanner;

    public Keypad() {
        this.scanner = new Scanner(System.in);
    }

    public int getInput() {
        return scanner.nextInt();
    }

    public double getAmount() {
        return scanner.nextDouble();
    }

    public String getPin() {
        return scanner.next();
    }
}
