/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.utility.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Owner
 */
public class JavaDB {

    private Connection conn = null;
    public Connection getConnection() { return this.conn; }

    public ResultSet execSql(String sql) throws SQLException {
        ResultSet resultSet = null;
        Statement stmt = conn.createStatement();
        resultSet = stmt.executeQuery(sql);
        return resultSet;
    }
    
    public void createConnection(String url, String userid, String password) throws SQLException {
        conn = DriverManager.getConnection("jdbc:derby:" + url, userid, password);
    }

    private void Init() throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("JavaDB is not installed properly!");
        }
    }

    public void setDatabase(String schema) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("SET SCHEMA " + schema);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public JavaDB(String url, String userid, String password) {
        try {
            Init();
            createConnection(url, userid, password);
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public JavaDB() {
        try {
            Init();
        } catch (SQLException e) {

        }
    }

}
