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
    private String RES[]; /*Contains String representations of installed
                            browsers with RES installed as well.*/
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
     * Sets RES to an array of strings representing 
     *     browsers with RES installs.
     */
    public void checkForRES() {
        //
    }
    /**
     * detectOperatingSystem -
     *     Checks the local machine for the
     *     which operating system it is running.
     *
     * Sets os to a string representation of the
     *      operating system on the local machine.
     */
    private void detectOperatingSystem() {
        os = System.getProperty("os.name").toLowerCase();
    }
    /**
     * detectInstalledBrowsers - 
     *     Looks for installed browsers on the
     *     local machine.
     *
     * Sets browsers to an array containing string representations
     *     of installed browsers on the local machine.
     */
    private void detectInstalledBrowsers() {
        if (this.os == null)
            throw new Exception("Cannot detect installed browsers"
                    + " without first running detectOperatingSystem()");
        switch(this.os) {
        case "win":
            //do windows stuff
            break;
        case "mac":
            //do mac stuff
            break;
        case "nix":
        case "nux":
        case "aix":
            //do linux stuff
            break;
        default:
            throw new Exception("Your OS isn't supported! Please report this issue"
                    + " on the github project page. Thanks! :)");
        }
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
