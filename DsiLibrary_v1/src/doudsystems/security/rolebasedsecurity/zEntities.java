/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.security.rolebasedsecurity;

import doudsystems.utility.sql.*;

/**
 *
 * @author Owner
 */
public class zEntities {

    private static Boolean isStaticInit = StaticConstructor();
    protected static JavaDB jdb = null;

    public static void setDatabaseInstance(String x, String user, String pass) {
        jdb = new JavaDB(x, user, pass);
    }

    public static void setDatabaseDefault(String schema) {
        if(jdb != null) {
            jdb.setDatabase(schema);
        }
    }

    private static Boolean StaticConstructor() {
        return true;
    }
}
