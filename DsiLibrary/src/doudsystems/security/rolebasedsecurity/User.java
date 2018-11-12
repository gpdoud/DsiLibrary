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
public class User {

    private static SqlStatement sqlStatement;
    private static JavaDB jdb = null;

//    private String id = null;
//    private Boolean idDirty = false;
//    public String getId() { return this.id; }
//    public void setId(String id) {
//        this.id = id;
//        this.idDirty = true;
//    }
//    public Boolean isIdDirty() { return this.idDirty = true; }
//    public void clearIdDirty() { this.idDirty = false; }

    public PrimaryKey pk = new PrimaryKey();

    private String firstName = null;
    private Boolean firstNameDirty = false;
    public String getFirstName() { return this.firstName; }
    public void setFirstName(String firstName) { 
        this.firstName = firstName;
        this.firstNameDirty = true;
    }
    public Boolean isFirstNameDirty() { return this.firstNameDirty = true; }
    public void clearFirstNameDirty() { this.firstNameDirty = false; }

    private String lastName = null;
    private Boolean lastNameDirty = false;
    public String getLastName() { return this.lastName; }
    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.lastNameDirty = true;
    }
    public Boolean isLastNameDirty() { return this.lastNameDirty = true; }
    public void clearLastNameDirty() { this.lastNameDirty = false; }

    private String login = null;
    private Boolean loginDirty = false;
    public String getLogin() { return this.login; }
    public void setLogin(String login) {
        this.login = login;
        this.loginDirty = true;
    }
    public Boolean isLoginDirty() { return this.loginDirty = true; }
    public void clearLoginDirty() { this.loginDirty = false; }

    private String email = null;
    private Boolean emailDirty = false;
    public String getEmail() { return this.email; }
    public void setEmail(String email) {
        this.email = email;
        this.emailDirty = true;
    }
    public Boolean isEmailDirty() { return this.emailDirty = true; }
    public void clearEmailDirty() { this.emailDirty = false; }

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

    public static User get(String id) throws SQLException {
        User[] users = User.select("Id = '" + id + "'", null);
        if(users.length == 0) {
            return null;
        }
        return users[0];
    }

    public static User[] select(String whereClause, String orderClause) {
        String sql = null;
        User[] users = null;
        ArrayList<User> list = null;
        try {
            Statement stmt = jdb.getConnection().createStatement();
            sql = User.sqlStatement.toSelectStatement(whereClause, orderClause);
            ResultSet rs = stmt.executeQuery(sql);
            list = new ArrayList<User>();
            while(rs.next()) {
                User user = User.createInstance(rs);
                list.add(user);
            }
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        users = Arrays.copyOf(list.toArray(new User[0]), list.size());
        return users;
    }

    public static User update(User user) throws SQLException {
        String sql = null;
        if(user == null) {
            throw new SQLException("ERROR: Users class can only update instances of User.");
        }
        try {
            Statement stmt = jdb.getConnection().createStatement();
            String[] dirtyColumns = user.getDirtyFields();
            String[] dirtyValues = user.getDirtyValues();
            //sql = User.sqlStatement.toUpdateStatement(dirtyColumns, dirtyValues, "Id = '" + user.getId() + "'");
            sql = User.sqlStatement.toUpdateStatement(dirtyColumns, dirtyValues, "Id = '" + user.pk.getValue() + "'");
            stmt.execute(sql);
            user.clearDirty(); // clear the isDirty flag for all columns
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return user;
    }

    public static void delete(User user) throws SQLException {
        String sql = null;
        if(user == null) {
            throw new SQLException("ERROR: Users class can only delete instances of User.");
        }
        try {
            Statement stmt = jdb.getConnection().createStatement();
            //String fmt = User.sqlStatement.toDeleteStatement("Id = '" + user.getId() + "'");
            String fmt = User.sqlStatement.toDeleteStatement("Id = '" + user.pk.getValue() + "'");
            //sql = String.format(fmt, user.getId());
            sql = String.format(fmt, user.pk.getValue());
            stmt.execute(sql);
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public static User add(User user) throws SQLException {
        String sql = null;
        try {
            Statement stmt = jdb.getConnection().createStatement();
            String fmt = User.sqlStatement.toInsertStatement();
            // TODO Allow Deleted date to be null
            String quote = "'";
            sql = String.format(fmt,
                    Conversion.getStringForDb(user.pk.getValue()),
                    Conversion.getStringForDb(user.getFirstName()),
                    Conversion.getStringForDb(user.getLastName()),
                    Conversion.getStringForDb(user.getLogin()),
                    Conversion.getStringForDb(user.getEmail()),
                    Conversion.getStringForDb(user.getDescription()),
                    Conversion.parse(user.getActive()),
                    Conversion.getDateForDb(user.getCreated()),
                    Conversion.getDateForDb(user.getUpdated()),
                    Conversion.getDateForDb(user.getDeleted()));
            stmt.execute(sql);
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return user;
    }

    public User update() throws SQLException {
        User.update(this);
        this.clearDirty();
        return this;
    }
    public void delete() throws SQLException {
        User.delete(this);
    }
    public User add() throws SQLException {
        User.add(this);
        this.clearDirty();
        return this;
    }

    public void clearDirty() {
        this.clearFirstNameDirty();
        this.clearLastNameDirty();
        this.clearLoginDirty();
        this.clearEmailDirty();
        this.clearDescriptionDirty();
        this.clearActiveDirty();
        this.clearCreatedDirty();
        this.clearUpdatedDirty();
        this.clearDeletedDirty();
    }

    public String[] getDirtyFields() {
        ArrayList<String> dirtyColumns = new ArrayList<String>();
        if(this.isFirstNameDirty()) dirtyColumns.add("FirstName");
        if(this.isLastNameDirty()) dirtyColumns.add("LastName");
        if(this.isLoginDirty()) dirtyColumns.add("Login");
        if(this.isEmailDirty()) dirtyColumns.add("Email");
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
        if(this.isFirstNameDirty()) dirtyValues.add(this.getFirstName());
        if(this.isLastNameDirty()) dirtyValues.add(this.getLastName());
        if(this.isLoginDirty()) dirtyValues.add(this.getLogin());
        if(this.isEmailDirty()) dirtyValues.add(this.getEmail());
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

    public static User createInstance(ResultSet rs) {
        try {
            User user = new User();
            //user.setId(rs.getString("Id"));
            user.pk.setValue(rs.getString("Id"));
            user.setFirstName(rs.getString("FirstName"));
            user.setLastName(rs.getString("LastName"));
            user.setLogin(rs.getString("Login"));
            user.setEmail(rs.getString("Email"));
            user.setDescription(rs.getString("Description"));
            user.setActive(rs.getBoolean("Active"));
            user.setCreated(Conversion.parse(rs.getDate("Created")));
            user.setUpdated(Conversion.parse(rs.getDate("Updated")));
            user.setDeleted(Conversion.parse(rs.getDate("Deleted")));
            user.clearDirty();
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    public User(String firstName, String lastName, String login,
            String email, String description) {
        this.pk.generatePrimaryKey();
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setLogin(login);
        this.setEmail(email);
        this.setDescription(description);
        this.setCreated();
        this.setUpdated();
    }
    public User() {
        this.pk.generatePrimaryKey();
        this.setCreated();
        this.setUpdated();
    }

    static  {
        jdb = new JavaDB("//localhost:1527/Test", "gpdoud", "radioham");
        jdb.setDatabase("RBS");
        sqlStatement = new SqlStatement("USER");
        // add the columns provided by Entity
        try {
            sqlStatement.add("Id");
            sqlStatement.add("FirstName");
            sqlStatement.add("LastName");
            sqlStatement.add("Login");
            sqlStatement.add("Email");
            sqlStatement.add("Description");
            sqlStatement.add("Active", true);
            sqlStatement.add("Created");
            sqlStatement.add("Updated");
            sqlStatement.add("Deleted");
        } catch (DuplicateValueException e) {}
    }
}
