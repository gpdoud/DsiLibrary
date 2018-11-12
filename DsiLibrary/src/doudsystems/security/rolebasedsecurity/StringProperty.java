/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.security.rolebasedsecurity;

/**
 *
 * @author Owner
 */
public class StringProperty extends BaseProperty {
    protected String value = null;
    public String getValue() { return this.value; }
    public String getSqlValue() { return "'" + getValue() + "'"; }
    public void setValue(String value) {
        this.value = value;
        this.setDirty();
    }
}
