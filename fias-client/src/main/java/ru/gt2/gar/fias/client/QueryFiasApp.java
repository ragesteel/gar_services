package ru.gt2.gar.fias.client;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gt2.gar.fias.client.rest.FileInfo;
import ru.gt2.gar.fias.client.rest.FileInfoService;

import java.text.MessageFormat;

@SpringBootApplication
@RequiredArgsConstructor
public class QueryFiasApp implements CommandLineRunner {
    private final FileInfoService fileInfoService;

    static void main(String... args) {
        SpringApplication.run(QueryFiasApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        fileInfoService.getAll().forEach(
                fi -> System.out.println(
                        MessageFormat.format("XML: {0}, Delta: {1}",
                        fi.GarXMLFullURL(), fi.GarXMLDeltaURL())
        ));
        System.out.println();

        FileInfo last = fileInfoService.getLast();
        System.out.println(MessageFormat.format("Latest: {0}", last.TextVersion()));
    }
}
