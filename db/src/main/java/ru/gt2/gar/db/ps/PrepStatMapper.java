package ru.gt2.gar.db.ps;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.gt2.gar.domain.AddressObjectType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Mapper
public interface PrepStatMapper {
    void bind(@MappingTarget PreparedStatement prepStat, AddressObjectType addressObjectType);

    // ↓↓↓ Кастомные мапперы для типов ↓↓↓
    default void map(long value, @MappingTarget PrepStatWrap prepStat) throws SQLException {
        prepStat.setLong(value);
    }

    default void map(int value, @MappingTarget PrepStatWrap prepStat) throws SQLException {
        prepStat.setInt(value);
    }

    default void map(String value, @MappingTarget PrepStatWrap prepStat) throws SQLException {
        prepStat.setString(value);
    }
}
