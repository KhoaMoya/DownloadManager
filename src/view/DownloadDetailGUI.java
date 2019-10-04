/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.DownloadManager;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import model.Downloader;

/**
 *
 * @author khoar
 */
public class DownloadDetailGUI extends javax.swing.JFrame implements Observer {

    private Downloader downloader;
    private DownloadTableThread mTableThreadModel;

    /**
     * Creates new form DownloadDetail
     */
    public DownloadDetailGUI() {
        initComponents();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    public DownloadDetailGUI(Downloader downloader) {
        mTableThreadModel = new DownloadTableThread(downloader);
        initComponents();
        jTableThread.setModel(mTableThreadModel);
        this.downloader = downloader;
        initialize();
    }

    public void initialize() {
        setTitle("Download Detail");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        downloader.addObserver(this);
        jTableThread.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Set up ProgressBar as renderer for progress column.
        ProgressRenderer renderer = new ProgressRenderer(0, 100);
        renderer.setStringPainted(true); // show progress text
        jTableThread.setDefaultRenderer(JProgressBar.class, renderer);
        jTableThread.getColumnModel().getColumn(0).setPreferredWidth(20);
        jTableThread.getColumnModel().getColumn(1).setPreferredWidth(250);
        jTableThread.setRowHeight(20);

        initInfor();
        updateChange();
    }

    @Override
    public void update(Observable o, Object o1) {
        if (downloader.equals(o)) {
            updateChange();
        }
    }

    public void updateChange() {
        if (downloader != null) {
            jProgressBarTotal.setValue((int) downloader.getProgress());
            jTextAreaLog.setText(downloader.getLog());
            if (downloader.getFileSize() == -1) {
                jLabelDownloaded.setText("0 KB (0%)");
            } else {
                jLabelDownloaded.setText(DownloadManager.convertSize(downloader.getDownloaded()) + "  (" + String.format("%.1f", downloader.getProgress()) + "%)");
            }
            jLabelTransferRate.setText(DownloadManager.convertSize(downloader.getTransferRate()));
            if(downloader.getTransferRate()!=0){
                long estimateSec = (downloader.getFileSize() - downloader.getDownloaded())/downloader.getTransferRate();
                jLabelEstimateTime.setText(DownloadManager.convertTime(estimateSec));
            } else jLabelEstimateTime.setText("0s");
            
            jLabelTime.setText(DownloadManager.convertTime(downloader.getTime()));
            jLabelLastTime.setText(downloader.getLastTime());
            jLabelState.setText(Downloader.STATUSES[downloader.getState()]);
            updateButton();
        }
    }
    public void updateButton(){
        int state = downloader.getState();
            jbnOpen.requestFocus();
            switch (state) {
                case Downloader.DOWNLOADING:
                    jbnPause.setEnabled(true);
                    jbnResume.setEnabled(false);
                    jbnCancel.setEnabled(true);
                    jbnOpen.setEnabled(false);
                    jbnOpenFolder.setEnabled(true);
                    break;
                case Downloader.PAUSED:
                    jbnPause.setEnabled(false);
                    jbnResume.setEnabled(true);
                    jbnCancel.setEnabled(true);
                    jbnOpen.setEnabled(false);
                    jbnOpenFolder.setEnabled(true);
                    break;
                case Downloader.ERROR:
                    jbnPause.setEnabled(false);
                    jbnResume.setEnabled(true);
                    jbnCancel.setEnabled(false);
                    jbnOpen.setEnabled(false);
                    jbnOpenFolder.setEnabled(true);
                    break;
                case Downloader.COMPLETED:
                    jbnPause.setEnabled(false);
                    jbnResume.setEnabled(false);
                    jbnCancel.setEnabled(false);
                    jbnOpen.setEnabled(true);
                    jbnOpenFolder.setEnabled(true);
                    break;
                default: // COMPLETE or CANCELLED
                    jbnPause.setEnabled(false);
                    jbnResume.setEnabled(false);
                    jbnCancel.setEnabled(false);
                    jbnOpen.setEnabled(false);
                    jbnOpenFolder.setEnabled(true);
                    break;
            }
    }
    public void initInfor() {
        if (downloader != null) {
            jLabelFileName.setText(downloader.getFileName());
            jLabelURL.setText(downloader.getURL());
            jLabelLocation.setText(downloader.getLocation());
            jLabelNumberThread.setText(downloader.getListDownloadThread().size() + "");
            jLabelFileSize.setText(DownloadManager.convertSize(downloader.getFileSize()));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel22 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabelFileName = new javax.swing.JLabel();
        jLabelURL = new javax.swing.JLabel();
        jLabelLocation = new javax.swing.JLabel();
        jLabelFileSize = new javax.swing.JLabel();
        jLabelDownloaded = new javax.swing.JLabel();
        jLabelTime = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabelLastTime = new javax.swing.JLabel();
        jLabelTransferRate = new javax.swing.JLabel();
        jLabelUnit = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabelEstimateTime = new javax.swing.JLabel();
        jbnPause = new javax.swing.JButton();
        jProgressBarTotal = new javax.swing.JProgressBar();
        jbnResume = new javax.swing.JButton();
        jbnCancel = new javax.swing.JButton();
        jbnOpen = new javax.swing.JButton();
        jbnOpenFolder = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabelState = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableThread = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaLog = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabelNumberThread = new javax.swing.JLabel();
        jbnRedownload = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel22.setText("Estimated time");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Time");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Downloaded");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("File size");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Save Location");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("URL");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("File name");

        jLabelFileName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelFileName.setText("File_name.zip");

        jLabelURL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelURL.setText("URL");

        jLabelLocation.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelLocation.setText("location");

        jLabelFileSize.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelFileSize.setText("0 KB");

        jLabelDownloaded.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelDownloaded.setText("0 KB");

        jLabelTime.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTime.setText("0s");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Last Time");

        jLabelLastTime.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelLastTime.setText("00:00:00 00/00/0000");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelLocation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelURL, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelFileName)
                            .addComponent(jLabelDownloaded)
                            .addComponent(jLabelTime)
                            .addComponent(jLabelLastTime)
                            .addComponent(jLabelFileSize))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelFileName))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabelURL))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabelLocation))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabelFileSize))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelDownloaded)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabelTime))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabelLastTime))
                .addGap(10, 10, 10))
        );

        jLabelTransferRate.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelTransferRate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTransferRate.setText("0 KB");

        jLabelUnit.setText("/s");

        jLabel26.setText("Transfer rate");

        jLabelEstimateTime.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelEstimateTime.setText("0s");

        jbnPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-pause-20.png"))); // NOI18N
        jbnPause.setText("Pause");
        jbnPause.setFocusCycleRoot(true);
        jbnPause.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jbnPause.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbnPause.setIconTextGap(10);
        jbnPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnPauseActionPerformed(evt);
            }
        });

        jProgressBarTotal.setForeground(new java.awt.Color(0, 153, 51));
        jProgressBarTotal.setStringPainted(true);

        jbnResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-play-20.png"))); // NOI18N
        jbnResume.setText("Resume");
        jbnResume.setFocusCycleRoot(true);
        jbnResume.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jbnResume.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbnResume.setIconTextGap(10);
        jbnResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnResumeActionPerformed(evt);
            }
        });

        jbnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-stop-20.png"))); // NOI18N
        jbnCancel.setText("Cancel");
        jbnCancel.setFocusCycleRoot(true);
        jbnCancel.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jbnCancel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbnCancel.setIconTextGap(10);
        jbnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCancelActionPerformed(evt);
            }
        });

        jbnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-view-details-20.png"))); // NOI18N
        jbnOpen.setText("Open");
        jbnOpen.setFocusCycleRoot(true);
        jbnOpen.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jbnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbnOpen.setIconTextGap(10);
        jbnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnOpenActionPerformed(evt);
            }
        });

        jbnOpenFolder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-opened-folder-20.png"))); // NOI18N
        jbnOpenFolder.setText("Open in folder");
        jbnOpenFolder.setFocusCycleRoot(true);
        jbnOpenFolder.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jbnOpenFolder.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbnOpenFolder.setIconTextGap(10);
        jbnOpenFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnOpenFolderActionPerformed(evt);
            }
        });

        jLabel2.setText("Status");

        jLabelState.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelState.setForeground(new java.awt.Color(0, 0, 204));
        jLabelState.setText("None");

        jTableThread.setSelectionBackground(new java.awt.Color(189, 189, 189));
        jTableThread.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jTableThread);

        jTextAreaLog.setEditable(false);
        jTextAreaLog.setColumns(20);
        jTextAreaLog.setRows(5);
        jScrollPane2.setViewportView(jTextAreaLog);

        jLabel3.setText("Threads");

        jLabel4.setText("Log");

        jLabelNumberThread.setText("0");

        jbnRedownload.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbnRedownload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-process-20.png"))); // NOI18N
        jbnRedownload.setText("Redownload");
        jbnRedownload.setFocusCycleRoot(true);
        jbnRedownload.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jbnRedownload.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbnRedownload.setIconTextGap(10);
        jbnRedownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnRedownloadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jProgressBarTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 833, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabelNumberThread)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel26)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelTransferRate, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jbnPause)
                                        .addGap(18, 18, 18)
                                        .addComponent(jbnResume)))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jbnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jbnRedownload, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jbnOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jbnOpenFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelEstimateTime, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabelState, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(20, 20, 20)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabelState)
                    .addComponent(jLabelEstimateTime)
                    .addComponent(jLabel22)
                    .addComponent(jLabelTransferRate)
                    .addComponent(jLabelUnit)
                    .addComponent(jLabel26))
                .addGap(18, 18, 18)
                .addComponent(jProgressBarTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbnOpenFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbnOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbnResume, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbnRedownload, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbnPause, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabelNumberThread))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(20, 20, 20))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(365, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbnResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnResumeActionPerformed
        downloader.resume();
    }//GEN-LAST:event_jbnResumeActionPerformed

    private void jbnPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnPauseActionPerformed
        downloader.pause();
    }//GEN-LAST:event_jbnPauseActionPerformed

    private void jbnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCancelActionPerformed
        downloader.cancel();
    }//GEN-LAST:event_jbnCancelActionPerformed

    private void jbnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnOpenActionPerformed
        if (!downloader.openFile()) {
            JOptionPane.showMessageDialog(this, "Cannot open file !", "Error", JOptionPane.ERROR);
        }
    }//GEN-LAST:event_jbnOpenActionPerformed

    private void jbnOpenFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnOpenFolderActionPerformed
        if (!downloader.openFolder()) {
            JOptionPane.showMessageDialog(this, "Cannot open file !", "Error", JOptionPane.ERROR);
        }
    }//GEN-LAST:event_jbnOpenFolderActionPerformed

    private void jbnRedownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnRedownloadActionPerformed
        downloader.redownload();
    }//GEN-LAST:event_jbnRedownloadActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
//            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DownloadDetailGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelDownloaded;
    private javax.swing.JLabel jLabelEstimateTime;
    private javax.swing.JLabel jLabelFileName;
    private javax.swing.JLabel jLabelFileSize;
    private javax.swing.JLabel jLabelLastTime;
    private javax.swing.JLabel jLabelLocation;
    private javax.swing.JLabel jLabelNumberThread;
    private javax.swing.JLabel jLabelState;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JLabel jLabelTransferRate;
    private javax.swing.JLabel jLabelURL;
    private javax.swing.JLabel jLabelUnit;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBarTotal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableThread;
    private javax.swing.JTextArea jTextAreaLog;
    private javax.swing.JButton jbnCancel;
    private javax.swing.JButton jbnOpen;
    private javax.swing.JButton jbnOpenFolder;
    private javax.swing.JButton jbnPause;
    private javax.swing.JButton jbnRedownload;
    private javax.swing.JButton jbnResume;
    // End of variables declaration//GEN-END:variables
}
