/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.utility.sql;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 *
 * @author Owner
 */
public class SqlStatement {

    private String tableName = null;
    public String getTableName() { return this.tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }
    
    private LinkedHashMap<String, SqlColumnStructure> columnList = null;

    public void copyTo(SqlStatement sqlStatement) throws DuplicateValueException {
        for(String key : columnList.keySet()) {
            SqlColumnStructure scs = columnList.get(key);
            sqlStatement.add(scs.name, scs.quoted);
        }
    }
    public String toInsertStatement() {
        String[] columns = this.retrieveAllColumns();
        return this.toInsertStatement(columns);
    }
    public String toInsertStatement(String[] columns) {
        String sqlformat = "INSERT INTO \"" + this.tableName + "\" (%s) VALUES (%s)";
        String strColumns = "";
        String strValues = "";
        String comma = "";
        String quote = "";
        for(String column : columns) {
            if(!columnList.containsKey(column)) {
                System.out.println("Column not in column list.");
                return null;
            }
            SqlColumnStructure scs = columnList.get(column);
            quote = (scs.quoted) ? "'" : "";
            strColumns += comma + scs.name;
            strValues += comma + quote + "%s" + quote;
            comma = ", ";
        }
        String sql = String.format(sqlformat, strColumns, strValues);
        return sql;
    }

    public String toUpdateStatement(String[] columns, String[] values, String whereClause) {
        String sqlformat = "UPDATE \"" + this.tableName + "\" SET %s ";
        String strColumns = "";
        String comma = "";
        String quote = "";
        if(columns == null) {
            columns = this.retrieveAllColumns();
        }
        for(int idx = 0; idx < columns.length; idx++) {
            String column = columns[idx];
            String value = values[idx];
            if(!columnList.containsKey(column)) {
                System.out.println("Column not in column list.");
                return null;
            }
            SqlColumnStructure scs = columnList.get(column);
            quote = (scs.quoted) ? "'" : "";
            strColumns += comma + column + " = " + quote + value + quote;
            comma = ", ";
        }
        String sql = String.format(sqlformat, strColumns);
        if(whereClause != null) {
            sql += "WHERE (" + whereClause + ") ";
        }
        return sql;
    }

    public String toDeleteStatement(String whereClause) {
        String sqlformat = "DELETE FROM \"" + this.tableName + "\" ";
        String sql = String.format(sqlformat);
        if(whereClause != null) {
            sql += "WHERE (" + whereClause + ") ";
        }
        return sql;
    }
    private String[] retrieveAllColumns() {
        Set<String> keys = columnList.keySet();
        String[] columns = Arrays.copyOf(keys.toArray(new String[0]), keys.size());
        return columns;
    }
    public String toSelectStatement() {
        String[] columns = retrieveAllColumns();
        String sql = this.toSelectStatement(columns);
        return sql;
    }
    public String toSelectStatement(String[] columns) {
        return this.toSelectStatement(columns, null);
    }
    public String toSelectStatement(String[] columns, String whereClause) {
        return this.toSelectStatement(columns, whereClause, null);
    }
    public String toSelectStatement(String whereClause, String orderClause) {
        String[] columns = retrieveAllColumns();
        String sql = this.toSelectStatement(columns, whereClause, orderClause);
        return sql;
    }
    public String toSelectStatement(String[] columns, String whereClause, String orderClause) {
        String sqlformat = "SELECT %s FROM \"" + this.tableName + "\" ";
        String strColumns = "";
        String comma = "";
        for(String column : columns) {
            if(!columnList.containsKey(column)) {
                System.out.println("Column not in column list.");
                return null;
            }
            SqlColumnStructure scs = columnList.get(column);
            strColumns += comma + scs.name;
            comma = ", ";
        }
        String sql = String.format(sqlformat, strColumns);
        if(whereClause != null) {
            sql += "WHERE (" + whereClause + ") ";
        }
        if(orderClause != null) {
            sql += "ORDER BY " + orderClause;
        }
        return sql;
    }

    public void add(String[] names) throws DuplicateValueException {
        for(String name : names) {
            this.add(name);
        }
    }
    public void add(String name) throws DuplicateValueException {
        this.add(name, true);
    }
    public void add(String name, Boolean quoted) throws DuplicateValueException {
        if(columnList.containsKey(name)) {
            throw new DuplicateValueException("Column already exists!");
        }
        SqlColumnStructure cs = new SqlColumnStructure();
        cs.name = name;
        cs.quoted = quoted;
        columnList.put(name, cs);
    }

    public SqlStatement(String tableName) {
        this.tableName = tableName;
        columnList = new LinkedHashMap<String, SqlColumnStructure>();
    }
    public SqlStatement() {
        this(null);
    }

    class SqlColumnStructure {
        public String name;
        public Boolean quoted;
    }
}
