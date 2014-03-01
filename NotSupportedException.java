/**
 * NotSupportedException.java
 * Written by: Adam Walsh (adw7422@rit.edu)
 * Maintained @ https://github.com/walshie4/backup-RES-Settings
 * 
 * An exception to be thrown in cases when RESBackup
 * does not support certain parameters it detects.
 */

import java.lang.Exception;

public class NotSupportedException extends Exception {
    /**
     * NotSupportedException - NotSupportedException constructor
     *
     * @param msg - Message to be associated with the exception
     *
     * @return New NotSupportedException object
     */
    public NotSupportedException(String msg) {
        super(msg);
    }
}
