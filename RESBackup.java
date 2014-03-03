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
import java.lang.UnsupportedOperationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

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
    private final String FF_PROFILE_MAC = "~/Library/Mozilla/Firefox/Profiles/";
    private final String FF_PROFILE_MAC_ALT = "~/Library/Application Support/"
        + "Firefox/Profiles/";
    private final String FF_PROFILE_WIN78 = "/Mozilla/Firefox/Profiles/";
    private final String FF_PROFILE_LINUX = "~/.mozilla/firefox/";
    private final String FF_PROFILE_SUFFIX = "/jetpack/jid1-xUfzOsOFlzSOXg@jetpack"
        + "/simple-storage/store.json";
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
     * detectOperatingSystem -
     *     Checks the local machine for the
     *     which operating system it is running.
     *
     * Sets os to a string representation of the
     *      operating system on the local machine.
     */
    private void detectOperatingSystem() {
        this.os = System.getProperty("os.name").toLowerCase();
    }
    /**
     * detectRES - 
     *     Looks for installed versions of RES on the
     *     local machine.
     *
     * Sets RES to an array containing File objects
     *      pointing to installed RES settings files
     *
     * @exception UnsupportedOperationException - thrown when the program encounters
     *                                   a scenario it knows it does not yet support.
     */
    private void detectRES() throws UnsupportedOperationException{
        detectOperatingSystem();
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
            throw new UnsupportedOperationException("Your OS isn't supported! Please report"
                    + "this issue on the github project page. Thanks! :)");
        }
    }
    /**
     * findFirefoxProfile -
     *     Finds the Firefox Profile folder, then calls findRESFile to
     *     find the RES file inside the found Firefox Profile folder.
     *     Finally, if a RES file is found it will be added to the
     *     local RES arraylist.
     */
    private void findFirefoxProfile() throws UnsupportedOperationException {
        switch(this.os) {
        case "Windows 7":
        case "Windows 8":
            if (this.APPDATA == null)
                throw new UnsupportedOperationException("The APPDATA variable is set"
                        + " to null, because of this the Firefox profile folder "
                        + "cannot be found on WIN 7/8");
            else {
                File profileWin78 = new File(this.APPDATA + this.FF_PROFILE_WIN78);
                if (profileWin78.exists()) 
                    findRESFile(profileWin78);
            }
            break;
        case "Windows XP":
            throw new UnsupportedOperationException("The Firefox profile folder"
                    + " location is not listed on the RES backup page. If you are "
                    + "encountering this error and know the location of the "
                    + "Firefox profile folder on Win. XP please submit it, "
                    + "along with an issue report to have this fixed. Thanks!");
        case "Mac OS X":
            File profileMac = new File(this.FF_PROFILE_MAC);
            if (profileMac.exists())
                findRESFile(profileMac);
            File profileMacAlt = new File(this.FF_PROFILE_MAC_ALT);
            if (profileMacAlt.exists())
                findRESFile(profileMacAlt);
            break;
        case "Linux":
            File profileLinux = new File(this.FF_PROFILE_LINUX);
            break;
        default:
            //this should never run, unless called out of order
        }
    }
    /**
     * findRESFile - 
     *      looks through the passed directory for the RES settings file
     *      and adds it to the RES arraylist if the settings file found exists
     *
     * @param profileDir - File object which points to the directory which
     *                     houses the Firefox Profile Folder on the local
     *                     machine.
     */
    private void findRESFile(File profileDir) {
        Hashtable<String, File> profiles = getProfiles(new File(profileDir, "profiles.ini"));
        Set<String> keys = profiles.keySet();
        Iterator<String> it = keys.iterator();
        while(it.hasNext()) {
            //Create a file object based off the object corresponding to the current key
            //as well as the common file path suffix for Firefox settings files
            File settings = new File(profiles.get(it.next()), FF_PROFILE_SUFFIX); 
            if (settings.exists())
                this.RES.add(settings);
        }
    }
    /**
     * getProfiles -
     *      looks through passed 'profiles.ini' file for lines that denote a new
     *      profile, and fills a hashtable of profile names ==> File object
     *      referring to the profile dir corresponding to the profile name
     *
     * @param profilesInfo - File object referring to the 'profiles.ini' file
     *                       found in the same dir as Firefox profile dirs
     *
     * @return HashTable<String name, File profile> - filled with all found profiles
     *                                                in passed directory
     */
    private Hashtable<String, File> getProfiles(File profileInfo) {
        Hashtable<String, File> profiles = new Hashtable<String, File>();
        try {
            //read each line in 'profiles.ini'
            BufferedReader reader = new BufferedReader(new FileReader(profileInfo));
            String line = null;
            String name = ""; //will hold name of section in 'profiles.ini' file
            boolean building = false; //true when still reading info on one section
            boolean relativePath = false; //true if path for current profile being
                    // built is relative to dir containing the profiles.ini file
            File profile = null; //used to hold the profile file during building
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (building) { //building a Hashtable entry
                    if (line.equals("")) { //empty (signals building is over)
                        profiles.put(name, profile);
                        building = false; //reset variables used on a loop level
                        name = ""; 
                        profile = null;
                    }
                    else if (line.length() >= 5 && line.substring(0,5).equals("Name="))
                        name = line.substring(5,line.length());
                    else if (line.length() >= 11 && line.substring(0, 11).equals("IsRelative=")) {
                        if (line.substring(11,line.length()).equals("1"))
                            relativePath = true;
                        else
                            relativePath = false;
                    }
                    else if (line.length() >= 5 && line.substring(0,5).equals("Path=")) {
                        if (relativePath) //build path off parent of info file
                            profile = new File(profileInfo.getParentFile(),
                                                line.substring(5,line.length()));
                        else //custom path
                            profile = new File(line.substring(5,line.length()));
                    }//else skip line
                }
                else { //analyze line
                    if (line.length() == 0) {} //skip line
                    else {
                        char[] chars = line.toCharArray();
                        //if the line starts with '[' and ends with ']'
                        if ((chars[0] == '[') && (chars[chars.length-1] == ']')) {
                            name = line.substring(1, line.length() - 1);
                            if (name.equals("General"))
                                building = false;
                            else
                                building = true;
                        }
                        else { //not the start of a section and not building
                            ;//do nothing
                        }
                    }
                }
            }
            if (profile != null) //last profile has not been added (because of EOF)
                profiles.put(name, profile);
        }
        catch(IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return profiles;
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
