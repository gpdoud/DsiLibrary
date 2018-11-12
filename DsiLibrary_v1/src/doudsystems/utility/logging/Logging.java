/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.utility.logging;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Owner
 */
public class Logging {

    public enum Severity { Debug, Info, Warning, Error, Fatal, All };
    private Severity severity = Severity.Info;
    private int msgNbr = 0;
    private Boolean displayDate = true;
    public Boolean getDisplayDate() { return this.displayDate; }
    public void setDisplayDate(Boolean displayDate) { this.displayDate = displayDate; }

    public void debug(Object message)   { log(message, Severity.Debug); }
    public void info(Object message)    { log(message, Severity.Info); }
    public void warning(Object message) { log(message, Severity.Warning); }
    public void error(Object message)   { log(message, Severity.Error); }
    public void fatal(Object message)   { log(message, Severity.Fatal); }
    public void all(Object message)     { log(message, Severity.All); }

    public void log(Object message, Severity severity) {
        PrintStream ps = System.out;
        Date now = new Date();
        String strNow = "";
        if(this.displayDate) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmssSSS ");
            strNow = sf.format(now);
        }
        String sev = severity.toString().substring(0, 1);
        if(severity.compareTo(this.severity) >= 0 ) {
            if(severity == Severity.Debug) {
                ps = System.err;
            }
            ps.format("%05d %s%s %s\n", ++this.msgNbr, strNow, sev, message.toString());
        }
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Logging() {
        this.setSeverity(Severity.Info);
    }
}
