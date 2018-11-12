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
public class UserSecurity {

    protected static SqlStatement sqlStatement;
    protected static JavaDB jdb = null;

    private String id = null;
    private Boolean idDirty = false;
    public String getId() { return this.id; }
    protected void setId(String id) {
        this.id = id;
        this.idDirty = true;
        this.setUpdated();
    }

    private String userId = null;
    private Boolean userIdDirty = false;
    public String getUserId() { return this.userId; }
    protected void setUserId(String userId) {
        this.userId = userId;
        this.userIdDirty = true;
        this.setUpdated();
    }

    private String password = null;
    private Boolean passwordDirty = false;
    public String getPassword() { return this.password; }
    public void setPassword(String password) {
        if(password == null) {
            this.password = null;
        } else {
            this.password = doudsystems.utility.cryptography.Password.encryptOneWayToBase64(password);
        }
        this.passwordDirty = true;
        this.setUpdated();
    }

    private Boolean chgPassword = false;
    private Boolean chgPasswordDirty = false;
    public Boolean getChgPassword() { return this.chgPassword; }
    public void setChgPassword(Boolean chgPassword) {
        this.chgPassword = chgPassword;
        this.chgPasswordDirty = true;
        this.setUpdated();
    }

    private String question = null;
    private Boolean questionDirty = false;
    public String getQuestion() { return this.question; }
    public void setQuestion(String question) {
        this.question = question;
        this.passwordDirty = true;
        this.setUpdated();
    }

    private String answer = null;
    private Boolean answerDirty = false;
    public String getAnswer() { return this.answer; }
    public void setAnswer(String answer) {
        if(answer == null) {
            this.answer = null;
        } else {
            this.answer = doudsystems.utility.cryptography.Password.encryptOneWayToBase64(answer);
        }
        this.answerDirty = true;
        this.setUpdated();
    }

    private Boolean active = false;
    private Boolean activeDirty = false;
    public Boolean getActive() { return this.active; }
    public void setActive(Boolean active) {
        this.active = active;
        this.activeDirty = true;
        this.setUpdated();
    }

    private Boolean locked = false;
    private Boolean lockedDirty = false;
    public Boolean getLocked() { return this.locked; }
    public void setLocked(Boolean locked) {
        this.locked = locked;
        this.lockedDirty = true;
        this.setUpdated();
    }

    private Calendar created = null;
    private Boolean createdDirty = false;
    public Calendar getCreated() { return this.created; }
    protected void setCreated(Calendar created) {
        this.created = created;
        this.createdDirty = true;
    }
    protected void setCreated(Date created) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(created);
        setCreated(cal);
    }
    protected void setCreated() {
        Calendar cal = Calendar.getInstance();
        setCreated(cal);
    }

    private Calendar updated = null;
    private Boolean updatedDirty = false;
    public Calendar getUpdated() { return this.updated; }
    protected void setUpdated(Calendar updated) {
        this.updated = updated;
        this.updatedDirty = true;
    }
    protected void setUpdated(Date updated) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(updated);
        setCreated(cal);
    }
    protected void setUpdated() {
        Calendar cal = Calendar.getInstance();
        setUpdated(cal);
    }

    private Calendar deleted = null;
    private Boolean deletedDirty = false;
    public Calendar getDeleted() { return this.deleted; }
    protected void setDeleted(Calendar deleted) {
        this.deleted = deleted;
        this.deletedDirty = true;
    }
    protected void setDeleted(Date deleted) {
        Calendar cal = Calendar.getInstance();
        if(deleted == null) {
            cal.setTimeInMillis(0);
        } else {
            cal.setTime(deleted);
        }
        setCreated(cal);
    }
    protected void setDeleted() {
        Calendar cal = Calendar.getInstance();
        setUpdated(cal);
    }

    private Boolean disposed;
    private Boolean getDisposed() { return this.disposed; }
    private void setDisposed(Boolean disposed) { this.disposed = disposed; }
    public void dispose() { this.setDisposed(true); }
    public Boolean isDisposed() { return this.getDisposed(); }

    public static UserSecurity getUserId(String userId) throws SQLException {
        if(userId == null) {
            throw new SQLException("UserId cannot be null!");
        }
        UserSecurity[] userSecurities = select("UserId = '" + userId + "'", null);
        if(userSecurities.length == 0) {
            throw new SQLException("No rows found for user id " + userId);
        } else if (userSecurities.length > 1) {
            throw new SQLException("More than one security record found for user id " + userId);
        }
        UserSecurity userSecurity = userSecurities[0];
        return userSecurity;
    }

    public static UserSecurity get(String id) throws SQLException {
        UserSecurity[] passwords = UserSecurity.select("Id = '" + id + "'", null);
        if(passwords.length == 0) {
            throw new SQLException("No rows found for user id " + id);
        }
        return passwords[0];
    }

    public static UserSecurity[] select(String whereClause, String orderClause) {
        String sql = null;
        UserSecurity[] passwords = null;
        ArrayList<UserSecurity> list = null;
        try {
            Statement stmt = jdb.getConnection().createStatement();
            sql = UserSecurity.sqlStatement.toSelectStatement(whereClause, orderClause);
            ResultSet rs = stmt.executeQuery(sql);
            list = new ArrayList<UserSecurity>();
            while(rs.next()) {
                UserSecurity password = UserSecurity.createInstance(rs);
                list.add(password);
            }
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        passwords = Arrays.copyOf(list.toArray(new UserSecurity[0]), list.size());
        return passwords;
    }

    public static UserSecurity update(UserSecurity password) throws SQLException {
        String sql = null;
        if(password == null) {
            throw new SQLException("ERROR: Passwords class can only update instances of Password.");
        }
        try {
            Statement stmt = jdb.getConnection().createStatement();
            String[] dirtyColumns = password.getDirtyFields();
            String[] dirtyValues = password.getDirtyValues();
            sql = UserSecurity.sqlStatement.toUpdateStatement(dirtyColumns, dirtyValues, "Id = '" + password.getId() + "'");
            stmt.execute(sql);
            password.clearDirty(); // clear the isDirty flag for all columns
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return password;
    }

    public static void delete(UserSecurity password) throws SQLException {
        String sql = null;
        if(password == null) {
            throw new SQLException("ERROR: Passwords class can only delete instances of Password.");
        }
        try {
            Statement stmt = jdb.getConnection().createStatement();
            String fmt = UserSecurity.sqlStatement.toDeleteStatement("Id = '" + password.getId() + "'");
            sql = String.format(fmt,
                    password.getId());
            stmt.execute(sql);
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public static UserSecurity add(UserSecurity password) throws SQLException {
        String sql = null;
        try {
            Statement stmt = jdb.getConnection().createStatement();
            String fmt = UserSecurity.sqlStatement.toInsertStatement();
            sql = String.format(fmt,
                    password.getId(),
                    password.getUserId(),
                    password.getPassword(),
                    password.getChgPassword(),
                    password.getQuestion(),
                    password.getAnswer(),
                    Conversion.parse(password.getLocked()),
                    Conversion.parse(password.getActive()),
                    Conversion.getDateForDb(password.getCreated()),
                    Conversion.getDateForDb(password.getUpdated()),
                    Conversion.getDateForDb(password.getDeleted()));
            stmt.execute(sql);
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return password;
    }

    public UserSecurity update() throws SQLException {
        UserSecurity.update(this);
        this.clearDirty();
        return this;
    }
    public void delete() throws SQLException {
        UserSecurity.delete(this);
    }
    public UserSecurity add() throws SQLException {
        UserSecurity.add(this);
        this.clearDirty();
        return this;
    }

    public UserSecurity clearDirty() {
        this.idDirty = false;
        this.userIdDirty = false;
        this.passwordDirty = false;
        this.chgPasswordDirty = false;
        this.questionDirty = false;
        this.answerDirty = false;
        this.lockedDirty = false;
        this.activeDirty = false;
        this.createdDirty = false;
        this.updatedDirty = false;
        this.deletedDirty = false;
        return this;
    }

    public String[] getDirtyFields() {
        ArrayList<String> dirtyColumns = new ArrayList<String>();
        if(this.userIdDirty) dirtyColumns.add("UserId");
        if(this.passwordDirty) dirtyColumns.add("Password");
        if(this.chgPasswordDirty) dirtyColumns.add("ChgPassword");
        if(this.questionDirty) dirtyColumns.add("Question");
        if(this.answerDirty) dirtyColumns.add("Answer");
        if(this.lockedDirty) dirtyColumns.add("Locked");
        if(this.activeDirty) dirtyColumns.add("Active");
        if(this.createdDirty) dirtyColumns.add("Created");
        if(this.updatedDirty) dirtyColumns.add("Updated");
        if(this.deletedDirty) dirtyColumns.add("Deleted");
        String[] thisResults = Arrays.copyOf(dirtyColumns.toArray(new String[0]), dirtyColumns.size());
        String[] results = new String[thisResults.length];
        System.arraycopy(thisResults, 0, results, 0, thisResults.length);
        return results;
    }

    public String[] getDirtyValues() {
        ArrayList<String> dirtyValues = new ArrayList<String>();
        if(this.userIdDirty) dirtyValues.add(this.getUserId());
        if(this.passwordDirty) dirtyValues.add(this.getPassword());
        if(this.chgPasswordDirty) dirtyValues.add(this.getChgPassword().toString());
        if(this.questionDirty) dirtyValues.add(this.getQuestion());
        if(this.answerDirty) dirtyValues.add(this.getAnswer());
        if(this.lockedDirty) dirtyValues.add(this.getLocked().toString());
        if(this.activeDirty) dirtyValues.add(this.getActive().toString());
        if(this.createdDirty) dirtyValues.add(Conversion.getDateForDb(this.getCreated()));
        if(this.updatedDirty) dirtyValues.add(Conversion.getDateForDb(this.getUpdated()));
        if(this.deletedDirty) dirtyValues.add(Conversion.getDateForDb(this.getDeleted()));
        String[] thisResults = Arrays.copyOf(dirtyValues.toArray(new String[0]), dirtyValues.size());
        String[] results = new String[thisResults.length];
        System.arraycopy(thisResults, 0, results, 0, thisResults.length);
        return results;
    }

    public static UserSecurity createInstance(ResultSet rs) {
        try {
            UserSecurity user = new UserSecurity();
            user.setId(rs.getString("Id"));
            user.setUserId(rs.getString("UserId"));
            user.setPassword(rs.getString("Password"));
            user.setChgPassword(rs.getBoolean("ChgPassword"));
            user.setQuestion(rs.getString("Question"));
            user.setAnswer(rs.getString("Answer"));
            user.setLocked(rs.getBoolean("Locked"));
            user.setActive(rs.getBoolean("Active"));
            user.setCreated(rs.getDate("Created"));
            user.setUpdated(rs.getDate("Updated"));
            user.setDeleted(rs.getDate("Deleted"));
            user.clearDirty();
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    public UserSecurity() {
    }

    static  {
        jdb = new JavaDB("//localhost:1527/Test", "gpdoud", "radioham");
        jdb.setDatabase("RBS");
        sqlStatement = new SqlStatement("USERSECURITY");
        // add the columns provided by Entity
        try {
            sqlStatement.add("Id");
            sqlStatement.add("UserId");
            sqlStatement.add("Password");
            sqlStatement.add("ChgPassword", false);
            sqlStatement.add("Question");
            sqlStatement.add("Answer");
            sqlStatement.add("Locked", false);
            sqlStatement.add("Active", false);
            sqlStatement.add("Created");
            sqlStatement.add("Updated");
            sqlStatement.add("Deleted");
        } catch (DuplicateValueException e) {}
    }
}
