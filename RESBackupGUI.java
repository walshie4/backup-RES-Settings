/**
 * RESBackupGUI.java
 * Written by: Adam Walsh
 * Maintained @ https://github.com/walshie4/backup-RES-Settings
 *
 * View and Control for RESBackup.java (Model)
 */

import javax.swing.*;
import java.util.ArrayList;
import java.io.File;
import javax.swing.table.*;
import java.util.Observable;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Desktop;
import java.net.URI;
import java.io.IOException;
import java.net.URISyntaxException;

public class RESBackupGUI {
    private JFrame win; /**Main window*/
    private RESBackupController control; /**Control to be used with this view*/
    private JLabel os; /**Label to hold the found OS*/
    private DefaultTableModel tableModel; /**Model for the table*/
    private JTable table; /**Table to hold data*/
    private JScrollPane scrollPane; /**Scroll pane which will hold the table*/
    /** Buttons */
    private JButton detectOS;
    private JButton about;
    private JButton detectRES;
    private JButton reset;
    private JButton backup;
    /**
     * Constructor - Creates new RESBackupGUI object
     *
     * @param control Controller to be used with this view 
     *
     * @return new RESBackupGUI object
     */
    public RESBackupGUI(RESBackupController control) {
        this.control = control;
        this.tableModel = new DefaultTableModel() {
            private static final long serialVersionUID=42L;
            public boolean isCellEditable(int row, int col) {
                return col == 0; //editable if checkbox
            }
            public int getColumnCount() { return 3; }
            public Class<?> getColumnClass(int col) {
                if (col == 0)
                    return Boolean.class; //checkbox
                return super.getColumnClass(col); }
        };
        this.tableModel.setColumnIdentifiers(new String[] {"Backup?", "Browser", "File Path"});
        this.table = new JTable();
        this.table.setModel(this.tableModel);
        this.table.setShowGrid(true);
        this.table.setGridColor(Color.BLACK);
        this.table.getColumnModel().getColumn(0).setMaxWidth(50);
        this.table.getColumnModel().getColumn(1).setMaxWidth(80);
        this.os = new JLabel("Detected OS: ");
        this.scrollPane = new JScrollPane(this.table);
        win = new JFrame("RES Backup / Restore Client");
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = win.getContentPane();
        this.detectOS = new JButton("Detect OS");
        this.detectRES = new JButton("Detect RES installs");
        this.reset = new JButton("Reset");
        this.about = new JButton("About");
        this.backup = new JButton("Make Backup");
        JPanel buttons = new JPanel(); //JPanel to hold buttons (at the bottom)
        buttons.setLayout(new FlowLayout(FlowLayout.RIGHT, 4, 4));
        buttons.add(about);//add in order from right to left
        buttons.add(reset);
        buttons.add(detectRES);
        buttons.add(detectOS);
        buttons.add(backup);
        win.setPreferredSize(new Dimension(1200,500));
        pane.add(os, BorderLayout.NORTH);
        pane.add(buttons, BorderLayout.SOUTH);
        pane.add(this.scrollPane, BorderLayout.CENTER);
        win.pack();
        win.setVisible(true);
        win.validate();
    }
    /**
     * addActionListeners - add actionlisteners to the buttons
     */
    public void addActionListeners() {
        this.about.addActionListener(this.control.aboutBtn());
        this.reset.addActionListener(this.control.resetBtn());
        this.detectRES.addActionListener(this.control.detectRESBtn());
        this.detectOS.addActionListener(this.control.detectOSBtn());
        this.backup.addActionListener(this.control.backupBtn());
    }
    /**
     * updateTable - updates the table with the data passed
     *
     * @param files - ArrayList<File> of files to display
     *
     * @param browsers - Arraylist<String> of browser names
     */
    public void updateTable(ArrayList<File> files, ArrayList<String> browsers) {
        int size = files.size();
        this.tableModel.setRowCount(size);
        int row = 0;
        for (int i = 0; i < size; i++) {
            tableModel.setValueAt(false, row, 0);
            tableModel.setValueAt(browsers.get(i), row, 1);
            tableModel.setValueAt(files.get(i), row, 2);
            row += 1;
        }
        this.table.setModel(this.tableModel);
    }
    /**
     * getSelected - gets the index of which File's have been selected via checkbox
     *
     * @return ArrayList of ints representing indexes of selected files
     */
    public ArrayList<Integer> getSelected() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(int i = 0; i < tableModel.getRowCount(); i++) {
            if(this.tableModel.getValueAt(i, 0) == true)//if selected
                result.add(i);
        }
        return result;
    }
    /**
     * setOS - set the OS output to be what it should
     * 
     * @param os - String representation of the detected OS
     */
    public void setOS(String os) {
    	this.os.setText("Detected OS: " + os);
    }
    /**
     * aboutWindow - returns a JFrame with information about the project
     *
     * @return JFrame instance with information about the project
     */
    public JFrame aboutWindow() {
        JFrame about = new JFrame("About this project");
        JLabel description = new JLabel();
        JButton projectLink = new JButton();
        try {
            final URI projectURI = new URI("https://github.com/walshie4/"
                    +"backup-RES-Settings");
            projectLink.setText(projectURI.toString());
            projectLink.setBorderPainted(false);
            projectLink.setToolTipText(projectURI.toString());
            projectLink.addActionListener(this.control.projectLink(projectURI));
            Container pane = about.getContentPane();
            pane.add(projectLink);
        }
        catch (URISyntaxException syntax) {
            System.err.println(syntax.getMessage());
        }
        about.setVisible(true);
        about.setSize(400,400);
        return about;
    }
}
