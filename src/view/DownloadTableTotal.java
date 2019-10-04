package view;

import control.DownloadManager;
import java.awt.Font;
import model.Downloader;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JProgressBar;
import javax.swing.table.AbstractTableModel;
import model.HttpDownloader;

/**
 * This class manages the download table's data.
 *
 */
public class DownloadTableTotal extends AbstractTableModel implements Observer {

    // These are the names for the table's columns.
    private static final String[] columnNames = {"File Name", "Size", "Progress", "Status", "Last Time"};

    // These are the classes for each column's values.
    @SuppressWarnings("rawtypes")
    private static final Class[] columnClasses = {String.class, String.class, String.class, String.class, String.class};
    
    /**
     * Load rows
     */
    /**
     * Add a new download to the table.
     */
    public void addNewDownload(HttpDownloader download) {
        // Register to be notified when the download changes.
        download.addObserver(this);

        // Fire table row insertion notification to table.
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    public void resumeDownload(HttpDownloader download) {
        // Register to be notified when the download changes.
        download.addObserver(this);
    }

    /**
     * Remove a download from the list.
     */
    public void clearDownload(int row) {
        // Fire table row deletion notification to table.
        fireTableRowsDeleted(row, row);
    }

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
        if (DownloadManager.getInstance().getDownloadList() != null) {
            return DownloadManager.getInstance().getDownloadList().size();
        }
        return 0;
    }

    /**
     * Get value for a specific row and column combination.
     */
    @Override
    public Object getValueAt(int row, int col) {
        // Get download from download list
        HttpDownloader download = DownloadManager.getInstance().getDownloadList().get(row);
        download.addObserver(this);

        switch (col) {
            case 0: // File Name
                return download.getFileName();
            case 1: // Size
                return DownloadManager.convertSize(download.getFileSize());
            case 2: // Progress
                return (download.getFileSize() == -1) ? "0%" : String.format("%.1f", download.getProgress()) + "%";
            case 3: // Status
                return HttpDownloader.STATUSES[download.getState()];
            case 4:
                return download.getLastTime();
        }
        return "";
    }

    /**
     * Update is called when a Download notifies its observers of any changes
     */
    @Override
    public void update(Observable o, Object arg) {
        int index = DownloadManager.getInstance().getDownloadList().indexOf(o);
        // Fire table row update notification to table.
        fireTableRowsUpdated(index, index);
    }
}
