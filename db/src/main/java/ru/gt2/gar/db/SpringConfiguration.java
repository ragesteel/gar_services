package ru.gt2.gar.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gt2.gar.db.schema.NamingStrategy;
import ru.gt2.gar.parse.xml.XMLProcessors;
import ru.gt2.gar.parse.xml.stax.event.StAXProcessors;

@Configuration
public class SpringConfiguration {
    @Bean
    public XMLProcessors xmlProcessors(@Value("${gar.parse.batch:1024}") int batchSize) {
        return new StAXProcessors(batchSize);
    }

    @Bean
    public NamingStrategy namingStrategy() {
        return NamingStrategy.LOWER_UNDERSCORE;
    }
}
