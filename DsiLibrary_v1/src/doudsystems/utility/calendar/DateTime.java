/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.utility.calendar;

import java.util.Calendar;

/**
 *
 * @author Owner
 */
public class DateTime {

    public static String getDateForDb(Calendar cal) {
        return String.format("%4d-%02d-%02d %02d:%02d:%02d.%02d",
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                cal.get(Calendar.SECOND),
                cal.get(Calendar.MILLISECOND));
    }
}
