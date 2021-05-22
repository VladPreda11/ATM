package atm.bean;

import atm.exception.CashDispenserException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class CashDispenser {
    private int noOfBills;
    private final String fileName = "cashdispenser.txt";

    public CashDispenser() {
        noOfBills = readFromFile();
        System.out.println("CashDispenser loaded");
    }

    private int readFromFile() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            String filePath = new File(classLoader.getResource(fileName).getFile()).getAbsolutePath();
            return Integer.parseInt(Files.readAllLines(Paths.get(filePath)).get(0));
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong during the database read");
        }
    }

    public void checkAvailability(Double amount) {
        if(amount % 20 != 0) {
            throw new CashDispenserException("Amount should be multiple of 20$");
        }
        if(noOfBills * 20 < amount) {
            throw new CashDispenserException("Not enough bills");
        }
        noOfBills -= amount / 20;
    }
}
