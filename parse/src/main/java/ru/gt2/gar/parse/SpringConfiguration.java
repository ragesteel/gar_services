package ru.gt2.gar.parse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gt2.gar.parse.xml.AllXMLProcessors;

@Configuration
public class SpringConfiguration {
    @Bean
    public AllXMLProcessors allXMLProcessors(@Value("${gar.parse.batch:1024}") int batchSize) {
        return new AllXMLProcessors(batchSize);
    }
}
