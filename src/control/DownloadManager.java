package control;

import model.HttpDownloader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import static model.Downloader.DOWNLOADING;
import view.DownloadManagerGUI;

public class DownloadManager {

    // The unique instance of this class
    private static DownloadManager sInstance = null;

    // Constant variables
    private static int DEFAULT_NUM_CONN_PER_DOWNLOAD = 8;
    public static String DEFAULT_OUTPUT_FOLDER = "D:/downloadManager/";
    public static String DEFAULT_HISTORY_FILE = "history.txt";
    public static ArrayList<String> listFilter;
    public String[] filters = {".3GP", ".7Z", ".AAC", ".ACE", ".AIF", ".ARJ",
        ".ASF", ".AVI", ".BIN", ".BZ2", ".EXE", ".GZ", ".GZIP", ".IMG", ".ISO", ".LZH", ".M4A",
        ".M4V", ".MKV", ".MOV", ".MP3", ".MP4", ".MPA", ".MPE", ".MPEG", ".MPG", ".MSI", ".MSU", ".OGG",
        ".OGV", ",PDF", ".PLJ", ".PPS", ".PPT", ".QT", ".RAR", ".TAR", ".TIF", ".TIFF", ".TFF", ".WAV", ".WMA", ".WMV", ".Z", ".ZIP"};

    // Member variables
    private int mNumConnPerDownload;
    private ArrayList<HttpDownloader> mDownloadList;

    /**
     * Protected constructor
     */
    public DownloadManager() {
        mNumConnPerDownload = DEFAULT_NUM_CONN_PER_DOWNLOAD;
        mDownloadList = readHistory();
        listFilter = new ArrayList<>(Arrays.asList(filters));
    }

    /**
     * Read downloader list from history.txt file
     */
    public ArrayList<HttpDownloader> readHistory() {
        ObjectInputStream objectInputStream = null;
        ArrayList<HttpDownloader> list = new ArrayList<>();
        try {
            File file = new File(DEFAULT_HISTORY_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }

            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            HttpDownloader downloader;
            while ((downloader = (HttpDownloader) objectInputStream.readObject()) != null) {
                list.add(downloader);
            }
        } catch (Exception ex) {
//            ex.printStackTrace();
//            System.out.println(ex.getMessage());
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            return list;
        }
    }

    /**
     * Write mDownloadList to history.txt file
     */
    public boolean writeHistory() {
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(new File(DEFAULT_HISTORY_FILE)));
            for (HttpDownloader downloader : mDownloadList) {
                outputStream.writeObject(downloader);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Get the max. number of connections per download
     */
    public int getNumConnPerDownload() {
        return mNumConnPerDownload;
    }

    /**
     * Set the max number of connections per download
     */
    public void SetNumConnPerDownload(int value) {
        mNumConnPerDownload = value;
    }

    /**
     * Get the httpDownloader object in the list
     *
     * @param index
     * @return
     */
    public HttpDownloader getDownload(int index) {
        return mDownloadList.get(index);
    }

    public void removeDownload(int index) {
        mDownloadList.remove(index);
    }

    /**
     * Get the download list
     *
     * @return
     */
    public ArrayList<HttpDownloader> getDownloadList() {
        return mDownloadList;
    }

    public HttpDownloader createDownload(URL verifiedURL, String outputFolder) {
        HttpDownloader fd = new HttpDownloader(verifiedURL, outputFolder, mNumConnPerDownload);
        mDownloadList.add(fd);
        return fd;
    }

    /**
     * Redownload downloader
     *
     * @param download
     */
    public void reDownload(HttpDownloader download) {
        download.redownload();
    }

    /**
     * Get the unique instance of this class
     *
     * @return the instance of this class
     */
    public static DownloadManager getInstance() {
        if (sInstance == null) {
            sInstance = new DownloadManager();
        }

        return sInstance;
    }

    /**
     * Verify whether an URL is valid
     *
     * @param fileURL
     * @return the verified URL, null if invalid
     */
    public static URL verifyURL(String fileURL) {
        // Only allow HTTP URLs.
        if (fileURL.toLowerCase().startsWith("https://") || fileURL.toLowerCase().startsWith("http://")) {
            int index = fileURL.lastIndexOf(".");
            if (index < 0) {
                return null;
            }
            String end = fileURL.substring(index, fileURL.length());
            System.out.println(end);
            if (DownloadManager.listFilter.contains(end.toUpperCase())) {
                // Verify format of URL.
                URL verifiedUrl = null;
                try {
                    verifiedUrl = new URL(fileURL);
                } catch (Exception e) {
                    return null;
                }

                // Make sure URL specifies a file.
                if (verifiedUrl.getFile().length() < 2) {
                    return null;
                }
                return verifiedUrl;
            }
        }
        return null;
    }

    /**
     * Convert from second to day:hour:minute:second
     *
     * @param time second: long
     * @return string day:hour:minute:second
     */
    public static String convertTime(long time) {
        if (time / 86400 > 0) {
            long day = time / 86400;
            long hour = (time - day * 86400) / 3600;
            long minute = (time - day * 86400 - 3600 * hour) / 60;
            long second = time - day * 86400 - 3600 * hour - minute * 60;
            return day + "d:" + hour + "h:" + minute + "m:" + second + "s";
        }

        if (time / 3600 > 0) {
            long hour = time / 3600;
            long minute = (time - 3600 * hour) / 60;
            long second = time - 3600 * hour - minute * 60;
            return hour + "h:" + minute + "m:" + second + "s";
        }
        if (time / 60 > 0) {
            long minute = time / 60;
            long second = time - minute * 60;
            return minute + "m:" + second + "s";
        }
        return time + "s";
    }

    /**
     * Convert from byte to GB/MB/KB
     *
     * @param size : byte
     * @return stirng GB/MB/KB
     */
    public static String convertSize(long size) {
        if ((size / 1000000000) > 0) {
            return String.format("%.1f", (float) size / 1000000000) + " GB";
        }
        if ((size / 1000000) > 0) {
            return String.format("%.1f", (float) size / 1000000) + " MB";
        }
        if ((size / 1000) > 0) {
            return String.format("%.1f", (float) size / 1000) + " KB";
        }
        return "0 KB";
    }
}
