package model;

import static model.Downloader.CANCELLED;
import static model.Downloader.DOWNLOADING;
import static model.Downloader.PAUSED;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HttpDownloader extends Downloader implements Serializable {

    public HttpDownloader(URL url, String outputFolder, int numConnections) {
        super(url, outputFolder, numConnections);
        setState(DOWNLOADING);
        download();
    }

    private void error(String mess) {
        addToLog("ERROR: " + mFileName + "\n" + mess);
        setState(ERROR);
    }

    @Override
    public void run() {
        // cập nhập lần thời gian bắt đầu/tiếp tục download
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        mLastTime = dtf.format(now);

        // cập nhập tốc độ download sau mỗi 1s
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mState == DOWNLOADING) {
                    try {
                        Thread.sleep(1000);
                        mTime++;
                        mTransferRate = mTempTranf;
                        mTempTranf = 0;
                        stateChanged();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                mTransferRate = 0;
                mTempTranf = 0;
                stateChanged();
            }
        }).start();

        HttpURLConnection conn = null;
        try {
            // Open connection to URL
            conn = (HttpURLConnection) mURL.openConnection();
            conn.setConnectTimeout(10000);

            // Connect to server
            conn.connect();

            // check accept ranges
            String acceptRanges = conn.getHeaderField("Accept-Ranges");
            if (acceptRanges.equals("none")) {
                mNumConnections = 1;
            }

            // Make sure the response code is in the 200 range.
            if (conn.getResponseCode() / 100 != 2) {
                error(conn.getResponseCode() + " : " + conn.getResponseMessage());
                return;
            }

            // Check for valid content length.
            int contentLength = conn.getContentLength();
            if (contentLength < 1) {
                error("URL is not file");
                return;
            }

            if (mFileSize == -1) {
                mFileSize = contentLength;
                stateChanged();
                addToLog("File size: " + mFileSize);
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
                } else { // resume all downloading threads
                    for (int i = 0; i < mListDownloadThread.size(); ++i) {
                        if (!mListDownloadThread.get(i).isFinished()) {
                            mListDownloadThread.get(i).download();
                        }
                    }
                }

                System.out.println(mNumConnections);
                // waiting for all threads to complete
                for (int i = 0; i < mListDownloadThread.size(); ++i) {
                    mListDownloadThread.get(i).waitFinish();
                }

                // check the current state again
                if (mState == DOWNLOADING) {
                    addToLog("Completed: " + mFileName + "\n");
                    setState(COMPLETED);
                } else if (mState == PAUSED) {
                    addToLog("Paused : " + mFileName + "\n");
                } else if (mState == CANCELLED) {
                    addToLog("Cancelled : " + mFileName + "\n");
                } else {
                    addToLog("Error : " + mFileName + "\n");
                }
            }
        } catch (Exception e) {
            error(e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * Thread using Http protocol to download a part of file
     */
    public class HttpDownloadThread extends DownloadThread implements Serializable {

        /**
         * Constructor
         *
         * @param threadID
         * @param url
         * @param outputFile
         * @param startByte
         * @param endByte
         */
        public HttpDownloadThread(int threadID, URL url, String outputFile, long startByte, long endByte) {
            super(threadID, url, outputFile, startByte, endByte);
        }

        @Override
        public void run() {
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
            } finally {
                if (raf != null) {
                    try {
                        raf.close();
                    } catch (IOException e) {
                    }
                }

                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                    }
                }
            }
            mIsConnecting = false;
            stateChanged();
            if (mState == DOWNLOADING) {
                mIsFinished = true;
                addToLog("Finished thread: " + mThreadID);
            }
        }
    }
}
