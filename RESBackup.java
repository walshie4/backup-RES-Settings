/**
 * RESBackup.java
 * Written by: Adam Walsh (adw7422@rit.edu)
 * Maintained @ https://github.com/walshie4/backup-RES-Settings
 *
 * A simple program designed to search for installs of the
 * Reddit Enhancement Suite on the local system and make backups.
 * In the future it will also have the capability to restore
 * backups to different RES installs on browsers on the local
 * machine.
 */

public class RESBackup {
    private String os; /*String representation of the OS*/
    private String browsers[]; /*Contains String representations of
                              installed browsers on the local machine.*/
    /**
     * makeBackup -
     *     Makes a backup of specified RES installs
     */
    public void makeBackup() {
        //
    }
    /**
     * checkForRES -
     *     Checks for RES installs on the specified
     *     browsers.
     *
     * @return An array of strings representing browsers with RES installs.
     */
    public String[] checkForRES() {
        //
    }
    /**
     * detectOperatingSystem -
     *     Checks the local machine for the
     *     which operating system it is running.
     *
     * @return A string representation of the
     *         operating system on the local machine.
     */
    private String detectOperatingSystem() {
        //
    }
    /**
     * detectInstalledBrowsers - 
     *     Looks for installed browsers on the
     *     local machine.
     *
     * @param os - A string representation of the
     *             detected operating system on
     *             the local system.
     * @return An array containing string representations
     *         of installed browsers on the local machine.
     */
    private String[] detectInstalledBrowsers(String os) {
        //
    }
    /**
     * main - Runs the program
     *
     * @param args - Command-line arguments
     */
    public void static main(String[] args) {
        //do stuff
    }
}
