/**
 * RESBackupGUI.java
 * Written by: Adam Walsh
 * Maintained @ https://github.com/walshie4/backup-RES-Settings
 *
 * View and Control for RESBackup.java (Model)
 */

import javax.swing.*;
import java.awt.event.*;
import java.util.Observer;
import java.util.ArrayList;
import java.io.File;

public class RESBackupGUI extends JFrame implements Observer {
    private JFrame win; /**Main window*/
    private RESBackup model; /**Model used with this view/control*/
    private ArrayList<File> files; /**Used to hold the files being displayed*/
    /**
     * Constructor - Creates new RESBackupGUI object
     *
     * @param model Model to be used with this view/control
     *
     * @return new RESBackupGUI object
     */
    public RESBackupGUI(RESBackup model) {
        this.model = model;
    }
    /**
     * Update - Updates UI components which have had their data
     *          changed in the model
     *
     * @param t - unused
     * @param o - unused
     *
     */
    public void update(Observable t, Object o) {
        this.osTextField = this.model.getOS();
        this.files = this.model.getFoundFiles();
    }
}
