package ru.gt2.gar.db.jrm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface JdbcMapper<T> {
    /// Вызов ps.set…(#, source.(get)) для установки значений
    void write(T source, PreparedStatement ps) throws SQLException;

    /// Чтение из rs.get..(#) и последующее создание объекта
    T read(ResultSet rs) throws SQLException;

    /// Вернуть количество ожидаемых колонок, можно использовать для проверок соответствия
    int columnCount();

    /// Вернуть данные о первичном ключе
    PrimaryKeyMeta<T> primaryKey();
}
