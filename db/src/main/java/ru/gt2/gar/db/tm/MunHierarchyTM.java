// АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
// Вам следует изменить класс гененратора (TableMappingGenerator) вместо ручного редактирования!
package ru.gt2.gar.db.tm;

import ru.gt2.gar.domain.MunHierarchy;

import javax.annotation.processing.Generated;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Generated(
        value = "ru.gt2.gar.db.tm.TableMappingGenerator",
        date = "2026-03-13T18:35:21.0969392+03:00"
)
public class MunHierarchyTM extends AbstractTableMapping<MunHierarchy, Long> {
    public MunHierarchyTM() {
        super(MunHierarchy::id);
    }

    @Override
    public void write(MunHierarchy source, PreparedStatement ps) throws SQLException {
        ps.setLong(1, source.id());
        ps.setLong(2, source.objectId());
        ps.setObject(3, source.parentObjId());
        ps.setLong(4, source.changeId());
        ps.setString(5, source.oktmo());
        ps.setObject(6, source.prevId());
        ps.setObject(7, source.nextId());
        ps.setObject(8, source.updateDate());
        ps.setObject(9, source.startDate());
        ps.setObject(10, source.endDate());
        ps.setBoolean(11, source.isActive());
        ps.setString(12, source.path());
    }

    @Override
    public MunHierarchy read(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        long objectId = rs.getLong(2);
        Long parentObjId = rs.getObject(3, Long.class);
        long changeId = rs.getLong(4);
        String oktmo = rs.getString(5);
        Long prevId = rs.getObject(6, Long.class);
        Long nextId = rs.getObject(7, Long.class);
        LocalDate updateDate = rs.getObject(8, LocalDate.class);
        LocalDate startDate = rs.getObject(9, LocalDate.class);
        LocalDate endDate = rs.getObject(10, LocalDate.class);
        boolean isActive = rs.getBoolean(11);
        String path = rs.getString(12);
        return new MunHierarchy(id, objectId, parentObjId, changeId, oktmo, prevId, nextId, updateDate, startDate, endDate, isActive, path);
    }
}
