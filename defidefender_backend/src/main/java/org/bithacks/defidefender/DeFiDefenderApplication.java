package org.bithacks.defidefender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
public class DeFiDefenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeFiDefenderApplication.class, args);
    }

}
