/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.security.rolebasedsecurity;

/**
 *
 * @author Owner
 */
public class BaseProperty {
    protected Boolean dirty = false;
    public Boolean isDirty() { return this.dirty; }
    public void setDirty() { this.dirty = true; }
    public void clearDirty() { this.dirty = false; }
}
