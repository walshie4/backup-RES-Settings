/**
 * RESBackupController.java
 * Written by: Adam Walsh (adw7422@rit.edu)
 * Maintained @ https://github.com/walshie4/backup-RES-Settings
 *
 * Controller for the MVC design
 */

import java.util.Observer;
import java.util.Observable;
import java.awt.event.*;
import java.net.URI;
import java.awt.Desktop;
import java.io.IOException;
import javax.swing.JOptionPane;

public class RESBackupController implements Observer {
	private RESBackup model;
	private RESBackupGUI view;
    /**
     * Constructor - Creates a new controller object
     *
     * @return new RESBackupController object
     */
    public RESBackupController() {
    	this.model = new RESBackup(this);
    	this.model.addObserver(this);
    }
    /**
     * buildView - Builds and shows the View
     */
    public void buildView() {
    	this.view = new RESBackupGUI(this);
        this.view.addActionListeners();
    }
    /**
     * aboutBtn - returns ActionListener for the about button
     * 
     * @return the Actionlistener for this button
     */
    public ActionListener aboutBtn() {
    	final RESBackupGUI v = this.view;
    	return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                v.aboutWindow();
            }
        };
    }
    /**
     * resetBtn - returns ActionListener for the reset button
     * 
     * @return the ActionListener for this button
     */
    public ActionListener resetBtn() {
    	final RESBackup mod = this.model;
    	return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mod.reset();
            }
        };
    }
    /**
     * detectRESBtn - returns actionlistener for the detectRES button
     * 
     * @return the ActionListener for this button
     */
    public ActionListener detectRESBtn() {
    	final RESBackup mod = this.model;
    	final RESBackupGUI v = this.view;
    	return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    mod.detectRES();
                }
                catch (UnsupportedOperationException exception) {
                    showAlert(exception.getMessage());
                }
            }
        };
    }
    /**
     * detectOSBtn - returns actionListener for the detectOS button
     * 
     * @return the ActionListener for this button
     */
    public ActionListener detectOSBtn() {
    	final RESBackup mod = this.model;
    	return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mod.detectOperatingSystem();
            }
        };
    }
    /**
     * backupBtn - returns the actionlistner for the backup button
     * 
     * @return the ActionListener for this button
     */
    public ActionListener backupBtn() {
        final RESBackup mod = this.model;
        final RESBackupGUI v = this.view;
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    mod.makeBackup(v.getSelected());
                }
                catch (Exception exception) {
                    showAlert(exception.getMessage());
                }
            }
        };
    }
    /**
     * projectLink - returns the actionlistener for clicking the project link
     * 
     * @param URI for the project page
     * 
     * @return the ActionListener to open the project page
     */
    public ActionListener projectLink(URI projURI) {
    	final URI projectURI = projURI;
    	return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(projectURI);
                    }
                    catch(IOException error) {
                        showAlert(error.getMessage());
                    }
                }
                else {
                    showAlert("Desktop is not supported! Link cannot be"
                        + "opened.");
                }
            }
        };
    }
    /**
     * update - update the view
     *
     * @param t - unused
     * @param o - unused
     */
    public void update(Observable t, Object o) {
        this.view.setOS(this.model.getOS());
        this.view.updateTable(this.model.getFoundFiles(), this.model.getFoundBrowsers());
    }
    /**
     * showAlert - tells the view to show the user an alert
     *
     * @param message - message to be shown
     */
    public void showAlert(String message) {
       this.view.showAlert(message);
    }
    /**
     * getResponse - asks question to user and returns result
     *
     * @param message - message to be shown
     * 
     * @return True if yes, else False
     */
    public boolean getResponse(String message) {
        int resp = JOptionPane.showConfirmDialog(null, message, "Warning",
                JOptionPane.YES_NO_OPTION);
        return resp == JOptionPane.YES_OPTION;
    }
    /**
     * Main - Runs the program
     *
     * @param args - Command-line arguments
     */
    public static void main(String[] args) {
        RESBackupController controller = new RESBackupController();
        controller.buildView();
    }
}

