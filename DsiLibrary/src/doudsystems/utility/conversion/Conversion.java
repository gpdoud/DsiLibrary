/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.utility.conversion;

import java.util.Date;
import java.util.Calendar;

/**
 *
 * @author Owner
 */
public class Conversion {

    static String quote = "'";

    public static int parse(Boolean b) {
        return ((b) ? 1 : 0);
    }
    public static String getStringForDb(String str) {
        if(str == null) {
            return "NULL";
        } else {
            return String.format("'%s'", str);
        }
    }
    public static String getDateForDb(Calendar cal) {
        if(cal == null) {
            return "NULL";
        } else {
            return String.format("'%4d-%02d-%02d %02d:%02d:%02d.%02d'",
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    cal.get(Calendar.SECOND),
                    cal.get(Calendar.MILLISECOND));
        }
    }
    public static java.util.Calendar parse(Date date) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
