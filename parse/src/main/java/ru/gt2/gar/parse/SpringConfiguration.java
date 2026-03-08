package ru.gt2.gar.parse;

import org.codehaus.stax2.XMLInputFactory2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gt2.gar.parse.xml.XMLProcessors;
import ru.gt2.gar.parse.xml.stax2.StAX2Processors;

@Configuration
public class SpringConfiguration {
    @Bean
    public XMLProcessors xmlProcessors(@Value("${gar.parse.batch:1024}") int batchSize, @Value("${gar.xml.parse.xmlInputFactory2}") String xmlInputFactoryClass) throws ClassNotFoundException {
        Class<XMLInputFactory2> xmlInputFactory2Class = (Class<XMLInputFactory2>) Class.forName(xmlInputFactoryClass);
        return new StAX2Processors(batchSize, BeanUtils.instantiateClass(xmlInputFactory2Class));
    }
}
