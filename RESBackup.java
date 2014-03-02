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

import java.io.File;
import java.util.ArrayList;

public class RESBackup {
    private String APPDATA; /*Holds path to users %APPDATA% dir*/
    private String HOME; /*Holds path to users home dir*/
    private final String CHROME_PATH_WIN78 = "/Local/Google/Chrome/"
        + "User Data/Default/Local Storage/chrome-extension_"
        + "kbmfpngjjgdllneeigpgjifpgocmfgmb_0.localstorage";
    private final String CHROMIUM_PATH_WIN78 = "/Local/Chromium/"
        + "User Data/Default/Local Storage/chrome-extension_"
        + "kbmfpngjjgdllneeigpgjifpgocmfgmb_0.localstorage";
    private final String CHROME_PATH_WINXP = "Local Settings/Application Data/Google"
        + "/Chrome/User Data/Default/Local Storage/chrome-extension_"
        + "kbmfpngjjgdllneeigpgjifpgocmfgmb_0.localstorage";
    private final String CHROME_PATH_OSX = "~/Library/Application Support/Google/"
        + "Chrome/Default/Local Storage/";
    private final String CHROMIUM_PATH_OSX = "~/Library/Application Support/"
        + "Chromium/Default";
    private final String CHROME_PATH_LINUX = "~/.config/google-chrome/Default/Local "
        + "Storage/chrome-extension_kbmfpngjjgdllneeigpgjifpgocmfgmb_0.localstorage";
    private String os; /*String representation of the OS*/
    private ArrayList<File> RES; /*Contains File object representations of
                            installed browsers with RES installed as well.*/
    /**
     * RESBackup -
     *     Constructor for RESBackup object
     *
     * @return A new RESBackup object
     */
    public RESBackup() {
        this.RES = new ArrayList<File>();
        this.APPDATA = System.getenv("APPDATA");
        this.HOME = System.getenv("user.home");
        if (this.HOME == null) //if null
            this.HOME = System.getProperty("user.home"); //use backup way
    }
    /**
     * makeBackup -
     *     Makes a backup of specified RES installs
     */
    private void makeBackup() {
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
    private void checkForRES() {
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
     * detectRES - 
     *     Looks for installed versions of RES on the
     *     local machine.
     *
     * Sets RES to an array containing File objects
     *      pointing to installed RES settings files
     */
    private void detectRES() throws NotSupportedException{
        if (this.os == null)
            throw new NotSupportedException("Cannot detect installed browsers"
                    + " without first running detectOperatingSystem()");
        switch(this.os) {
        case "Windows 7":
        case "Windows 8":
            if (this.APPDATA == null)
                System.out.println("The APPDATA variable is null, because of this"
                        + " finding installs on WIN 7/8 is impossible.");
            else {
                File chrome78 = new File(this.APPDATA + this.CHROME_PATH_WIN78);
                if (chrome78.exists())
                    this.RES.add(chrome78);
                File chromium78 = new File(this.APPDATA + this.CHROMIUM_PATH_WIN78);
                if (chromium78.exists())
                    this.RES.add(chromium78);
                //look for firefox install
            }
            break;
        case "Windows XP":
            if (this.HOME == null)
                System.out.println("The HOME variable is null, because of this"
                        + " finding installs on WIN XP is impossible.");
            else {
                File chromeXP = new File(this.HOME + this.CHROME_PATH_WINXP);
                if (chromeXP.exists())
                    this.RES.add(chromeXP);
            }
            break;
        case "Mac OS X":
            File chromeOSX = new File(this.CHROME_PATH_OSX);
            if (chromeOSX.exists())
                this.RES.add(chromeOSX);
            File chromiumOSX = new File(this.CHROMIUM_PATH_OSX);
            if (chromiumOSX.exists())
                this.RES.add(chromiumOSX);
            //look for firefox install
            //look for safari install
            break;
        case "Linux":
            File chromeLinux = new File(this.CHROME_PATH_LINUX);
            if (chromeLinux.exists())
                this.RES.add(chromeLinux);
            //look for firefox install
            break;
        default:
            throw new NotSupportedException("Your OS isn't supported! Please report"
                    + "this issue on the github project page. Thanks! :)");
        }
    }
    /**
     * main - Runs the program
     *
     * @param args - Command-line arguments
     */
    public static void main(String[] args) {
        RESBackup backup = new RESBackup();
    }
}
