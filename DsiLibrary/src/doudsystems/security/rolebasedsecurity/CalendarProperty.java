/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.security.rolebasedsecurity;

import doudsystems.utility.conversion.Conversion;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Owner
 */
public class CalendarProperty extends BaseProperty {
    private Calendar value = null;
    public Calendar getValue() { return this.value; }
    public String getSqlValue() { return "'" + Conversion.getDateForDb(getValue()) + "'"; }
    public void setValue(Calendar value) {
        this.value = value;
        this.setDirty();
    }
    public void setValue(Date updated) {
        Calendar cal = Calendar.getInstance();
        if(updated == null) {
            cal.setTimeInMillis(0);
        } else {
            cal.setTime(updated);
        }
        this.setValue(cal);
    }
    public void setValue() {
        Calendar cal = Calendar.getInstance();
        this.setValue(cal);
    }
}
