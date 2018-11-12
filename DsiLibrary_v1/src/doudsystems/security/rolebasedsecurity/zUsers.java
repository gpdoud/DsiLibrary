/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.security.rolebasedsecurity;

import doudsystems.utility.calendar.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author Owner
 */
public class zUsers extends zEntities {
    private static Boolean isStaticInit = StaticConstructor();

    public static User get(String id) throws SQLException {
        User[] users = zUsers.select("Id = '" + id + "'", null);
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
            Statement stmt = zEntities.jdb.getConnection().createStatement();
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

    public static void delete(zEntity entity) throws SQLException {
        String sql = null;
        //User user = (entity instanceof User) ? (User) entity : null;
        User user = null;
        if(user == null) {
            throw new SQLException("ERROR: Users class can only delete instances of User.");
        }
        try {
            Statement stmt = zEntities.jdb.getConnection().createStatement();
            String fmt = "DELETE FROM \"USER\" WHERE Id = '%s'";
            sql = String.format(fmt,
                    user.getId());
            stmt.execute(sql);
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public static User add(zEntity entity) throws SQLException {
        String sql = null;
        User user = checkValidEntityType(entity);
        try {
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

    private static User checkValidEntityType(zEntity entity) throws ClassCastException {
        //User user = (entity instanceof User) ? (User) entity : null;
        User user = null;
        if(user == null) {
            throw new ClassCastException("ERROR: Users class can only add instances of User.");
        }
        return user;
    }

    private static Boolean StaticConstructor() {
        return true;
    }
}
