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
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Color;

public class RESBackupGUI implements Observer {
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
        this.os = new JLabel("Detected OS: " + model.getOS());
        this.scrollPane = new JScrollPane(this.table);
        win = new JFrame("RES Backup / Restore Client");
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = win.getContentPane();
        final RESBackup finalModel = this.model;
        JButton detectOS = new JButton("Detect OS");
        JButton detectRES = new JButton("Detect RES installs");
        JButton reset = new JButton("Reset");
        JButton about = new JButton("About");
        JPanel buttons = new JPanel(); //JPanel to hold buttons (at the bottom)
        buttons.setLayout(new FlowLayout(FlowLayout.RIGHT, 4, 4));
        final RESBackup mod = this.model; /**Final model to be used in listeners*/
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //show about window
            }
        });
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mod.reset();
            }
        });
        detectRES.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mod.detectRES();
            }
        });
        detectOS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mod.detectOperatingSystem();
            }
        });
        buttons.add(about);//add in order from right to left
        buttons.add(reset);
        buttons.add(detectRES);
        buttons.add(detectOS);
        win.setPreferredSize(new Dimension(600,500));
        pane.add(os, BorderLayout.NORTH);
        pane.add(buttons, BorderLayout.SOUTH);
        pane.add(this.scrollPane, BorderLayout.CENTER);
        win.pack();
        win.setVisible(true);
        win.validate();
    }
    /**
     * generateTable - create a new JTable to fit the data passed
     *
     * @param files - ArrayList of files to display
     *
     * @return JTable made to fit the data passed
     */
    private JTable generateTable(ArrayList<File> files) {
        DefaultTableModel dataForTable = new DefaultTableModel() { //2 col table
            public static final long serialVersionUID = 42L;
            public int getColumnCount() { return 2; }
            public boolean isCellEditable(int x, int y) { 
                return y == 0 && x != 0; } //not editable unless checkbox, not title
            public Class<?> getColumnClass(int col) {
                if (col == 0)
                    return Boolean.class; //checkbox
                return super.getColumnClass(col); } 
        };
        dataForTable.setColumnIdentifiers(new String[] {"Backup?", "File path"});
        for (File file : files) //add files w/ unchecked boxes
            dataForTable.addRow(new Object[] {false, file});
        JTable result = new JTable(dataForTable);
        result.getColumnModel().getColumn(0).setMaxWidth(80);
        result.setShowGrid(true);
        result.setGridColor(Color.BLACK);
        return result;
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
        this.os.setText("Detected OS: " + this.model.getOS());
        this.table = this.generateTable(this.model.getFoundFiles());
    }
    /**
     * Main - Runs the program
     *
     * @param args - Command-line arguments
     */
    public static void main(String[] args) {
        RESBackupGUI gui = new RESBackupGUI(new RESBackup());
    }
}
