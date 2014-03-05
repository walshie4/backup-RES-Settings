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
    private DefaultTableModel tableModel; /**Model for the table*/
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
        this.tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int col) {
                return row != 0 && col == 0; //editable if checkbox
            }
            public int getColumnCount() { return 2; }
            public Class<?> getColumnClass(int col) {
                if (col == 0)
                    return Boolean.class; //checkbox
                return super.getColumnClass(col); }
        };
        this.tableModel.setColumnIdentifiers(new String[] {"Backup?", "File Path"});
        this.table = new JTable();
        this.table.setModel(this.tableModel);
        this.table.setShowGrid(true);
        this.table.setGridColor(Color.BLACK);
        this.table.getColumnModel().getColumn(0).setMaxWidth(80);
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
     * updateTable - updates the table with the data passed
     *
     * @param files - ArrayList<File> of files to display
     */
    private void updateTable(ArrayList<File> files) {
        this.tableModel.setRowCount(files.size());
        int row = 0;
        for (File file : files) {
            model.setValueAt(false, row, 0);
            model.setValueAt(file, row, 1);
            row += 1;
        }
        this.table.setModel(this.tableModel);
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
        this.table.getTableModel();
        //need to update table in UI
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
