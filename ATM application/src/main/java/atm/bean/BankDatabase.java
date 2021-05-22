package atm.bean;

import atm.model.Account;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.stream.Stream;

@Component
public class BankDatabase {
    private HashMap<Integer, Account> accounts = new HashMap<>();
    private final String fileName = "database.txt";

    public BankDatabase() {
        readAllAccounts();
    }

    public Account getAccount(int accountId) {
        return accounts.get(accountId);
    }

    private void readAllAccounts() {
        ClassLoader classLoader = getClass().getClassLoader();
        String filePath = new File(classLoader.getResource(fileName).getFile()).getAbsolutePath();
        try(Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(this::put);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Database loaded");
    }

    private void put(String line) {
        String[] lineSplit = line.split("-");

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] pin = lineSplit[1].getBytes();
            byte[] hashedPin = messageDigest.digest(pin);
            Account account = new Account(Integer.parseInt(lineSplit[0]), hashedPin, Double.parseDouble(lineSplit[2]));
            accounts.put(account.getAccountNumber(), account);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void updateAccounts() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());
            OutputStream outputStream = new FileOutputStream(file);
            try (
                    BufferedWriter br
                            = new BufferedWriter(new OutputStreamWriter(outputStream))
            ) {
                accounts.values().forEach(account -> {
                    try {
                        br.write(account.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong during the database updating");
        }
    }
}