/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.security.rolebasedsecurity;

import doudsystems.utility.sql.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 *
 * @author Owner
 */
public class zEntity {

    private static Boolean isStaticInit = StaticConstructor();
    protected static SqlStatement entitySqlStatement;

    private String id = null;
    private Boolean idDirty = false;
    public String getId() { return this.id; }
    protected zEntity setId(String id) {
        this.id = id;
        this.idDirty = true;
        return this;
    }

    private Boolean active = false;
    private Boolean activeDirty = false;
    public Boolean getActive() { return this.active; }
    public zEntity setActive(Boolean active) {
        this.active = active;
        this.activeDirty = true;
        this.setUpdated();
        return this;
    }

    private Calendar created = null;
    private Boolean createdDirty = false;
    public Calendar getCreated() { return this.created; }
    protected zEntity setCreated(Calendar created) {
        this.created = created;
        this.createdDirty = true;
        return this;
    }
    protected zEntity setCreated(Date created) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(created);
        return setCreated(cal);
    }
    protected zEntity setCreated() {
        Calendar cal = Calendar.getInstance();
        return setCreated(cal);
    }

    private Calendar updated = null;
    private Boolean updatedDirty = false;
    public Calendar getUpdated() { return this.updated; }
    protected zEntity setUpdated(Calendar updated) {
        this.updated = updated;
        this.updatedDirty = true;
        return this;
    }
    protected zEntity setUpdated(Date updated) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(updated);
        return setCreated(cal);
    }
    protected zEntity setUpdated() {
        Calendar cal = Calendar.getInstance();
        return setUpdated(cal);
    }

    private Calendar deleted = null;
    private Boolean deletedDirty = false;
    public Calendar getDeleted() { return this.deleted; }
    protected zEntity setDeleted(Calendar deleted) {
        this.deleted = deleted;
        this.deletedDirty = true;
        return this;
    }
    protected zEntity setDeleted(Date deleted) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(deleted);
        return setCreated(cal);
    }
    protected zEntity setDeleted() {
        Calendar cal = Calendar.getInstance();
        return setUpdated(cal);
    }

    private Boolean disposed;
    private Boolean getDisposed() { return this.disposed; }
    private void setDisposed(Boolean disposed) { this.disposed = disposed; }
    public void dispose() { this.setDisposed(true); }
    public Boolean isDisposed() { return this.getDisposed(); }
    
    protected zEntity add() throws SQLException { return null; }
    protected zEntity update() throws SQLException { return null; }
    protected void delete() throws SQLException {}

    public zEntity clearDirty() {
        this.idDirty = false;
        this.activeDirty = false;
        this.createdDirty = false;
        this.updatedDirty = false;
        this.deletedDirty = false;
        return this;
    }

    public String[] getDirtyFields() {
        ArrayList<String> dirtyColumns = new ArrayList<String>();
        if(this.activeDirty) dirtyColumns.add("Active");
        if(this.createdDirty) dirtyColumns.add("Created");
        if(this.updatedDirty) dirtyColumns.add("Updated");
        if(this.deletedDirty) dirtyColumns.add("Deleted");
        String[] results = Arrays.copyOf(dirtyColumns.toArray(new String[0]), dirtyColumns.size());
        return results;
    }

    public static String calendarToString(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.s");
        return sdf.format(cal.getTime());
    }

    public String[] getDirtyValues() {
        ArrayList<String> dirtyValues = new ArrayList<String>();
        if(this.activeDirty) dirtyValues.add(this.getActive().toString());
        if(this.createdDirty) dirtyValues.add(calendarToString(this.getCreated()));
        if(this.updatedDirty) dirtyValues.add(calendarToString(this.getUpdated()));
        if(this.deletedDirty) dirtyValues.add(calendarToString(this.getDeleted()));
        String[] results = Arrays.copyOf(dirtyValues.toArray(new String[0]), dirtyValues.size());
        return results;
    }

    public zEntity() {
        // set the Id to a random, unique key
        this.id = UUID.randomUUID().toString();
        // set the active property to true
        this.setActive(true);
        // set the created and updated property to today
        Calendar cal = GregorianCalendar.getInstance();
        this.setCreated(cal);
        this.setUpdated(cal);
        // set the disposed property to false
        // when disposed, cannot be set back to true
        this.setDisposed(false);
    }
    
    private static Boolean StaticConstructor() {
        if(zEntity.entitySqlStatement == null)
            zEntity.entitySqlStatement = new SqlStatement(null);
        // add the columns provided by Entity
        try {
            zEntity.entitySqlStatement.add("Id");
            zEntity.entitySqlStatement.add("Active", Boolean.FALSE);
            zEntity.entitySqlStatement.add("Created");
            zEntity.entitySqlStatement.add("Updated");
            zEntity.entitySqlStatement.add("Deleted");
        } catch(DuplicateValueException e) { return false; }
        return true;
    }
}
