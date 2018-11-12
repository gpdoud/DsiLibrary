/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.security.rolebasedsecurity;

import doudsystems.utility.cryptography.*;
import doudsystems.utility.sql.DuplicateValueException;
import doudsystems.utility.sql.SqlStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Owner
 */
public class Pass extends zEntity {

    private static Boolean isStaticInit = StaticConstructor();
    protected static SqlStatement sqlStatement = new SqlStatement(null);

    private String userId = null;
    public String getUserId() { return this.userId; }
    protected Pass setUserId(String userId) {
        this.userId = userId;
        this.setUpdated();
        return this;
    }

    private String password = null;
    private Boolean passwordDirty = false;
    public String getPassword() { return this.password; }
    public Pass setPassword(String password) {
        this.password = Password.encryptOneWayToBase64(password);
        this.passwordDirty = true;
        this.setUpdated();
        return this;
    }
    public Boolean isValidPassword(String password) {
        String currentPassword = this.getPassword();
        String enteredPassword = Password.encryptOneWayToBase64(password);
        return currentPassword.equals(enteredPassword);
    }

    private Boolean chgPassword = false;
    private Boolean chgPasswordDirty = false;
    public Boolean getChgPassword() { return this.chgPassword; }
    public Pass setChgPassword(Boolean chgPassword) {
        this.chgPassword = chgPassword;
        this.chgPasswordDirty = true;
        this.setUpdated();
        return this;
    }

    private String question = null;
    private Boolean questionDirty = false;
    public String getQuestion() { return this.question; }
    public Pass setQuestion(String question) {
        this.question = question;
        this.questionDirty = true;
        this.setUpdated();
        return this;
    }

    private String answer = null;
    private Boolean answerDirty = false;
    public String getAnswer() { return this.answer; }
    public Pass setAnswer(String answer) {
        this.answer = answer;
        this.answerDirty = true;
        this.setUpdated();
        return this;
    }

    private Boolean locked = false;
    private Boolean lockedDirty = false;
    public Boolean getLocked() { return this.locked; }
    public Pass setLocked(Boolean locked) {
        this.locked = locked;
        this.lockedDirty = true;
        this.setUpdated();
        return this;
    }

    @Override
    public Pass update() throws SQLException {
        zPasses.update(this);
        this.clearDirty();
        return this;
    }
    @Override
    public void delete() throws SQLException {
        zPasses.delete(this);
    }
    @Override
    public Pass add() throws SQLException {
        zPasses.add(this);
        this.clearDirty();
        return this;
    }

    @Override
    public Pass clearDirty() {
        super.clearDirty();
        this.passwordDirty = false;
        this.chgPasswordDirty = false;
        this.questionDirty = false;
        this.answerDirty = false;
        this.lockedDirty = false;
        return this;
    }

    @Override
    public String[] getDirtyFields() {
        // TODO Must get dirty fields from Entity
        ArrayList<String> dirtyColumns = new ArrayList<String>();
        if(this.passwordDirty) dirtyColumns.add("Password");
        if(this.chgPassword) dirtyColumns.add("ChgPassword");
        if(this.questionDirty) dirtyColumns.add("Question");
        if(this.answerDirty) dirtyColumns.add("Answer");
        if(this.lockedDirty) dirtyColumns.add("Locked");
        String[] thisResults = Arrays.copyOf(dirtyColumns.toArray(new String[0]), dirtyColumns.size());
        String[] superResults = super.getDirtyFields();
        String[] results = new String[thisResults.length + superResults.length];
        System.arraycopy(thisResults, 0, results, 0, thisResults.length);
        System.arraycopy(superResults, 0, results, thisResults.length, superResults.length);
        return results;
    }

    @Override
    public String[] getDirtyValues() {
        ArrayList<String> dirtyValues = new ArrayList<String>();
        if(this.passwordDirty) dirtyValues.add(this.getPassword());
        if(this.chgPasswordDirty) dirtyValues.add(this.getChgPassword().toString());
        if(this.questionDirty) dirtyValues.add(this.getQuestion());
        if(this.answerDirty) dirtyValues.add(this.getAnswer());
        if(this.lockedDirty) dirtyValues.add(this.getLocked().toString());
        String[] thisResults = Arrays.copyOf(dirtyValues.toArray(new String[0]), dirtyValues.size());
        String[] superResults = super.getDirtyValues();
        String[] results = new String[thisResults.length + superResults.length];
        System.arraycopy(thisResults, 0, results, 0, thisResults.length);
        System.arraycopy(superResults, 0, results, thisResults.length, superResults.length);
        return results;
    }

    public static Pass createInstance(ResultSet rs) {
        try {
            Pass pass = new Pass();
            pass.setId(rs.getString("Id"));
            pass.setUserId(rs.getString("UserId"));
            pass.setPassword(rs.getString("Password"));
            pass.setChgPassword(rs.getBoolean("ChgPassword"));
            pass.setQuestion(rs.getString("Question"));
            pass.setAnswer(rs.getString("Answer"));
            pass.setLocked(rs.getBoolean("Locked"));
            pass.setActive(rs.getBoolean("Active"));
            pass.setCreated(rs.getDate("Created"));
            pass.setUpdated(rs.getDate("Updated"));
            pass.clearDirty();
            return pass;
        } catch (SQLException e) {
            return null;
        }
    }

    public Pass() {
        super();
    }

    private static Boolean StaticConstructor() {
        Pass.sqlStatement = new SqlStatement("PASS");
        // add the columns provided by Entity
        try {
            zEntity.entitySqlStatement.copyTo(Pass.sqlStatement);
            Pass.sqlStatement.add("UserId");
            Pass.sqlStatement.add("Password");
            Pass.sqlStatement.add("ChgPassword");
            Pass.sqlStatement.add("Question");
            Pass.sqlStatement.add("Answer");
            Pass.sqlStatement.add("Locked");
        } catch (DuplicateValueException e) {}
        return true;
    }
}
