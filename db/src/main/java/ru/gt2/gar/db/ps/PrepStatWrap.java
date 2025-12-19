package ru.gt2.gar.db.ps;

import jakarta.annotation.Nullable;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/// Обёртка вокруг PreparedStatement для удобства работы в MapStruct.
public class PrepStatWrap {
    private final PreparedStatement ps;
    private int index = 1;

    public PrepStatWrap(PreparedStatement ps) {
        this.ps = ps;
    }

    public void resetIndex() {
        index = 1;
    }

    public void setInt(int value) throws SQLException {
        ps.setInt(index++, value);
    }

    public void setString(@Nullable String value) throws SQLException {
        if (value == null) {
            ps.setNull(index++, java.sql.Types.VARCHAR);
        } else {
            ps.setString(index++, value);
        }
    }

    public void setLong(long value) throws SQLException {
        ps.setLong(index++, value);
    }
}
