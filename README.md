# DownloadManager

# Features
- Download multi thread
- Download from url
- Download from clipboard
- Manage downloaded files

![Home screen](https://github.com/KhoaMoya/DownloadManager/blob/master/DowloadManagerUI.png)

# Implementation
### 1. Split to multi threads to download a file (if server allow)
```java

            // check accept ranges
            String acceptRanges = conn.getHeaderField("Accept-Ranges");
            if (acceptRanges.equals("none")) {
                mNumConnections = 1;
            }

           // if the state is DOWNLOADING (no error) -> start downloading
            if (mState == DOWNLOADING) {
                // check whether we have list of download threads or not, if not -> init download
                if (mListDownloadThread.size() == 0) {
                    if (mFileSize > mMinDownloadSize) {
                        int partSize;
                        if (mNumConnections == 1) {
                            // download size for 1 thread
                            partSize = (int) mFileSize;
                        } else {
                            // downloading size for each thread
                            partSize = Math.round(((float) mFileSize / mNumConnections) / mBlockSize) * mBlockSize;
                        }
                        addToLog("Part size: " + partSize);

                        // start/end Byte for each thread
                        long startByte = 0;
                        long endByte = partSize - 1;
                        HttpDownloadThread aThread = new HttpDownloadThread(1, mURL, mOutputFolder + mFileName, startByte, endByte);
                        mListDownloadThread.add(aThread);
                        int i = 2;
                        while (endByte < mFileSize) {
                            startByte = endByte + 1;
                            if ((endByte + partSize) > mFileSize) {
                                endByte = mFileSize;
                            } else {
                                endByte += partSize;
                            }
                            aThread = new HttpDownloadThread(i, mURL, mOutputFolder + mFileName, startByte, endByte);
                            mListDownloadThread.add(aThread);
                            ++i;
                        }
                    } else {
                        HttpDownloadThread aThread = new HttpDownloadThread(1, mURL, mOutputFolder + mFileName, 0, mFileSize);
                        mListDownloadThread.add(aThread);
                    }
                }
```

### 2. Downloading on each thread
```java
            BufferedInputStream in = null;
            RandomAccessFile raf = null;

            try {
                // open Http connection to URL
                HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();

                // set the range of byte to download
                String byteRange = mCurrentByte + "-" + mEndByte;
                conn.setRequestProperty("Range", "bytes=" + byteRange);
                addToLog("Thread " + mThreadID + " : " + byteRange);

                // connect to server
                conn.connect();

                // Make sure the response code is in the 200 range.
                if (conn.getResponseCode() / 100 != 2) {
                    error(conn.getResponseCode() + " : " + conn.getResponseMessage());
                    return;
                }

                // get the input stream
                in = new BufferedInputStream(conn.getInputStream());

                // open the output file and seek to the start location
                raf = new RandomAccessFile(mOutputFile, "rw");
                raf.seek(mCurrentByte);

                byte data[] = new byte[mBufferSize];
                int numRead;
                while ((mState == DOWNLOADING) && ((numRead = in.read(data, 0, mBufferSize)) != -1)) {
                    mIsConnecting = true;
                    // write to buffer
                    raf.write(data, 0, numRead);
                    // increase the startByte for resume later
                    mCurrentByte += numRead;
                    mTempTranf += numRead;
                    // increase the downloaded size
                    downloaded(numRead);
                }
            } catch (IOException e) {
                error(e.getLocalizedMessage());
            }
 
 ```
### 3. Observable, Observer to update UI

```java
public abstract class Downloader extends Observable{
    /**
     * Set the state has changed and notify the observers
     */
    protected void stateChanged() {
        setChanged();
        notifyObservers();
    }
}
```

```java
public class DownloadTableTotal extends AbstractTableModel implements Observer {
	public void addNewDownload(HttpDownloader download) {
        // Register to be notified when the download changes.
        download.addObserver(this);
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
```
### 4. SystemTray listener change of clipboard

Create SystemTray with icon
```java
Image image = Toolkit.getDefaultToolkit().getImage(ClipboardResouceListener.class.getResource("/image/icons8_downloads_50.png"));

        PopupMenu trayPopupMenu = new PopupMenu();

        MenuItem action = new MenuItem("Display");
        action.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mDownloadManagerGUI==null) mDownloadManagerGUI = new DownloadManagerGUI();
                if(!mDownloadManagerGUI.isVisible()) mDownloadManagerGUI.setVisible(true);
            }
        });
			
				TrayIcon trayIcon = new TrayIcon(image, "Download Manager", trayPopupMenu);
        trayIcon.setImageAutoSize(true);

        try {
            SystemTray systemTray = SystemTray.getSystemTray();
            systemTray.add(trayIcon);
        } catch (AWTException awtException) {
            awtException.printStackTrace();
        }
```				
Listener change of clipboard
```java
java.util.List<DataFlavor> flavors = Arrays.asList(sysClip.getAvailableDataFlavors());
                    if (flavors.contains(DataFlavor.stringFlavor)) {
                        String data = (String) sysClip.getData(DataFlavor.stringFlavor);
                        if (recentContent.equals("")) {
                            recentContent = data;
                        } else if (!data.equals(recentContent)) {
                            recentContent = data;
                            System.out.println("New clipboard text detected: " + data);
                            URL url = DownloadManager.getInstance().verifyURL(data);
                            if (url != null) {
                                setChanged();
                                notifyObservers(url);
                            }
                        }
                    }
```										
