/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.security.rolebasedsecurity;

import doudsystems.utility.sql.DuplicateValueException;
import doudsystems.utility.sql.JavaDB;
import doudsystems.utility.sql.SqlStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Owner
 */
public class Privilege extends BaseEntity {

    public StringProperty Name = new StringProperty();
    public StringProperty Description = new StringProperty();
    public StringProperty Category = new StringProperty();
    public BooleanProperty CanChange = new BooleanProperty();
    public BooleanProperty CanDelete = new BooleanProperty();

    public static void createInstance(Privilege priv, ResultSet rs) {
        try {
            BaseEntity.createInstance(priv, rs);
            priv.Name.setValue(rs.getString("Name"));
            priv.Description.setValue(rs.getString("Description"));
            priv.Category.setValue(rs.getString("Category"));
            priv.CanChange.setValue(rs.getBoolean("CanChange"));
            priv.CanDelete.setValue(rs.getBoolean("CanDelete"));
            priv.clearDirty();
        } catch (SQLException e) {
        }
    }

    public static Privilege get(String id) throws SQLException {
        Privilege[] recs = Privilege.select("Id = '" + id + "'", null);
        if(recs.length != 1) {
            throw new SQLException("Get on primary key returned " + recs.length + " rows.");
        }
        return recs[0];
    }
    public static Privilege[] select(String whereClause, String orderClause) throws SQLException {
        ArrayList<Privilege> list = null;
        try {
            Statement stmt = jdb.getConnection().createStatement();
            String sql = Privilege.sqlStatement.toSelectStatement(whereClause, orderClause);
            ResultSet rs = stmt.executeQuery(sql);
            list = new ArrayList<Privilege>();
            while(rs.next()) {
                Privilege rec = new Privilege();
                Privilege.createInstance(rec, rs);
                list.add(rec);
            }
            Privilege[] recs = Arrays.copyOf(list.toArray(new Privilege[0]), list.size());
            return recs;
        } catch(SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static void update(Privilege priv) throws SQLException {
        if(priv == null) {
            throw new SQLException("ERROR: Users class can only update instances of User.");
        }
        try {
            Statement stmt = jdb.getConnection().createStatement();
            String[] dirtyColumns = priv.getDirtyFields();
            String[] dirtyValues = priv.getDirtyValues();
            //sql = User.sqlStatement.toUpdateStatement(dirtyColumns, dirtyValues, "Id = '" + user.getId() + "'");
            String sql = Privilege.sqlStatement.toUpdateStatement(dirtyColumns, dirtyValues, "Id = '" + priv.pk.getValue() + "'");
            stmt.execute(sql);
            priv.clearDirty(); // clear the isDirty flag for all columns
        } catch(SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public String[] getDirtyFields() {
        String[] superDirtyColumns = super.getDirtyFields();
        ArrayList<String> dirtyColumns = new ArrayList<String>();
        for(String column : superDirtyColumns) {
            dirtyColumns.add(column);
        }
        if(this.Name.isDirty()) dirtyColumns.add("Name");
        if(this.Description.isDirty()) dirtyColumns.add("Description");
        if(this.Category.isDirty()) dirtyColumns.add("Category");
        if(this.CanChange.isDirty()) dirtyColumns.add("CanChange");
        if(this.CanDelete.isDirty()) dirtyColumns.add("CanDelete");
        String[] results = Arrays.copyOf(dirtyColumns.toArray(new String[0]), dirtyColumns.size());
        return results;
    }

    @Override
    public String[] getDirtyValues() {
        String[] superDirtyValues = super.getDirtyValues();
        ArrayList<String> dirtyValues = new ArrayList<String>();
        for(String value : superDirtyValues) {
            dirtyValues.add(value);
        }
        if(this.Name.isDirty()) dirtyValues.add(this.Name.getSqlValue());
        if(this.Description.isDirty()) dirtyValues.add(this.Description.getSqlValue());
        if(this.Category.isDirty()) dirtyValues.add(this.Category.getSqlValue());
        if(this.CanChange.isDirty()) dirtyValues.add(this.CanChange.getSqlValue());
        if(this.CanDelete.isDirty()) dirtyValues.add(this.CanDelete.getSqlValue());
        String[] results = Arrays.copyOf(dirtyValues.toArray(new String[0]), dirtyValues.size());
        return results;
    }

    @Override
    public void clearDirty() {
        super.clearDirty();
        this.Name.clearDirty();
        this.Description.clearDirty();
        this.Category.clearDirty();
        this.CanChange.clearDirty();
        this.CanDelete.clearDirty();
    }
    static  {
        jdb = new JavaDB("//localhost:1527/Test", "gpdoud", "radioham");
        jdb.setDatabase("RBS");
        sqlStatement = new SqlStatement("PRIVILEGE");
        // add the columns provided by Entity
        try {
            sqlStatement.add("Id");
            sqlStatement.add("Name");
            sqlStatement.add("Description");
            sqlStatement.add("Category");
            sqlStatement.add("CanChange");
            sqlStatement.add("CanDelete");
            sqlStatement.add("Active", true);
            sqlStatement.add("Created");
            sqlStatement.add("Updated");
            sqlStatement.add("Deleted");
        } catch (DuplicateValueException e) {}
    }
}
