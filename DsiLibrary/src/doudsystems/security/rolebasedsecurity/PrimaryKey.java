/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.security.rolebasedsecurity;

import java.util.UUID;

/**
 *
 * @author Owner
 */
public class PrimaryKey extends StringProperty {
    public void generatePrimaryKey() { 
        this.setValue(UUID.randomUUID().toString()); 
    }
}
