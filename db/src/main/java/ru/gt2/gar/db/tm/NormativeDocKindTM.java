// АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
// Вам следует изменить класс гененратора (TableMappingGenerator) вместо ручного редактирования!
package ru.gt2.gar.db.tm;

import ru.gt2.gar.domain.NormativeDocKind;

import javax.annotation.processing.Generated;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Generated(
        value = "ru.gt2.gar.db.tm.TableMappingGenerator",
        date = "2026-03-13T18:35:21.1039408+03:00"
)
public class NormativeDocKindTM extends AbstractTableMapping<NormativeDocKind, Integer> {
    public NormativeDocKindTM() {
        super(NormativeDocKind::id);
    }

    @Override
    public void write(NormativeDocKind source, PreparedStatement ps) throws SQLException {
        ps.setInt(1, source.id());
        ps.setString(2, source.name());
    }

    @Override
    public NormativeDocKind read(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String name = rs.getString(2);
        return new NormativeDocKind(id, name);
    }
}
