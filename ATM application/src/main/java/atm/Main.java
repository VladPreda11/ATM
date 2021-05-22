package atm;

import atm.bean.ATM;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);

        ATM atm = applicationContext.getBean(ATM.class);

        atm.run();
    }
}
