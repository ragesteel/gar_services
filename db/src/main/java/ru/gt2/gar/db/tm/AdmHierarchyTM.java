// АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
// Вам следует изменить класс гененратора (TableMappingGenerator) вместо ручного редактирования!
package ru.gt2.gar.db.tm;

import ru.gt2.gar.domain.AdmHierarchy;

import javax.annotation.processing.Generated;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Generated(
        value = "ru.gt2.gar.db.tm.TableMappingGenerator",
        date = "2026-03-13T18:35:21.0569434+03:00"
)
public class AdmHierarchyTM extends AbstractTableMapping<AdmHierarchy, Long> {
    public AdmHierarchyTM() {
        super(AdmHierarchy::id);
    }

    @Override
    public void write(AdmHierarchy source, PreparedStatement ps) throws SQLException {
        ps.setLong(1, source.id());
        ps.setLong(2, source.objectId());
        ps.setObject(3, source.parentObjId());
        ps.setLong(4, source.changeId());
        ps.setString(5, source.regionCode());
        ps.setString(6, source.areaCode());
        ps.setString(7, source.cityCode());
        ps.setString(8, source.placeCode());
        ps.setString(9, source.planCode());
        ps.setString(10, source.streetCode());
        ps.setObject(11, source.prevId());
        ps.setObject(12, source.nextId());
        ps.setObject(13, source.updateDate());
        ps.setObject(14, source.startDate());
        ps.setObject(15, source.endDate());
        ps.setBoolean(16, source.isActive());
        ps.setString(17, source.path());
    }

    @Override
    public AdmHierarchy read(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        long objectId = rs.getLong(2);
        Long parentObjId = rs.getObject(3, Long.class);
        long changeId = rs.getLong(4);
        String regionCode = rs.getString(5);
        String areaCode = rs.getString(6);
        String cityCode = rs.getString(7);
        String placeCode = rs.getString(8);
        String planCode = rs.getString(9);
        String streetCode = rs.getString(10);
        Long prevId = rs.getObject(11, Long.class);
        Long nextId = rs.getObject(12, Long.class);
        LocalDate updateDate = rs.getObject(13, LocalDate.class);
        LocalDate startDate = rs.getObject(14, LocalDate.class);
        LocalDate endDate = rs.getObject(15, LocalDate.class);
        boolean isActive = rs.getBoolean(16);
        String path = rs.getString(17);
        return new AdmHierarchy(id, objectId, parentObjId, changeId, regionCode, areaCode, cityCode, placeCode, planCode, streetCode, prevId, nextId, updateDate, startDate, endDate, isActive, path);
    }
}
