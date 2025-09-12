package ru.gt2.gar.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/// Загрузка данных в базу
@SpringBootApplication
public class LoadDataApp implements CommandLineRunner {
    public static void main(String... args) {
        SpringApplication.run(LoadDataApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
