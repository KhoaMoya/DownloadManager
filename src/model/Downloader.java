package model;

import control.ConfigurationManager;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.DownloadManagerGUI;

public abstract class Downloader extends Observable implements Runnable, Serializable {

    // Member variables
    /**
     * The URL to download the file
     */
    protected URL mURL;

    /**
     * Output folder for downloaded file
     */
    protected String mOutputFolder;

    /**
     * Number of connections (threads) to download the file
     */
    protected int mNumConnections;

    /**
     * The file name, extracted from URL
     */
    protected String mFileName;

    /**
     * Size of the downloaded file (in bytes)
     */
    protected long mFileSize;

    /**
     * The state of the download
     */
    protected int mState;

    /**
     * downloaded size of the file (in bytes)
     */
    protected long mDownloaded;

    /**
     * Time download
     */
    protected long mTime;

    /**
     * Last time
     */
    protected String mLastTime;

    /**
     * FileType of download file
     */
    protected FileType mFileType;

    /**
     * Log file
     */
    protected StringBuilder log;

    /**
     * number thread
     */
    protected int mNumberThread;

    /**
     * Transfer rate
     */
    protected long mTransferRate;
    protected long mTempTranf;

    // Contants for block and buffer size
    protected int mBlockSize;
    protected int mBufferSize;
    protected int mMinDownloadSize;
    /**
     * List of download threads
     */
    protected ArrayList<DownloadThread> mListDownloadThread;

    // These are the status names.
    public static final String STATUSES[] = {"Downloading", "Paused", "Completed", "Cancelled", "Error"};

    // Contants for download's state
    public static final int DOWNLOADING = 0;
    public static final int PAUSED = 1;
    public static final int COMPLETED = 2;
    public static final int CANCELLED = 3;
    public static final int ERROR = 4;

    /**
     * Constructor
     *
     * @param fileURL
     * @param outputFolder
     * @param numConnections
     */
    protected Downloader(URL url, String outputFolder, int numConnections) {
        mURL = url;
        mOutputFolder = outputFolder;
        mNumConnections = numConnections;

        // Get the file name from url path
        String fileURL = url.getFile();
        mFileName = fileURL.substring(fileURL.lastIndexOf('/') + 1);
        mFileSize = -1;
        mState = DOWNLOADING;
        mDownloaded = 0;
        mTime = 0;

        mListDownloadThread = new ArrayList<DownloadThread>();
        log = new StringBuilder();

        ConfigurationManager config = new ConfigurationManager();
        mNumberThread = config.NUMBER_THREAD;
        mBlockSize = config.BLOCK_SIZE;
        mBufferSize = config.BUFFER_SIZE;
        mMinDownloadSize = config.MIN_DOWNLOAD_SIZE;
    }

    /**
     * Pause the downloader
     */
    public void pause() {
        setState(PAUSED);
    }

    /**
     * Resume the downloader
     */
    public void resume() {
        setState(DOWNLOADING);
        download();
    }

    /**
     * Cancel the downloader
     */
    public void cancel() {
        setState(CANCELLED);
    }

    /**
     * Redownload file
     */
    public void redownload() {
        setState(DOWNLOADING);
        clearDownloader();
        download();
    }

    /**
     * Clear downloader
     */
    public void clearDownloader() {
        mDownloaded = 0;
        mTime = 0;
        mFileSize = -1;
        addToLog("Redownload");
        for (DownloadThread thread : mListDownloadThread) {
            thread.mCurrentByte = thread.mStartByte;
            thread.mIsFinished = false;
        }
    }

    /**
     * add to log file
     */
    public void addToLog(String str) {
        if (log == null) {
            log = new StringBuilder();
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        log.append(dtf.format(now));
        log.append(": ");
        log.append(str);
        log.append("\n");
    }

    /**
     * get log file
     */
    public String getLog() {
        return log.toString();
    }

    public String getOutputFolder() {
        return mOutputFolder;
    }

    /**
     * Open downloaded file
     */
    public boolean openFile() {
        try {
            //first check if Desktop is supported by Platform or not
            if (!Desktop.isDesktopSupported()) {
                System.out.println("Desktop is not supported");
                return false;
            }

            Desktop desktop = Desktop.getDesktop();
            File file = new File(mOutputFolder + mFileName);
            if (file.exists()) {
                desktop.open(file);
            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Open folder contain downloaded file
     */
    public boolean openFolder() {
        try {
            //first check if Desktop is supported by Platform or not
            if (!Desktop.isDesktopSupported()) {
                System.out.println("Desktop is not supported");
                return false;
            }

            Desktop desktop = Desktop.getDesktop();
            File file = new File(mOutputFolder);
            if (file.exists()) {
                desktop.open(file);
            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Get the URL (in String)
     */
    public String getURL() {
        return mURL.toString();
    }

    /**
     * Get the downloaded file's size
     */
    public long getFileSize() {
        return mFileSize;
    }

    public long getTime() {
        return mTime;
    }

    public String getLocation() {
        return mOutputFolder;
    }

    /**
     * Get the current progress of the download
     */
    public float getProgress() {
        return ((float) mDownloaded / mFileSize) * 100;
    }

    public long getDownloaded() {
        return mDownloaded;
    }

    /**
     * Get current state of the downloader
     */
    public int getState() {
        return mState;
    }

    /**
     * Get File Name
     *
     * @return get file name
     */
    public String getFileName() {
        return mFileName;
    }

    /**
     * Set the state of the downloader
     */
    protected void setState(int value) {
        mState = value;
        stateChanged();
    }

    public String getLastTime() {
        return mLastTime;
    }

    public long getTransferRate() {
        return mTransferRate;
    }

    /**
     * Start or resume download
     */
    protected void download() {
        System.out.println("Downloading: " + mFileName);
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * Increase the downloaded size
     */
    protected synchronized void downloaded(int value) {
        mDownloaded += value;
        stateChanged();
    }

    /**
     * Set the state has changed and notify the observers
     */
    protected void stateChanged() {
        setChanged();
        notifyObservers();
    }

    public ArrayList<DownloadThread> getListDownloadThread() {
        return mListDownloadThread;
    }

    /**
     * Thread to download part of a file
     */
    public abstract class DownloadThread implements Runnable, Serializable {

        protected int mThreadID;
        protected URL mURL;
        protected String mOutputFile;
        protected long mStartByte;
        protected long mCurrentByte;
        protected long mEndByte;
        protected boolean mIsFinished;
        protected MyThread mThread;
        protected boolean mIsConnecting;

        public DownloadThread(int threadID, URL url, String outputFile, long startByte, long endByte) {
            mThreadID = threadID;
            mURL = url;
            mOutputFile = outputFile;
            mStartByte = startByte;
            mCurrentByte = mStartByte;
            mEndByte = endByte;
            mIsFinished = false;
            mIsConnecting = false;

            download();
        }

        /**
         * Get whether the thread is finished download the part of file
         */
        public boolean isFinished() {
            return mIsFinished;
        }

        public boolean isConnecting() {
            return mIsConnecting;
        }

        /**
         * Start or resume the download
         */
        public void download() {
            mThread = new MyThread(this);
            mThread.start();
        }

        /**
         * Get process
         *
         * @return process of thread
         */
        public float getProgress() {
            return ((float) (mCurrentByte - mStartByte) / (mEndByte - mStartByte)) * 100;
        }

        /**
         * Waiting for the thread to finish
         *
         * @throws InterruptedException
         */
        public void waitFinish() throws InterruptedException {
            mThread.join();
        }
    }
}
