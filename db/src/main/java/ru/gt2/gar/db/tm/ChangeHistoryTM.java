// АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
// Вам следует изменить класс гененратора (TableMappingGenerator) вместо ручного редактирования!
package ru.gt2.gar.db.tm;

import ru.gt2.gar.domain.ChangeHistory;

import javax.annotation.processing.Generated;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

@Generated(
        value = "ru.gt2.gar.db.tm.TableMappingGenerator",
        date = "2026-03-13T18:35:21.0829719+03:00"
)
public class ChangeHistoryTM extends AbstractTableMapping<ChangeHistory, Long> {
    public ChangeHistoryTM() {
        super(ChangeHistory::changeId);
    }

    @Override
    public void write(ChangeHistory source, PreparedStatement ps) throws SQLException {
        ps.setLong(1, source.changeId());
        ps.setLong(2, source.objectID());
        ps.setObject(3, source.adrObjectId());
        ps.setInt(4, source.operTypeId());
        ps.setObject(5, source.nDocId());
        ps.setObject(6, source.changeDate());
    }

    @Override
    public ChangeHistory read(ResultSet rs) throws SQLException {
        long changeId = rs.getLong(1);
        long objectID = rs.getLong(2);
        UUID adrObjectId = rs.getObject(3, UUID.class);
        int operTypeId = rs.getInt(4);
        Long nDocId = rs.getObject(5, Long.class);
        LocalDate changeDate = rs.getObject(6, LocalDate.class);
        return new ChangeHistory(changeId, objectID, adrObjectId, operTypeId, nDocId, changeDate);
    }
}
