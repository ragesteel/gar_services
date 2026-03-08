package ru.gt2.gar.parse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gt2.gar.parse.xml.XMLProcessors;
import ru.gt2.gar.parse.xml.stax2.StAX2Processors;

@Configuration
public class SpringConfiguration {
    @Bean
    public XMLProcessors xmlProcessors(@Value("${gar.parse.batch:1024}") int batchSize) {
        return new StAX2Processors(batchSize);
    }
}
