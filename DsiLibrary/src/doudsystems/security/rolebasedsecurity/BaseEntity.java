/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.security.rolebasedsecurity;

import doudsystems.utility.conversion.Conversion;
import doudsystems.utility.sql.JavaDB;
import doudsystems.utility.sql.SqlStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Owner
 */
public class BaseEntity {

    protected static SqlStatement sqlStatement;
    protected static JavaDB jdb = null;

    public PrimaryKey pk = new PrimaryKey();
    public BooleanProperty Active = new BooleanProperty();
    public CalendarProperty Created = new CalendarProperty();
    public CalendarProperty Updated = new CalendarProperty();
    public CalendarProperty Deleted = new CalendarProperty();

    private Boolean disposed;
    private Boolean getDisposed() { return this.disposed; }
    private void setDisposed(Boolean disposed) { this.disposed = disposed; }
    public void dispose() { this.setDisposed(true); }
    public Boolean isDisposed() { return this.getDisposed(); }

    protected static void createInstance(BaseEntity eb, ResultSet rs) throws SQLException {
        try {
            eb.pk.setValue(rs.getString("Id"));
            eb.Active.setValue(rs.getBoolean("Active"));
            eb.Created.setValue(rs.getDate("Created"));
            eb.Updated.setValue(rs.getDate("Updated"));
            eb.Deleted.setValue(rs.getDate("Deleted"));
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void clearDirty() {
        this.pk.clearDirty();
        this.Active.clearDirty();
        this.Created.clearDirty();
        this.Updated.clearDirty();
        this.Deleted.clearDirty();
    }
    public String[] getDirtyFields() {
        ArrayList<String> dirtyColumns = new ArrayList<String>();
        if(this.pk.isDirty()) dirtyColumns.add("Id");
        if(this.Active.isDirty()) dirtyColumns.add("Active");
        if(this.Created.isDirty()) dirtyColumns.add("Created");
        if(this.Updated.isDirty()) dirtyColumns.add("Updated");
        if(this.Deleted.isDirty()) dirtyColumns.add("Deleted");
        String[] results = Arrays.copyOf(dirtyColumns.toArray(new String[0]), dirtyColumns.size());
        return results;
    }
    public String[] getDirtyValues() {
        ArrayList<String> dirtyValues = new ArrayList<String>();
        if(this.pk.isDirty()) dirtyValues.add(this.pk.getSqlValue());
        if(this.Active.isDirty()) dirtyValues.add(this.Active.getSqlValue());
        if(this.Created.isDirty()) dirtyValues.add(this.Created.getSqlValue());
        if(this.Updated.isDirty()) dirtyValues.add(this.Updated.getSqlValue());
        if(this.Deleted.isDirty()) dirtyValues.add(this.Deleted.getSqlValue());
        String[] results = Arrays.copyOf(dirtyValues.toArray(new String[0]), dirtyValues.size());
        return results;
    }
}
