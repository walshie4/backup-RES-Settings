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
import java.util.Observable;

public class RESBackup extends Observable {
    private String APPDATA; /*Holds path to users %APPDATA% dir*/
    private String LOCALAPPDATA; /*Holds path to users %LOCALAPPDATA% dir*/
    private String HOME; /*Holds path to users home dir*/
    private final String CHROME_PATH_WIN78 = "/Google/Chrome/"
            + "User Data/Default/Local Storage/chrome-extension_"
            + "kbmfpngjjgdllneeigpgjifpgocmfgmb_0.localstorage";
    private final String CHROMIUM_PATH_WIN78 = "/Chromium/"
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
            + "Firefox/";
    private final String FF_PROFILE_WIN78 = "/Mozilla/Firefox/";
    private final String FF_PROFILE_LINUX = "~/.mozilla/firefox/";
    private final String FF_PROFILE_SUFFIX = "/jetpack/jid1-xUfzOsOFlzSOXg@jetpack"
            + "/simple-storage/store.json";
    private final String SAFARI_FILE_HEAD = "safari-extension_com.honestbleeps."
            + "redditenhancementsuite-";
    private String os; /*String representation of the OS*/
    private ArrayList<File> RES; /*Contains File object representations of
                            found RES settings files*/
    /**
     * RESBackup -
     *     Constructor for RESBackup object
     *
     * @return A new RESBackup object
     */
    public RESBackup() {
        this.RES = new ArrayList<File>();
        this.APPDATA = System.getenv("APPDATA");
        this.LOCALAPPDATA = System.getenv("LOCALAPPDATA");
        this.HOME = System.getenv("user.home");
        if (this.HOME == null) //if null
            this.HOME = System.getProperty("user.home"); //use backup way
    }
    /**
     * makeBackup -
     *     Makes a backup of the files passed
     *
     * @param filesToBackup - an arraylist of file objects pointing to
     *                        found RES settings files that the user has
     *                        elected to include in the backup.
     */
    public void makeBackup(ArrayList<File> filesToBackup) {
        //
    }
    /**
     * getOS - returns the internal string holding the detected OS value
     *
     * @return a String representation of the OS on the current machine, or
     *         null if the OS has not yet been detected.
     */
    public String getOS() {
        return this.os;
    }
    /**
     * getFoundFiles - returns an arrayList of File objects that point to the found
     *                 RES settings files
     *
     * @return ArrayList of File objects that refer to the known RES settings files
     *         on the local machine.
     */
    public ArrayList<File> getFoundFiles() {
        return this.RES;
    }
    /**
     * detectOperatingSystem -
     *     Checks the local machine for the
     *     which operating system it is running.
     *
     * Sets os to a string representation of the
     *      operating system on the local machine.
     */
    public void detectOperatingSystem() {
        this.os = System.getProperty("os.name").toLowerCase();
        setChanged();
        notifyObservers();
    }
    /**
     * reset - resets the instance variables and UI
     */
    public void reset() {
        this.os = null;
        this.RES = new ArrayList<File>();
        setChanged();
        notifyObservers();
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
    public void detectRES() throws UnsupportedOperationException{
        this.RES = new ArrayList<File>(); //reset the list contents
        detectOperatingSystem();
        switch(this.os) {
        case "windows 7":
        case "windows 8":
        case "windows 8.1":
            if (this.APPDATA == null && this.LOCALAPPDATA == null)
                throw new UnsupportedOperationException("The APPDATA and "
                        + "LOCALAPPDATA variable is null, because of this"
                        + "finding installs on WIN 7/8 is impossible.");
            if (this.APPDATA != null && this.LOCALAPPDATA != null) {
                detectOnWindows(this.APPDATA);
                detectOnWindows(this.LOCALAPPDATA);
            }
            else if (this.APPDATA != null) 
                detectOnWindows(this.APPDATA);
            else 
                detectOnWindows(this.LOCALAPPDATA);
            break;
        case "windows xp":
            if (this.HOME == null)
                throw new UnsupportedOperationException("The HOME variable is null, "
                        + "because of this finding installs on WIN XP is "
                        + "impossible.");
            File chromeXP = new File(this.HOME + this.CHROME_PATH_WINXP);
            if (chromeXP.exists())
                this.RES.add(chromeXP);
            findOperaWindows();
            break;
        case "mac os x":
            if (this.HOME == null)
                throw new UnsupportedOperationException("The HOME variable is null, "
                        + "because of this finding installs on MAC OS X is "
                        + "impossible.");
            String chromePathOSX = this.CHROME_PATH_OSX.replace("~", this.HOME);
            File chromeOSX = new File(chromePathOSX);
            if (chromeOSX.exists())
                this.RES.add(chromeOSX);
            String chromium = this.CHROMIUM_PATH_OSX.replace("~", this.HOME);
            File chromiumOSX = new File(chromium);
            if (chromiumOSX.exists())
                this.RES.add(chromiumOSX);
            findFirefoxProfile();
            findSafariOSX();
            break;
        case "linux":
            if (this.HOME == null)
                throw new UnsupportedOperationException("User's home directory "
                        + "cannot be found, because of this it is impossible to "
                        + "find chrome's RES file");
            String chromePathLinux = this.CHROME_PATH_LINUX.replace("~", this.HOME);
            File chromeLinux = new File(chromePathLinux);
            if (chromeLinux.exists())
                this.RES.add(chromeLinux);
            findFirefoxProfile();
            break;
        default:
            throw new UnsupportedOperationException("Your OS isn't supported! Please report"
                    + " this issue on the github project page. Thanks! :)");
        }
        setChanged();
        notifyObservers();
    }
    /**
     * detectOnWindows - 
     *     Finds windows RES installs based on appDataPath specified
     *
     * @param appDataPath - appDataPath to look inside of
     *
     * @exception UnsupportedOperationException - thrown if user home property is null
     */
    private void detectOnWindows(String appDataPath) {
        File chrome78 = new File(appDataPath + this.CHROME_PATH_WIN78);
        if (chrome78.exists())
            this.RES.add(chrome78);
        File chromium78 = new File(appDataPath + this.CHROMIUM_PATH_WIN78);
        if (chromium78.exists())
            this.RES.add(chromium78);
        findFirefoxProfile();
        if (this.HOME == null)
        throw new UnsupportedOperationException("The user home directory "
                    + "system property is null, beacuse of this finding Opera "
                    + "on windows is impossible.");
        findOperaWindows();
    }
    /**
     * findOperaWindows -
     *     Finds the Opera RES settings file, and adds it to the local RES ArrayList
     */
    private void findOperaWindows() {
        //find windows opera file
    }
    /**
     * findSafariOSX - 
     *     Finds the Safrai RES settings file, and adds it to the local RES ArrayList
     */
    private void findSafariOSX() {
        String path = "~/Library/Safari/LocalStorage/";
        File settings = findSafariFile(path.replace("~", this.HOME));
        if (settings.exists())
            this.RES.add(settings);
    }
    /**
     * findSafariFile -
     *     Finds the Safari RES settings file based off the start of the file name
     *
     * @param path - String representing the path to where the RES settings file
     *               should be able to be found
     *
     * @return File object pointing to the RES settings file
     *
     * @exception UnsupportedOperationException - thrown if the Safari RES settings
     *                                            file cannot be found
     */
    private File findSafariFile(String path) throws UnsupportedOperationException {
        File folder = new File(path);
        File[] possibles = folder.listFiles();
        for (File possible : possibles) {
            if (possible.getName().startsWith(this.SAFARI_FILE_HEAD))
                return possible;
        }
        throw new UnsupportedOperationException("Safari RES settings file could not"
                + " be found. :(");
    }
    /**
     * findFirefoxProfile -
     *     Finds the Firefox Profile folder, then calls findRESFile to
     *     find the RES file inside the found Firefox Profile folder.
     *     Finally, if a RES file is found it will be added to the
     *     local RES arraylist.
     *
     * @exception UnsupportedOperationException - thrown when you OS is one
     *                                            that does not have a known
     *                                            Firefox profiles location
     */
    private void findFirefoxProfile() throws UnsupportedOperationException {
        switch(this.os) {
        case "Windows 7":
        case "Windows 8":
            File profileWin78 = new File(this.APPDATA + this.FF_PROFILE_WIN78);
            if (profileWin78.exists()) 
                findRESFile(profileWin78);
            break;
        case "Windows XP":
            throw new UnsupportedOperationException("The Firefox profile folder"
                    + " location is not listed on the RES backup page. If you are "
                    + "encountering this error and know the location of the "
                    + "Firefox profile folder on Win. XP please submit it, "
                    + "along with an issue report to have this fixed. Thanks!");
        case "Mac OS X":
        case "mac os x":
            String path = this.FF_PROFILE_MAC.replace("~", this.HOME);
            File profileMac = new File(path + "profiles.ini");
            if (profileMac.exists())
                findRESFile(new File(path));
            String altPath = this.FF_PROFILE_MAC_ALT.replace("~", this.HOME);
            File profileMacAlt = new File(altPath + "profiles.ini");
            if (profileMacAlt.exists())
                findRESFile(new File(altPath));
            break;
        case "Linux":
        case "linux":
            String firefox = FF_PROFILE_LINUX.replace("~", this.HOME);
            File profileLinux = new File(firefox);
            if (profileLinux.exists())
                findRESFile(profileLinux);
            break;
        default:
            //this should never run, unless called out of order or switch does not
            //match switch in detectRES()
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
        //build a hashtable of <String names, File profile> from the profiles.ini file
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
     *      profile, and fills a hashtable of profile names to File object
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
        BufferedReader reader = null;
        try {
            //read each line in 'profiles.ini'
            reader = new BufferedReader(new FileReader(profileInfo));
            String line = null;
            String name = ""; //will hold name of section in 'profiles.ini' file
            boolean building = false; //true when still reading info on one section
            boolean relativePath = false; //true if path for current profile being
                    // built is relative to dir containing the profiles.ini file
            File profile = null; //used to hold the profile file during building
            while ((line = reader.readLine()) != null) {
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
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        return profiles;
    }
    /**
     * main - Runs the program (debugging only)
     *
     * @param args - Command-line arguments
     */
    public static void main(String[] args) {
        RESBackup backup = new RESBackup();
        backup.detectRES();
        for (File found : backup.getFoundFiles())
            System.out.println(found.getAbsolutePath());
    }
}
