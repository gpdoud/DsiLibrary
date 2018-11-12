package doudsystems.test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import doudsystems.utility.logging.*;
import doudsystems.security.rolebasedsecurity.*;

/**
 *
 * @author Owner
 */
public class Main {

    public static Logging Log = new Logging();

    void testPrivilege() {
        Log.info("Begin testPrivilege();");
        try {
            Privilege psa = Privilege.get("PSA");
            psa.Name.setValue("The Super Admin");
            psa.Description.setValue("The Super Admin Description");
            psa.Category.setValue("SYSTEM");
            psa.CanChange.setValue(true);
            psa.CanDelete.setValue(true);
            Privilege.update(psa);
            Privilege[] ps = Privilege.select(null, null);
        } catch (Exception e) {
            error(e.getMessage());
            return;
        }
        Log.info("End testPrivilege();");
    }

    void testRole() {
        try {
            Role rAdmin = new Role("Admin", "System Administrator").add();
            Role rUser = new Role("User", "Normal user").add();
        } catch (Exception e) {
            error(e.getMessage());
            return;
        }
    }

    void testUser() {
        try {
            User ug = new User("Gregory", "Doud", "gdoud", "gpdoud@hotmail.com", "A father").add();
            User uc = new User("Cynthia", "Doud", "cjdoud", "cjdoud@hotmail.com", "A mother").add();
            User un = new User("Nicholas", "Doud", "nsdoud", "nsdoud@hotmail.com", "A son").add();
            User uk = new User("Kenneth", "Doud", "kmdoud", "kmdoud@hotmail.com", "A son").add();
        } catch (Exception e) {
            error(e.getMessage());
            return;
        }
    }

    void test() {
        try {
            testPrivilege();
            testRole();
            testUser();
        } catch(Exception e) {}
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
        pgm.test();
        Log.all("End RoleBasedSecurity test ...");
    }

}
