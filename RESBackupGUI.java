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
import javax.swing.table.*;
import java.util.Observable;

public class RESBackupGUI<E> extends JFrame implements Observer {
    private JFrame win; /**Main window*/
    private RESBackup model; /**Model used with this view/control*/
    private JLabel os; /**Label to hold the found OS*/
    private JTable table; /**Table to hold data*/
    private JScrollPane scrollPane; /**Scroll pane which will hold the table*/
    /**
     * Constructor - Creates new RESBackupGUI object
     *
     * @param model Model to be used with this view/control
     *
     * @return new RESBackupGUI object
     */
    public RESBackupGUI(RESBackup model) {
        this.model = model;
        this.model.addObserver(this);
        this.table = generateTable(this.model.getFoundFiles());
        this.os = new JLabel(model.getOS());
        win = new JFrame();
    }
    /**
     * generateTable - create a new JTable to fit the data passed
     *
     * @param files - ArrayList of files to display
     *
     * @return JTable made to fit the data passed
     */
    private JTable generateTable(ArrayList<File> data) {
        final ArrayList<File> finalData = data;
        TableModel dataForTable = new AbstractTableModel() { //2 col table
            public int getColumnCount() { return 2; }
            public int getRowCount() { return finalData.size(); } //as big as data
            public Object getValueAt(int x, int y) {
                return finalData.get(y); } //return item in data at index y in column 2
            public boolean isCellEditable(int x, int y) { 
                return false; } //not editable
        };
        return new JTable(dataForTable);
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
        this.os.setText(this.model.getOS());
        this.table = this.generateTable(this.model.getFoundFiles());
    }
}
