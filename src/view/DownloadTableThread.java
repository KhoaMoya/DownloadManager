/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;
import model.Downloader;

/**
 *
 * @author khoar
 */
public class DownloadTableThread extends AbstractTableModel implements Observer {

    // These are the names for the table's columns.
    private static final String[] columnNames = {"No", "Progress", "Status"};

    // These are the classes for each column's values.
    @SuppressWarnings("rawtypes")
    private static final Class[] columnClasses = {String.class, ProgressRenderer.class, String.class};

    private Downloader downloader;

    public DownloadTableThread(Downloader downloader) {
        this.downloader = downloader;
        this.downloader.addObserver(this);
    }

    /**
     * Load rows
     */
    /**
     * Get table's column count.
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Get a column's name.
     */
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /**
     * Get a column's class.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Class getColumnClass(int col) {
        return columnClasses[col];
    }

    /**
     * Get table's row count.
     */
    public int getRowCount() {
        return downloader.getListDownloadThread().size();
    }

    /**
     * Get value for a specific row and column combination.
     */
    @Override
    public Object getValueAt(int row, int col) {
        Downloader.DownloadThread thread = downloader.getListDownloadThread().get(row);
        switch (col) {
            // No
            case 0:
                return String.valueOf(row + 1);
            // Progress    
            case 1:
                return thread.getProgress();
            // State    
            case 2:
                return thread.isConnecting() ? "Connecting" : "Disconnected";
        }
        return "";
    }

    /**
     * Update is called when a Download notifies its observers of any changes
     */
    @Override
    public void update(Observable o, Object arg) {
        if (downloader.equals(o)) {
            for (int index = 0; index < downloader.getListDownloadThread().size(); index++) {
                fireTableRowsUpdated(index, index);
            }
        }
    }
}
