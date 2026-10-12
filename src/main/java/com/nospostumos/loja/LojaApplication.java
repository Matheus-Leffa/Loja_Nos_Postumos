package com.nospostumos.loja;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LojaApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        setSystemPropertyFromDotenv(dotenv, "DB_URL");
        setSystemPropertyFromDotenv(dotenv, "DB_USERNAME");
        setSystemPropertyFromDotenv(dotenv, "DB_PASSWORD");

        SpringApplication.run(LojaApplication.class, args);
    }

    private static void setSystemPropertyFromDotenv(Dotenv dotenv, String propertyName) {
        String value = dotenv.get(propertyName);

        if (value != null && !value.isBlank() && System.getProperty(propertyName) == null) {
            System.setProperty(propertyName, value);
        }
    }
}
