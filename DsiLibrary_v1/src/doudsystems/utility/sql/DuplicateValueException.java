/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.utility.sql;

/**
 *
 * @author Owner
 */
public class DuplicateValueException extends Exception {
    public DuplicateValueException(String message, Exception innerException) {
        super(message, innerException);
    }
    public DuplicateValueException(String message) {
        super(message);
    }
    public DuplicateValueException() {
        super();
    }
}
