package ru.gt2.gar.db.tm;

import ru.gt2.gar.domain.GarRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public interface TableMapping<T extends GarRecord, K extends Number> {
    @Deprecated
    String getIdColumnType();

    @Deprecated
    String getSelectSQL();

    @Deprecated
    String getInsertSQL();

    /// Вызов ps.set…(#, source.(get)) для установки значений
    void write(T source, PreparedStatement ps) throws SQLException;

    /// Чтение из rs.get..(#) и последующее создание объекта
    T read(ResultSet rs) throws SQLException;

    /// Вернуть данные первичного ключа из GarRecord'а
    Function<T, K> getPrimaryKey();
}
