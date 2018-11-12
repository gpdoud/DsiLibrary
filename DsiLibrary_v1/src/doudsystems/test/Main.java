package doudsystems.test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.w3c.dom.*;

import doudsystems.utility.xml.*;
import doudsystems.utility.sql.*;
import doudsystems.utility.logging.*;
import doudsystems.security.rolebasedsecurity.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Owner
 */
public class Main {

    public static Logging Log = new Logging();

    void sqlStatement() {
        try {
            String[] columns = new String[] { "Id", "Login", "Password", "Email", 
                "Description", "Created", "Updated" };
            SqlStatement sql = new SqlStatement("USER");
            sql.add(columns);
            sql.add("Active", Boolean.FALSE);

            String stmt = sql.toSelectStatement();
            System.out.println(stmt);
            columns = new String[] { "Id", "Password", "Description" };
            stmt = sql.toSelectStatement(columns);
            console(stmt);
            stmt = sql.toSelectStatement(columns, "Id = 'ABC123'");
            console(stmt);
            stmt = sql.toSelectStatement(columns, null, "Email");
            console(stmt);
            stmt = sql.toSelectStatement(columns, "Password = 'radioham'", "Email, Login Desc");
            console(stmt);
            stmt = sql.toDeleteStatement("Id = 'ABC123'");
            console(stmt);
            stmt = sql.toInsertStatement();
            console(stmt);
            stmt = sql.toInsertStatement(columns);
            console(stmt);
        } catch (DuplicateValueException e) {
            System.out.println("Duplication value exception: " + e.getMessage());
        }
    }

    void update() {
        zEntities.setDatabaseInstance("//localhost:1527/Test", "gpdoud", "radioham");
        zEntities.setDatabaseDefault("RBS");

        try {
            User greg = zUsers.get("UGD");
            console("Password valid : " + greg.isValidPassword("radioham"));
        } catch(Exception e) {}
    }

    void get() {
        zEntities.setDatabaseInstance("//localhost:1527/Test", "gpdoud", "radioham");
        zEntities.setDatabaseDefault("RBS");

        String key = "T004";
        try {
            User user = zUsers.get(key);
            if(user == null) {
                error("Key " + key + " not found!");
                return;
            }
            console("For key " + key + ", user name is " + user.getFirstName() + " " + user.getLastName() +
                        ", login is " + user.getLogin());
        } catch(Exception e) {}
    }
    void select() {
        zEntities.setDatabaseInstance("//localhost:1527/Test", "gpdoud", "radioham");
        zEntities.setDatabaseDefault("RBS");

        try {
            User[] users = zUsers.select("FirstName = 'Nick' or FirstName = 'Ken'", "FirstName");
            for(User user : users) {
                console("User name is " + user.getFirstName() + " " + user.getLastName() +
                        ", login is " + user.getLogin());
            }
        } catch(Exception e) {}
    }
    void roleBasedSecurity() {
        zEntities.setDatabaseInstance("//localhost:1527/Test", "gpdoud", "radioham");
        zEntities.setDatabaseDefault("RBS");

        try {
            User[] users = zUsers.select(null, null);
            for(User user : users) {
                console("User login is " + user.getLogin());
            }
        } catch(Exception e) {}

        try {
            User u1 = zUsers.get("07291d78-02f8-410c-be0b-4b7f7d273a06");
            if(u1 == null) {
                throw new Exception("User not found.");
            }
            u1.setDescription("Updated User Record!");
            u1.update();
        } catch (Exception e) {}

        User user = null;
        try {
            user = new User();
            user.setDescription("My first User");
            user.setEmail("gpdoud@hotmail.com");
            user.setLogin("gpdoud");
            //user.setPassword("radioham");
            user.add();

            User newUser = zUsers.get(user.getId());

            user.setDescription("A new description for this user.");
            user.update();

            user.delete();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
    void sql() {
        JavaDB jdb = new JavaDB("//localhost:1527/Test", "gpdoud", "radioham");
        try {
            ResultSet rs = jdb.execSql("Select * from GPDOUD.\"User\"");
        } catch (SQLException e) {

        }
    }
    void selectX() {
        Document doc = XmlDocument.openDocument("C:\\Users\\Owner\\Documents\\Projects\\DsiLibraryTest\\Customers.xml");
        NodeList custs = doc.getElementsByTagName("customer");
        for(int idx = 0; idx < custs.getLength(); idx++) {
            Element cust = (Element) custs.item(idx);
            System.out.println("Customer:");
            String name = XmlDocument.selectSingleElement(cust, "name").getTextContent();
            System.out.println("\tName:\t" + name);
            String addr = XmlDocument.selectSingleElement(cust, "address").getTextContent();
            System.out.println("\tAddr:\t" + addr);
            String city = XmlDocument.selectSingleElement(cust, "city").getTextContent();
            String state = XmlDocument.selectSingleElement(cust, "state").getTextContent();
            String zip = XmlDocument.selectSingleElement(cust, "zip").getTextContent();
            System.out.println("\tCSZ:\t" + city + ", " + state + " " + zip);
            String phone = XmlDocument.selectSingleElement(cust, "phone").getTextContent();
            System.out.println("\tPhone:\t" + phone);
            System.out.println();
        }
    }
    void create() {
        Document doc = XmlDocument.newDocument();
        doc.setXmlVersion("1.0");
        Element root = doc.createElement("customers");
        doc.appendChild(root);
        Element cust1 = XmlDocument.addElement(root, "customer", null);
        cust1.setAttribute("id", "gd");
        XmlDocument.addElement(cust1, "name", "Gregory Doud");
        XmlDocument.addElement(cust1, "address", "260 Riva Ridge Ct");
        XmlDocument.addElement(cust1, "city", "Loveland");
        XmlDocument.addElement(cust1, "state", "Ohio");
        XmlDocument.addElement(cust1, "zip", "45140-7793");
        XmlDocument.addElement(cust1, "phone", "513-703-7315");

        Element cust2 = XmlDocument.addElement(root, "customer", null);
        cust2.setAttribute("id", "cd");
        XmlDocument.addElement(cust2, "name", "Cynthia Doud");
        XmlDocument.addElement(cust2, "address", "260 Riva Ridge Ct");
        XmlDocument.addElement(cust2, "city", "Loveland");
        XmlDocument.addElement(cust2, "state", "Ohio");
        XmlDocument.addElement(cust2, "zip", "45140-7793");
        XmlDocument.addElement(cust2, "phone", "513-304-7990");

        Element cust3 = XmlDocument.addElement(root, "customer", null);
        cust3.setAttribute("id", "nd");
        XmlDocument.addElement(cust3, "name", "Nicholas Doud");
        XmlDocument.addElement(cust3, "address", "260 Riva Ridge Ct");
        XmlDocument.addElement(cust3, "city", "Loveland");
        XmlDocument.addElement(cust3, "state", "Ohio");
        XmlDocument.addElement(cust3, "zip", "45140-7793");
        XmlDocument.addElement(cust3, "phone", "513-600-4732");

        Element cust4 = XmlDocument.addElement(root, "customer", null);
        cust4.setAttribute("id", "kd");
        XmlDocument.addElement(cust4, "name", "Kenneth Doud");
        XmlDocument.addElement(cust4, "address", "260 Riva Ridge Ct");
        XmlDocument.addElement(cust4, "city", "Loveland");
        XmlDocument.addElement(cust4, "state", "Ohio");
        XmlDocument.addElement(cust4, "zip", "45140-7793");
        XmlDocument.addElement(cust4, "phone", "513-600-3890");

        XmlDocument.saveDocument(doc, "C:\\Users\\Owner\\Documents\\Projects\\DsiLibraryTest\\Customers.xml");
    }
    void open() {
        Document doc = XmlDocument.openDocument("C:\\Users\\Owner\\Documents\\Projects\\DsiLibraryTest\\XmlDoc.xml");
        Element root = doc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        for(int idx = 0; idx < childNodes.getLength(); idx++) {
            Node node = childNodes.item(idx);
            System.out.println(node.getNodeName() + "\n");
        }
    }

    void console(Object message) {
        msg(message, Logging.Severity.Info);
    }
    void error(Object message) {
        msg(message, Logging.Severity.Debug);
    }
    void msg(Object message, Logging.Severity severity) {
        Log.log(message, severity);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Log.setDisplayDate(Boolean.FALSE);
        Log.all("Begin RoleBasedSecurity test ...");
        Main pgm = new Main();
        pgm.update();
        Log.all("End RoleBasedSecurity test ...");
    }

}
