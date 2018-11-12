/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.security.rolebasedsecurity;

import doudsystems.utility.calendar.DateTime;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Owner
 */
public class zPasses extends zEntities {
    private static Boolean isStaticInit = StaticConstructor();

    public static Pass get(String id) throws SQLException {
        Pass[] passes = zPasses.select("Id = '" + id + "'", null);
        if(passes.length == 0) {
            return null;
        }
        return passes[0];
    }

    public static Pass getPasswordByUserId(String userId) {
        Pass[] passes = zPasses.select("UserId = '" + userId + "'", null);
        if(passes.length == 0) {
            return null;
        }
        Pass pass = passes[0];
        return pass;
    }

    public static Pass[] select(String whereClause, String orderClause) {
        String sql = null;
        Pass[] passes = null;
        ArrayList<Pass> list = null;
        try {
            Statement stmt = zEntities.jdb.getConnection().createStatement();
            sql = Pass.sqlStatement.toSelectStatement(whereClause, orderClause);
            ResultSet rs = stmt.executeQuery(sql);
            list = new ArrayList<Pass>();
            while(rs.next()) {
                Pass pass = Pass.createInstance(rs);
                list.add(pass);
            }
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        passes = Arrays.copyOf(list.toArray(new Pass[0]), list.size());
        return passes;
    }

    public static Pass update(Pass pass) throws SQLException {
        String sql = null;
        if(pass == null) {
            throw new SQLException("ERROR: Users class can only update instances of User.");
        }
        try {
            Statement stmt = zEntities.jdb.getConnection().createStatement();
            String[] dirtyColumns = pass.getDirtyFields();
            String[] dirtyValues = pass.getDirtyValues();
            sql = Pass.sqlStatement.toUpdateStatement(dirtyColumns, dirtyValues, "Id = '" + pass.getId() + "'");
            stmt.execute(sql);
            pass.clearDirty(); // clear the isDirty flag for all columns
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return pass;
    }

    public static void delete(zEntity entity) throws SQLException {
        String sql = null;
        Pass pass = (entity instanceof Pass) ? (Pass) entity : null;
        if(pass == null) {
            throw new SQLException("ERROR: Passes class can only delete instances of Pass.");
        }
        try {
            Statement stmt = zEntities.jdb.getConnection().createStatement();
            String fmt = "DELETE FROM \"PASS\" WHERE Id = '%s'";
            sql = String.format(fmt,
                    pass.getId());
            stmt.execute(sql);
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public static Pass add(zEntity entity) throws SQLException {
        String sql = null;
        Pass pass = checkValidEntityType(entity);
        try {
            Statement stmt = zEntities.jdb.getConnection().createStatement();
            String fmt = "INSERT INTO \"PASS\" (Id, UserId, Password, ChgPassword, Question, Answer, Locked, Active, Created, Updated) VALUES('%s', '%s', '%s', '%s', '%s', %d, '%s', '%s')";
            String strCreated = DateTime.getDateForDb(pass.getCreated());
            String strUpdated = DateTime.getDateForDb(pass.getUpdated());
            sql = String.format(fmt,
                    pass.getId(),
                    pass.getUserId(),
                    pass.getPassword(),
                    ((pass.getChgPassword()) ? 1 : 0),
                    pass.getQuestion(),
                    pass.getAnswer(),
                    ((pass.getLocked()) ? 1 : 0),
                    ((pass.getActive()) ? 1 : 0),
                    strCreated,
                    strUpdated);
            stmt.execute(sql);
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return pass;
    }

    private static Pass checkValidEntityType(zEntity entity) throws ClassCastException {
        Pass pass = (entity instanceof Pass) ? (Pass) entity : null;
        if(pass == null) {
            throw new ClassCastException("ERROR: Passes class can only add instances of Pass.");
        }
        return pass;
    }

    private static Boolean StaticConstructor() {
        return true;
    }
}
