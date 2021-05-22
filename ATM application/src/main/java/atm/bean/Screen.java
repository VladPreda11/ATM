package atm.bean;

import org.springframework.stereotype.Component;

@Component
public class Screen {
    public void displayMessage(String message) {
        System.out.println(message);
    }
}
