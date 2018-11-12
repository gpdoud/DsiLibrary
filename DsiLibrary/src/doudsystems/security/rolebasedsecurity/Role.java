/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.security.rolebasedsecurity;

import doudsystems.utility.conversion.Conversion;
import doudsystems.utility.sql.DuplicateValueException;
import doudsystems.utility.sql.JavaDB;
import doudsystems.utility.sql.SqlStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Owner
 */
public class Role {

    private static SqlStatement sqlStatement;
    private static JavaDB jdb = null;

    public PrimaryKey pk = new PrimaryKey();

    private String name = null;
    private Boolean nameDirty = false;
    public String getName() { return this.name; }
    public void setName(String name) {
        this.name = name;
        this.nameDirty = true;
    }
    public Boolean isNameDirty() { return this.nameDirty = true; }
    public void clearNameDirty() { this.nameDirty = false; }

    private String description = null;
    private Boolean descriptionDirty = false;
    public String getDescription() { return this.description; }
    public void setDescription(String description) {
        this.description = description;
        this.descriptionDirty = true;
    }
    public Boolean isDescriptionDirty() { return this.descriptionDirty = true; }
    public void clearDescriptionDirty() { this.descriptionDirty = false; }

    private Boolean active = true;
    private Boolean activeDirty = false;
    public Boolean getActive() { return this.active; }
    public void setActive(Boolean active) {
        this.active = active;
        this.activeDirty = true;
    }
    public Boolean isActiveDirty() { return this.activeDirty = true; }
    public void clearActiveDirty() { this.activeDirty = false; }

    private Calendar created = null;
    private Boolean createdDirty = false;
    public Calendar getCreated() { return this.created; }
    public void setCreated(Calendar created) {
        this.created = created;
        this.createdDirty = true;
    }
    public Boolean isCreatedDirty() { return this.createdDirty = true; }
    public void clearCreatedDirty() { this.createdDirty = false; }

    private Calendar updated = null;
    private Boolean updatedDirty = false;
    public Calendar getUpdated() { return this.updated; }
    public void setUpdated(Calendar updated) {
        this.updated = updated;
        this.updatedDirty = true;
    }
    public Boolean isUpdatedDirty() { return this.updatedDirty = true; }
    public void clearUpdatedDirty() { this.updatedDirty = false; }

    private Calendar deleted = null;
    private Boolean deletedDirty = false;
    public Calendar getDeleted() { return this.deleted; }
    public void setDeleted(Calendar deleted) {
        this.deleted = deleted;
        this.deletedDirty = true;
    }
    public Boolean isDeletedDirty() { return this.deletedDirty = true; }
    public void clearDeletedDirty() { this.deletedDirty = false; }

    public Boolean isValidPassword(String password) {
        return true;
    }

    protected void setCreated(Date created) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(created);
        this.setCreated(cal);
    }
    protected void setCreated() {
        Calendar cal = Calendar.getInstance();
        this.setCreated(cal);
    }

    protected void setUpdated(Date updated) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(updated);
        this.setUpdated(cal);
    }
    public void setUpdated() {
        Calendar cal = Calendar.getInstance();
        this.setUpdated(cal);
    }

    protected void setDeleted(Date deleted) {
        Calendar cal = Calendar.getInstance();
        if(deleted == null) {
            cal.setTimeInMillis(0);
        } else {
            cal.setTime(deleted);
        }
        this.setDeleted(cal);
    }
    protected void setDeleted() {
        Calendar cal = Calendar.getInstance();
        this.setDeleted(cal);
    }

    private Boolean disposed;
    private Boolean getDisposed() { return this.disposed; }
    private void setDisposed(Boolean disposed) { this.disposed = disposed; }
    public void dispose() { this.setDisposed(true); }
    public Boolean isDisposed() { return this.getDisposed(); }

    public static Role get(String id) throws SQLException {
        Role[] roles = Role.select("Id = '" + id + "'", null);
        if(roles.length == 0) {
            return null;
        }
        return roles[0];
    }

    public static Role[] select(String whereClause, String orderClause) {
        String sql = null;
        Role[] roles = null;
        ArrayList<Role> list = null;
        try {
            Statement stmt = jdb.getConnection().createStatement();
            sql = Role.sqlStatement.toSelectStatement(whereClause, orderClause);
            ResultSet rs = stmt.executeQuery(sql);
            list = new ArrayList<Role>();
            while(rs.next()) {
                Role role = Role.createInstance(rs);
                list.add(role);
            }
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        roles = Arrays.copyOf(list.toArray(new Role[0]), list.size());
        return roles;
    }

    public static Role update(Role role) throws SQLException {
        String sql = null;
        if(role == null) {
            throw new SQLException("ERROR: Role class can only update instances of Role.");
        }
        try {
            Statement stmt = jdb.getConnection().createStatement();
            String[] dirtyColumns = role.getDirtyFields();
            String[] dirtyValues = role.getDirtyValues();
            //sql = User.sqlStatement.toUpdateStatement(dirtyColumns, dirtyValues, "Id = '" + user.getId() + "'");
            sql = Role.sqlStatement.toUpdateStatement(dirtyColumns, dirtyValues, "Id = '" + role.pk.getValue() + "'");
            stmt.execute(sql);
            role.clearDirty(); // clear the isDirty flag for all columns
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return role;
    }

    public static void delete(Role role) throws SQLException {
        String sql = null;
        if(role == null) {
            throw new SQLException("ERROR: Role class can only delete instances of Role.");
        }
        try {
            Statement stmt = jdb.getConnection().createStatement();
            //String fmt = User.sqlStatement.toDeleteStatement("Id = '" + user.getId() + "'");
            String fmt = Role.sqlStatement.toDeleteStatement("Id = '" + role.pk.getValue() + "'");
            //sql = String.format(fmt, user.getId());
            sql = String.format(fmt, role.pk.getValue());
            stmt.execute(sql);
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public static Role add(Role role) throws SQLException {
        String sql = null;
        try {
            Statement stmt = jdb.getConnection().createStatement();
            String fmt = Role.sqlStatement.toInsertStatement();
            // TODO Allow Deleted date to be null
            String quote = "'";
            sql = String.format(fmt,
                    Conversion.getStringForDb(role.pk.getValue()),
                    Conversion.getStringForDb(role.getName()),
                    Conversion.getStringForDb(role.getDescription()),
                    Conversion.parse(role.getActive()),
                    Conversion.getDateForDb(role.getCreated()),
                    Conversion.getDateForDb(role.getUpdated()),
                    Conversion.getDateForDb(role.getDeleted()));
            stmt.execute(sql);
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return role;
    }

    public Role update() throws SQLException {
        Role.update(this);
        this.clearDirty();
        return this;
    }
    public void delete() throws SQLException {
        Role.delete(this);
    }
    public Role add() throws SQLException {
        Role.add(this);
        this.clearDirty();
        return this;
    }

    public void clearDirty() {
        this.clearNameDirty();
        this.clearDescriptionDirty();
        this.clearActiveDirty();
        this.clearCreatedDirty();
        this.clearUpdatedDirty();
        this.clearDeletedDirty();
    }

    public String[] getDirtyFields() {
        ArrayList<String> dirtyColumns = new ArrayList<String>();
        if(this.isNameDirty()) dirtyColumns.add("Name");
        if(this.isDescriptionDirty()) dirtyColumns.add("Description");
        if(this.isActiveDirty()) dirtyColumns.add("Active");
        if(this.isCreatedDirty()) dirtyColumns.add("Created");
        if(this.isUpdatedDirty()) dirtyColumns.add("Updated");
        if(this.isDeletedDirty()) dirtyColumns.add("Deleted");
        String[] thisResults = Arrays.copyOf(dirtyColumns.toArray(new String[0]), dirtyColumns.size());
        String[] results = new String[thisResults.length];
        System.arraycopy(thisResults, 0, results, 0, thisResults.length);
        return results;
    }

    public String[] getDirtyValues() {
        ArrayList<String> dirtyValues = new ArrayList<String>();
        if(this.isNameDirty()) dirtyValues.add(this.getName());
        if(this.isDescriptionDirty()) dirtyValues.add(this.getDescription());
        if(this.isActiveDirty()) dirtyValues.add(this.getActive().toString());
        if(this.isCreatedDirty()) dirtyValues.add(Conversion.getDateForDb(this.getCreated()));
        if(this.isUpdatedDirty()) dirtyValues.add(Conversion.getDateForDb(this.getUpdated()));
        if(this.isDeletedDirty()) dirtyValues.add(Conversion.getDateForDb(this.getDeleted()));
        String[] thisResults = Arrays.copyOf(dirtyValues.toArray(new String[0]), dirtyValues.size());
        String[] results = new String[thisResults.length];
        System.arraycopy(thisResults, 0, results, 0, thisResults.length);
        return results;
    }

    public static Role createInstance(ResultSet rs) {
        try {
            Role role = new Role();
            //user.setId(rs.getString("Id"));
            role.pk.setValue(rs.getString("Id"));
            role.setName(rs.getString("Name"));
            role.setDescription(rs.getString("Description"));
            role.setActive(rs.getBoolean("Active"));
            role.setCreated(Conversion.parse(rs.getDate("Created")));
            role.setUpdated(Conversion.parse(rs.getDate("Updated")));
            role.setDeleted(Conversion.parse(rs.getDate("Deleted")));
            role.clearDirty();
            return role;
        } catch (SQLException e) {
            return null;
        }
    }

    public Role(String name, String description) {
        this.pk.generatePrimaryKey();
        this.setName(name);
        this.setDescription(description);
        this.setCreated();
        this.setUpdated();
    }
    public Role() {
        this.pk.generatePrimaryKey();
        this.setCreated();
        this.setUpdated();
    }

    static  {
        jdb = new JavaDB("//localhost:1527/Test", "gpdoud", "radioham");
        jdb.setDatabase("RBS");
        sqlStatement = new SqlStatement("ROLE");
        // add the columns provided by Entity
        try {
            sqlStatement.add("Id");
            sqlStatement.add("Name");
            sqlStatement.add("Description");
            sqlStatement.add("Active", true);
            sqlStatement.add("Created");
            sqlStatement.add("Updated");
            sqlStatement.add("Deleted");
        } catch (DuplicateValueException e) {}
    }
}