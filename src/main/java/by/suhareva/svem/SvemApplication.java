package by.suhareva.svem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages =  "by.suhareva.svem")
public class SvemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SvemApplication.class, args);
    }

}
