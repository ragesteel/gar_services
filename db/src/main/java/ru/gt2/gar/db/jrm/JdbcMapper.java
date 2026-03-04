package ru.gt2.gar.db.jrm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public interface JdbcMapper<T> {
    /// Вызов ps.set…(#, source.(get)) для установки значений
    void write(T source, PreparedStatement ps) throws SQLException;

    /// Чтение из rs.get..(#) и последующее создание объекта
    T read(ResultSet rs) throws SQLException;

    /// Вернуть количество ожидаемых колонок, можно использовать для проверок соответствия
    int columnCount();

    /// Вернуть данные первичного ключа
    Function<T, ?> primaryKey();
}
