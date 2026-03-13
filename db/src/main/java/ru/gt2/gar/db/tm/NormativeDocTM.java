// АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
// Вам следует изменить класс гененратора (TableMappingGenerator) вместо ручного редактирования!
package ru.gt2.gar.db.tm;

import ru.gt2.gar.domain.NormativeDoc;

import javax.annotation.processing.Generated;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Generated(
        value = "ru.gt2.gar.db.tm.TableMappingGenerator",
        date = "2026-03-13T18:35:21.0999392+03:00"
)
public class NormativeDocTM extends AbstractTableMapping<NormativeDoc, Long> {
    public NormativeDocTM() {
        super(NormativeDoc::id);
    }

    @Override
    public void write(NormativeDoc source, PreparedStatement ps) throws SQLException {
        ps.setLong(1, source.id());
        ps.setString(2, source.name());
        ps.setObject(3, source.date());
        ps.setString(4, source.number());
        ps.setInt(5, source.type());
        ps.setInt(6, source.kind());
        ps.setObject(7, source.updateDate());
        ps.setString(8, source.orgName());
        ps.setString(9, source.regNum());
        ps.setObject(10, source.regDate());
        ps.setObject(11, source.accDate());
        ps.setString(12, source.comment());
    }

    @Override
    public NormativeDoc read(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        String name = rs.getString(2);
        LocalDate date = rs.getObject(3, LocalDate.class);
        String number = rs.getString(4);
        int type = rs.getInt(5);
        int kind = rs.getInt(6);
        LocalDate updateDate = rs.getObject(7, LocalDate.class);
        String orgName = rs.getString(8);
        String regNum = rs.getString(9);
        LocalDate regDate = rs.getObject(10, LocalDate.class);
        LocalDate accDate = rs.getObject(11, LocalDate.class);
        String comment = rs.getString(12);
        return new NormativeDoc(id, name, date, number, type, kind, updateDate, orgName, regNum, regDate, accDate, comment);
    }
}
