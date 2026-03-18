package ru.gt2.gar.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfiguration {
    @Bean
    public NamingStrategy namingStrategy() {
        return NamingStrategy.LOWER_UNDERSCORE;
    }
}
