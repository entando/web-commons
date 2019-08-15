package org.entando.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.entando")
public class EntandoTestJavaApplication {

    public static void main(final String[] args) {
        SpringApplication.run(EntandoTestJavaApplication.class, args);
    }

}
