package ru.gt2.gar.db;

import org.postgresql.PGConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gt2.gar.parse.xml.AllXMLProcessors;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/// Загрузка данных в базу
@SpringBootApplication
public class LoadDataApp implements CommandLineRunner {
    private final AllXMLProcessors xmlProcessors;
    private final DataSource dataSource;
    private final File zipFile;

    public LoadDataApp(AllXMLProcessors xmlProcessors, DataSource dataSource,
                       @Value("${gar.zip.full}") File zipFile) {

        this.xmlProcessors = xmlProcessors;
        this.dataSource = dataSource;
        this.zipFile = zipFile;
    }

    public static void main(String... args) {
        SpringApplication.run(LoadDataApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO Сначала сделать загрузку для одной таблицы, из корневых справочников
        // TODO Потом — всех корневых справочников
        // TODO И наконец — всех данных по регионам
        // TODO Сохранение данных вынести в отдельный класс с интерфейсом
        //  и реализациями на BatchPreparedStatement, UNNEST и COPY
        Connection connection = dataSource.getConnection();
        ;
        ResultSet typeInfo = connection.getMetaData().getTypeInfo();
        ResultSetMetaData typeInfoMetaData = typeInfo.getMetaData();
        for (int i = 1; i <= typeInfoMetaData.getColumnCount(); i++) {
            System.out.print(typeInfoMetaData.getColumnName(i) + " ");
        }
        System.out.println();

        while (typeInfo.next()) {
            for (int i = 1; i <= typeInfoMetaData.getColumnCount(); i++) {
                System.out.print(typeInfo.getString(i) + " ");
            }
            System.out.println();
        }

        // ?reWriteBatchedInserts=true
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO table_name (column_name1, column_name2) VALUES (?, ?)");
        // batch
        preparedStatement.setInt(1, 123);
        preparedStatement.setString(2, "test");
        preparedStatement.addBatch();
        // batch
        preparedStatement.execute();

        PGConnection pgConnection = connection.unwrap(PGConnection.class);
        // pgConnection.createArrayOf()
    }
}
