/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.security.rolebasedsecurity;

import doudsystems.utility.calendar.DateTime;
import doudsystems.utility.cryptography.Password;
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
public class User  {

    protected static SqlStatement sqlStatement;
    private static Boolean isStaticInit = StaticConstructor();
    protected static JavaDB jdb = null;

    private String firstName = null;
    private Boolean firstNameDirty = false;
    public String getFirstName() { return this.firstName; }
    public User setFirstName(String firstName) {
        this.firstName = firstName;
        this.firstNameDirty = true;
        this.setUpdated();
        return this;
    }

    private String lastName = null;
    private Boolean lastNameDirty = false;
    public String getLastName() { return this.lastName; }
    public User setLastName(String lastName) {
        this.lastName = lastName;
        this.lastNameDirty = true;
        this.setUpdated();
        return this;
    }

    private String login = null;
    private Boolean loginDirty = false;
    public String getLogin() { return this.login; }
    public User setLogin(String login) {
        this.login = login;
        this.loginDirty = true;
        this.setUpdated();
        return this;
    }

//    private String password = null;
//    private Boolean passwordDirty = false;
//    public String getPassword() { return this.password; }
//    public User setPassword(String password) {
//        this.password = Password.encryptOneWayToBase64(password);
//        this.passwordDirty = true;
//        this.setUpdated();
//        return this;
//    }
    public Boolean isValidPassword(String password) {
        Pass pass = zPasses.getPasswordByUserId(this.getId());
        if(pass == null) {
            return false;
        }
        String currentPassword = pass.getPassword();
        String enteredPassword = Password.encryptOneWayToBase64(password);
        return currentPassword.equals(enteredPassword);
    }

    private String email = null;
    private Boolean emailDirty = false;
    public String getEmail() { return this.email; }
    public User setEmail(String email) {
        this.email = email;
        this.emailDirty = true;
        this.setUpdated();
        return this;
    }

    private String description = null;
    private Boolean descriptionDirty = false;
    public String getDescription() { return this.description; }
    public User setDescription(String description) {
        this.description = description;
        this.descriptionDirty = true;
        this.setUpdated();
        return this;
    }

    private String id = null;
    private Boolean idDirty = false;
    public String getId() { return this.id; }
    protected User setId(String id) {
        this.id = id;
        this.idDirty = true;
        return this;
    }

    private Boolean active = false;
    private Boolean activeDirty = false;
    public Boolean getActive() { return this.active; }
    public User setActive(Boolean active) {
        this.active = active;
        this.activeDirty = true;
        this.setUpdated();
        return this;
    }

    private Calendar created = null;
    private Boolean createdDirty = false;
    public Calendar getCreated() { return this.created; }
    protected User setCreated(Calendar created) {
        this.created = created;
        this.createdDirty = true;
        return this;
    }
    protected User setCreated(Date created) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(created);
        return setCreated(cal);
    }
    protected User setCreated() {
        Calendar cal = Calendar.getInstance();
        return setCreated(cal);
    }

    private Calendar updated = null;
    private Boolean updatedDirty = false;
    public Calendar getUpdated() { return this.updated; }
    protected User setUpdated(Calendar updated) {
        this.updated = updated;
        this.updatedDirty = true;
        return this;
    }
    protected User setUpdated(Date updated) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(updated);
        return setCreated(cal);
    }
    protected User setUpdated() {
        Calendar cal = Calendar.getInstance();
        return setUpdated(cal);
    }

    private Calendar deleted = null;
    private Boolean deletedDirty = false;
    public Calendar getDeleted() { return this.deleted; }
    protected User setDeleted(Calendar deleted) {
        this.deleted = deleted;
        this.deletedDirty = true;
        return this;
    }
    protected User setDeleted(Date deleted) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(deleted);
        return setCreated(cal);
    }
    protected User setDeleted() {
        Calendar cal = Calendar.getInstance();
        return setUpdated(cal);
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
            Statement stmt = User.jdb.getConnection().createStatement();
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
            Statement stmt = zEntities.jdb.getConnection().createStatement();
            String[] dirtyColumns = user.getDirtyFields();
            String[] dirtyValues = user.getDirtyValues();
            sql = User.sqlStatement.toUpdateStatement(dirtyColumns, dirtyValues, "Id = '" + user.getId() + "'");
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
            Statement stmt = User.jdb.getConnection().createStatement();
            String fmt = "DELETE FROM \"USER\" WHERE Id = '%s'";
            sql = String.format(fmt,
                    user.getId());
            stmt.execute(sql);
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public static User add(User user) throws SQLException {
        String sql = null;        try {
            Statement stmt = zEntities.jdb.getConnection().createStatement();
            String fmt = "INSERT INTO \"USER\" (Id, Login, Email, Description, Active, Created, Updated) VALUES('%s', '%s', '%s', '%s', %d, '%s', '%s')";
            String strCreated = DateTime.getDateForDb(user.getCreated());
            String strUpdated = DateTime.getDateForDb(user.getUpdated());
            sql = String.format(fmt,
                    user.getId(),
                    user.getLogin(),
                    //user.getPassword(),
                    user.getEmail(),
                    user.getDescription(),
                    ((user.getActive()) ? 1 : 0),
                    strCreated,
                    strUpdated);
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

    public User clearDirty() {
        this.firstNameDirty = false;
        this.lastNameDirty = false;
        this.loginDirty = false;
        //this.passwordDirty = false;
        this.emailDirty = false;
        this.descriptionDirty = false;
        return this;
    }

    public String[] getDirtyFields() {
        // TODO Must get dirty fields from Entity
        ArrayList<String> dirtyColumns = new ArrayList<String>();
        if(this.firstNameDirty) dirtyColumns.add("FirstName");
        if(this.lastNameDirty) dirtyColumns.add("LastName");
        if(this.loginDirty) dirtyColumns.add("Login");
        //if(this.passwordDirty) dirtyColumns.add("Password");
        if(this.emailDirty) dirtyColumns.add("Email");
        if(this.descriptionDirty) dirtyColumns.add("Description");
        String[] thisResults = Arrays.copyOf(dirtyColumns.toArray(new String[0]), dirtyColumns.size());
        String[] results = new String[thisResults.length];
        System.arraycopy(thisResults, 0, results, 0, thisResults.length);
        return results;
    }

    public String[] getDirtyValues() {
        ArrayList<String> dirtyValues = new ArrayList<String>();
        if(this.firstNameDirty) dirtyValues.add(this.getFirstName());
        if(this.lastNameDirty) dirtyValues.add(this.getLastName());
        if(this.loginDirty) dirtyValues.add(this.getLogin());
        //if(this.passwordDirty) dirtyValues.add(this.getPassword());
        if(this.emailDirty) dirtyValues.add(this.getEmail());
        if(this.descriptionDirty) dirtyValues.add(this.getDescription());
        String[] thisResults = Arrays.copyOf(dirtyValues.toArray(new String[0]), dirtyValues.size());
        String[] results = new String[thisResults.length];
        System.arraycopy(thisResults, 0, results, 0, thisResults.length);
        return results;
    }

    public static User createInstance(ResultSet rs) {
        try {
            User user = new User();
            user.setId(rs.getString("Id"));
            user.setFirstName(rs.getString("FirstName"));
            user.setLastName(rs.getString("LastName"));
            user.setLogin(rs.getString("Login"));
            //user.setPassword(rs.getString("Password"));
            user.setEmail(rs.getString("Email"));
            user.setDescription(rs.getString("Description"));
            user.setActive(rs.getBoolean("Active"));
            user.setCreated(rs.getDate("Created"));
            user.setUpdated(rs.getDate("Updated"));
            user.clearDirty();
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    public User() {
        super();
    }

    public static Boolean StaticConstructor() {
        User.sqlStatement = new SqlStatement("USER");
        // add the columns provided by Entity
        try {
            zEntity.entitySqlStatement.copyTo(User.sqlStatement);
            User.sqlStatement.add("FirstName");
            User.sqlStatement.add("LastName");
            User.sqlStatement.add("Login");
            //User.sqlStatement.add("Password");
            User.sqlStatement.add("Email");
            User.sqlStatement.add("Description");
        } catch (DuplicateValueException e) {}
        return true;
    }
}
