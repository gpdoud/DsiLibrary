/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.security.rolebasedsecurity;

/**
 *
 * @author Owner
 */
public class BooleanProperty extends BaseProperty {
    private Boolean value = null;
    public Boolean getValue() { return this.value; }
    public String getSqlValue() { return (getValue() ? "1" : "0"); }
    public void setValue(Boolean value) {
        this.value = value;
        this.setDirty();
    }
}
