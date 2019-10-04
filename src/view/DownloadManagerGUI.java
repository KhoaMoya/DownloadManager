package view;

import control.DownloadManager;
import model.Downloader;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import model.HttpDownloader;
import service.ClipboardTextListener;

public class DownloadManagerGUI extends javax.swing.JFrame implements Observer {

    private final DownloadTableTotal mTableModel;
    private HttpDownloader mSelectedDownloader;
    private boolean mIsClearing;
    public static DownloadManagerGUI mDownloadManagerGUI;

    /**
     * Creates new form DownloadManagerGUI
     */
    public DownloadManagerGUI() {
        mTableModel = new DownloadTableTotal();
        initComponents();
        initialize();
    }

    private void initialize() {
        getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(189, 189, 189)));
        // Set up table
        this.setLocationRelativeTo(null);
        jtbDownload.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                tableSelectionChanged();
            }
        });
        // Listener double click row in table
        jtbDownload.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    mSelectedDownloader = DownloadManager.getInstance().getDownloadList().get(row);
                    clickDefaultDownload();
                }
            }
        });
        // Allow only one row at a time to be selected.
        jtbDownload.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Set up ProgressBar as renderer for progress column.
//        ProgressRenderer renderer = new ProgressRenderer(0, 100);
//        renderer.setStringPainted(true); // show progress text
//        jtbDownload.setDefaultRenderer(JProgressBar.class, renderer);
        // Set table's row height large enough to fit JProgressBar.
        jtbDownload.setRowHeight(30);

        // Set center column
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jtbDownload.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        jtbDownload.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        jtbDownload.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        jtbDownload.getColumnModel().getColumn(0).setPreferredWidth(350);

        // Set center header
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) jtbDownload.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jtbDownload.getTableHeader().setDefaultRenderer(headerRenderer);
        jtbDownload.getTableHeader().setBackground(Color.GRAY);

        // set table
        Font font = new Font("Tohoma", Font.BOLD, 14);
        jtbDownload.getTableHeader().setForeground(Color.BLACK);
        jtbDownload.getTableHeader().setFont(font);
        jtbDownload.getTableHeader().setEnabled(true);
        jtbDownload.setSelectionBackground(new java.awt.Color(0, 153, 153));

        jtbDownload.setBackground(new java.awt.Color(255, 255, 255));
        jtbDownload.setForeground(Color.BLACK);

        jbnAdd.requestFocus();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jtxURL.setText("https://download.oracle.com/otn_software/nt/instantclient/19300/instantclient-basic-nt-19.3.0.0.0dbru.zip");
//        jtxURL.setText("http://localhost/Qtnpcap-master.zip");
        jTxtLocation.setText(DownloadManager.DEFAULT_OUTPUT_FOLDER);

        DragListener dragListener = new DragListener(this, jPanelBar);
        jPanelBar.addMouseListener(dragListener);
        jPanelBar.addMouseMotionListener(dragListener);
    }

    public void clickDefaultDownload() {
        JFrame frame = new DownloadDetailGUI(mSelectedDownloader);
        frame.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser = new javax.swing.JFileChooser();
        jPanelRemoveDownload = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jCheckBoxDeleteFileInDisk = new javax.swing.JCheckBox();
        jCbLocatingDefault = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jbtnBrowser = new javax.swing.JButton();
        jTxtLocation = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jbnResume = new javax.swing.JButton();
        jbnCancel = new javax.swing.JButton();
        jbnRemove = new javax.swing.JButton();
        jbnPause = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbDownload = new javax.swing.JTable();
        jbnAdd = new javax.swing.JButton();
        jtxURL = new javax.swing.JTextField();
        jbnDetail = new javax.swing.JButton();
        jbnOpenFolder = new javax.swing.JButton();
        jbnOpen = new javax.swing.JButton();
        jPanelBar = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabelClose = new javax.swing.JLabel();
        jLabelHide = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jbnRedownload = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jBtnSetting = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jTxtSearch = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jCbSort = new javax.swing.JComboBox<>();

        jLabel1.setText("Delete this file from the download list ?");

        jCheckBoxDeleteFileInDisk.setText("Also delete files in the disk");

        javax.swing.GroupLayout jPanelRemoveDownloadLayout = new javax.swing.GroupLayout(jPanelRemoveDownload);
        jPanelRemoveDownload.setLayout(jPanelRemoveDownloadLayout);
        jPanelRemoveDownloadLayout.setHorizontalGroup(
            jPanelRemoveDownloadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRemoveDownloadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRemoveDownloadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jCheckBoxDeleteFileInDisk))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelRemoveDownloadLayout.setVerticalGroup(
            jPanelRemoveDownloadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRemoveDownloadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxDeleteFileInDisk)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Download Manager");
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setFocusCycleRoot(false);
        setUndecorated(true);

        jCbLocatingDefault.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCbLocatingDefault.setText("Save as default location");
        jCbLocatingDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCbLocatingDefaultActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-link-30.png"))); // NOI18N
        jLabel3.setText("Download URL");

        jbtnBrowser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbtnBrowser.setText("Browser");
        jbtnBrowser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBrowserActionPerformed(evt);
            }
        });

        jTxtLocation.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-folder-30.png"))); // NOI18N
        jLabel2.setText("Save location");

        jbnResume.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbnResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-play-20.png"))); // NOI18N
        jbnResume.setText("Resume");
        jbnResume.setEnabled(false);
        jbnResume.setFocusCycleRoot(true);
        jbnResume.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jbnResume.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbnResume.setIconTextGap(10);
        jbnResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnResumeActionPerformed(evt);
            }
        });

        jbnCancel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-stop-20.png"))); // NOI18N
        jbnCancel.setText("Cancel");
        jbnCancel.setEnabled(false);
        jbnCancel.setFocusCycleRoot(true);
        jbnCancel.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jbnCancel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbnCancel.setIconTextGap(10);
        jbnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCancelActionPerformed(evt);
            }
        });

        jbnRemove.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-remove-20.png"))); // NOI18N
        jbnRemove.setText("Remove");
        jbnRemove.setEnabled(false);
        jbnRemove.setFocusCycleRoot(true);
        jbnRemove.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jbnRemove.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbnRemove.setIconTextGap(10);
        jbnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnRemoveActionPerformed(evt);
            }
        });

        jbnPause.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbnPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-pause-20.png"))); // NOI18N
        jbnPause.setText("Pause");
        jbnPause.setEnabled(false);
        jbnPause.setFocusCycleRoot(true);
        jbnPause.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jbnPause.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbnPause.setIconTextGap(10);
        jbnPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnPauseActionPerformed(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        jtbDownload.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtbDownload.setModel(mTableModel);
        jScrollPane1.setViewportView(jtbDownload);

        jbnAdd.setForeground(new java.awt.Color(51, 51, 51));
        jbnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-downloads-50.png"))); // NOI18N
        jbnAdd.setText("Download");
        jbnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbnAdd.setIconTextGap(0);
        jbnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnAddActionPerformed(evt);
            }
        });

        jtxURL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxURL.setToolTipText("");
        jtxURL.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jtxURL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxURLActionPerformed(evt);
            }
        });

        jbnDetail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbnDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-informatics-20.png"))); // NOI18N
        jbnDetail.setText("Detail");
        jbnDetail.setEnabled(false);
        jbnDetail.setFocusCycleRoot(true);
        jbnDetail.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jbnDetail.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbnDetail.setIconTextGap(10);
        jbnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnDetailActionPerformed(evt);
            }
        });

        jbnOpenFolder.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbnOpenFolder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-opened-folder-20.png"))); // NOI18N
        jbnOpenFolder.setText("Open in folder");
        jbnOpenFolder.setEnabled(false);
        jbnOpenFolder.setFocusCycleRoot(true);
        jbnOpenFolder.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jbnOpenFolder.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbnOpenFolder.setIconTextGap(10);
        jbnOpenFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnOpenFolderActionPerformed(evt);
            }
        });

        jbnOpen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-view-details-20.png"))); // NOI18N
        jbnOpen.setText("Open");
        jbnOpen.setEnabled(false);
        jbnOpen.setFocusCycleRoot(true);
        jbnOpen.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jbnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbnOpen.setIconTextGap(10);
        jbnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnOpenActionPerformed(evt);
            }
        });

        jPanelBar.setBackground(new java.awt.Color(189, 189, 189));
        jPanelBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanelBarMouseDragged(evt);
            }
        });
        jPanelBar.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jPanelBarMouseWheelMoved(evt);
            }
        });

        jLabelClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon_close_normal.png"))); // NOI18N
        jLabelClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelCloseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelCloseMouseExited(evt);
            }
        });

        jLabelHide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon_hide.png"))); // NOI18N
        jLabelHide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelHideMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelHideMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelHideMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelHideMousePressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel5.setText("Download Manager");

        javax.swing.GroupLayout jPanelBarLayout = new javax.swing.GroupLayout(jPanelBar);
        jPanelBar.setLayout(jPanelBarLayout);
        jPanelBarLayout.setHorizontalGroup(
            jPanelBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBarLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(0, 0, 0)
                .addComponent(jLabelHide, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabelClose))
        );
        jPanelBarLayout.setVerticalGroup(
            jPanelBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBarLayout.createSequentialGroup()
                .addGroup(jPanelBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelClose, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelHide, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jbnRedownload.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbnRedownload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-process-20.png"))); // NOI18N
        jbnRedownload.setText("Redownload");
        jbnRedownload.setEnabled(false);
        jbnRedownload.setFocusCycleRoot(true);
        jbnRedownload.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jbnRedownload.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbnRedownload.setIconTextGap(10);
        jbnRedownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnRedownloadActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Categories");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 184, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jBtnSetting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-gear-20.png"))); // NOI18N
        jBtnSetting.setText("Setting");
        jBtnSetting.setMaximumSize(new java.awt.Dimension(73, 30));
        jBtnSetting.setMinimumSize(new java.awt.Dimension(73, 30));

        jLabel8.setText("Search");

        jLabel9.setText("Sort by");

        jCbSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jBtnSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCbLocatingDefault)
                                .addGap(90, 90, 90))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtxURL)
                                    .addComponent(jTxtLocation))
                                .addGap(10, 10, 10)))
                        .addComponent(jbtnBrowser, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(165, 165, 165))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCbSort, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 743, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbnOpenFolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbnOpen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbnResume, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbnRedownload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbnDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbnPause, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbnRemove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelBar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jtxURL, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTxtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbtnBrowser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jbnAdd))
                .addGap(6, 6, 6)
                .addComponent(jCbLocatingDefault)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbnPause, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbnResume, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jbnRedownload, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jbnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(jbnDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbnOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbnOpenFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTxtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jCbSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbnPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnPauseActionPerformed
        mSelectedDownloader.pause();
        updateButtons();
    }//GEN-LAST:event_jbnPauseActionPerformed

    private void jbnResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnResumeActionPerformed
        mTableModel.resumeDownload(mSelectedDownloader);
        mSelectedDownloader.resume();
        updateButtons();
    }//GEN-LAST:event_jbnResumeActionPerformed

    private void jbnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCancelActionPerformed
        mSelectedDownloader.cancel();
        updateButtons();
    }//GEN-LAST:event_jbnCancelActionPerformed

    private void jbnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnRemoveActionPerformed
        jCheckBoxDeleteFileInDisk.setSelected(false);
        int option = JOptionPane.showConfirmDialog(this, jPanelRemoveDownload, "Remove File", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            mIsClearing = true;
            if (jCheckBoxDeleteFileInDisk.isSelected()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(mSelectedDownloader.getOutputFolder() + mSelectedDownloader.getFileName());
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }).run();
            }
            int index = jtbDownload.getSelectedRow();
            DownloadManager.getInstance().removeDownload(index);
            mTableModel.clearDownload(index);
            mIsClearing = false;
            mSelectedDownloader = null;
            updateButtons();
        }
    }//GEN-LAST:event_jbnRemoveActionPerformed

    private void jbnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnAddActionPerformed
        URL verifiedUrl = DownloadManager.verifyURL(jtxURL.getText());
        if (verifiedUrl != null) {
            HttpDownloader download = DownloadManager.getInstance().createDownload(verifiedUrl, jTxtLocation.getText());
            mTableModel.addNewDownload(download);
            jtxURL.setText(""); // reset add text field
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Download URL", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jbnAddActionPerformed

    private void jtxURLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxURLActionPerformed
    }//GEN-LAST:event_jtxURLActionPerformed

    private void jbtnBrowserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBrowserActionPerformed
        int returnVal = jFileChooser.showDialog(this, "Choosen folder");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            String location = file.getAbsolutePath() + "\\";
            System.out.println("Selected: " + location);
            jTxtLocation.setText(location);
        }
    }//GEN-LAST:event_jbtnBrowserActionPerformed

    private void jbnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnDetailActionPerformed
        clickDefaultDownload();
    }//GEN-LAST:event_jbnDetailActionPerformed

    private void jbnOpenFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnOpenFolderActionPerformed
        if (!mSelectedDownloader.openFolder()) {
            JOptionPane.showMessageDialog(this, "Cannot open folder !", "Error", JOptionPane.ERROR);
        }
    }//GEN-LAST:event_jbnOpenFolderActionPerformed

    private void jbnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnOpenActionPerformed
        if (!mSelectedDownloader.openFile()) {
            JOptionPane.showMessageDialog(this, "Cannot open file !", "Error", JOptionPane.ERROR);
        }
    }//GEN-LAST:event_jbnOpenActionPerformed

    private void jLabelCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelCloseMouseEntered
        jLabelClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon_close_focus.png")));
    }//GEN-LAST:event_jLabelCloseMouseEntered

    private void jLabelCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelCloseMouseExited
        jLabelClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon_close_normal.png")));
    }//GEN-LAST:event_jLabelCloseMouseExited

    private void jLabelHideMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHideMouseEntered
        jLabelHide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon_hide_focus.png")));
    }//GEN-LAST:event_jLabelHideMouseEntered

    private void jLabelHideMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHideMouseExited
        jLabelHide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon_hide.png")));
    }//GEN-LAST:event_jLabelHideMouseExited

    private void jLabelCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelCloseMouseClicked
        getInstance().setVisible(false);
    }//GEN-LAST:event_jLabelCloseMouseClicked

    private void jLabelHideMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHideMousePressed
    }//GEN-LAST:event_jLabelHideMousePressed

    private void jLabelHideMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHideMouseClicked
        setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jLabelHideMouseClicked

    private void jPanelBarMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jPanelBarMouseWheelMoved
    }//GEN-LAST:event_jPanelBarMouseWheelMoved

    private void jPanelBarMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelBarMouseDragged
    }//GEN-LAST:event_jPanelBarMouseDragged

    private void jbnRedownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnRedownloadActionPerformed
        int option = JOptionPane.showConfirmDialog(this, "Redownload will delete file existed. Do you want to continue ?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            File file = new File(mSelectedDownloader.getOutputFolder() + mSelectedDownloader.getFileName());
            if (file.exists()) {
                if (!file.delete()) {
                    JOptionPane.showMessageDialog(this, "Cannot delete file");
                    mSelectedDownloader.addToLog("Cannot delete file");
                    return;
                }
                mSelectedDownloader.addToLog("Delete file success");
            }
        }
        DownloadManager.getInstance().reDownload(mSelectedDownloader);
    }//GEN-LAST:event_jbnRedownloadActionPerformed

    private void jCbLocatingDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCbLocatingDefaultActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCbLocatingDefaultActionPerformed

    // Called when table row selection changes.
    private void tableSelectionChanged() {
//        System.out.println("table selection changed");
        // unregister from receiving notifications from the last selected download.
        if (mSelectedDownloader != null) {
            mSelectedDownloader.deleteObserver(DownloadManagerGUI.this);
        }

        // If not in the middle of clearing a download, set the selected download and register to
        // receive notifications from it.
        if (!mIsClearing) {
            int index = jtbDownload.getSelectedRow();
            if (index != -1) {
                mSelectedDownloader = DownloadManager.getInstance().getDownload(jtbDownload.getSelectedRow());
                mSelectedDownloader.addObserver(DownloadManagerGUI.this);
            } else {
                mSelectedDownloader = null;
            }
            updateButtons();
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if(arg!=null) System.out.println("!=null - " + arg.getClass().getName());
        System.out.println(o.getClass().getName());
        if (arg instanceof ClipboardTextListener) {
            ClipboardTextListener listener = (ClipboardTextListener) o;
            JOptionPane.showMessageDialog(this, listener.getClipboardText());
            
//            if(!getInstance().isVisible()){
//                
//            }
            
            
            
        } else {
            // Update buttons if the selected download has changed.
            if (mSelectedDownloader != null && mSelectedDownloader.equals(o)) {
                updateButtons();
            }
        }
    }

    /**
     * Update buttons' state
     */
    private void updateButtons() {
        if (mSelectedDownloader != null) {
            int state = mSelectedDownloader.getState();
            jbnDetail.setEnabled(true);
            switch (state) {
                case Downloader.DOWNLOADING:
                    jbnPause.setEnabled(true);
                    jbnResume.setEnabled(false);
                    jbnCancel.setEnabled(true);
                    jbnRemove.setEnabled(false);
                    jbnRedownload.setEnabled(false);
                    jbnOpen.setEnabled(false);
                    jbnOpenFolder.setEnabled(true);
                    break;
                case Downloader.PAUSED:
                    jbnPause.setEnabled(false);
                    jbnResume.setEnabled(true);
                    jbnCancel.setEnabled(true);
                    jbnRemove.setEnabled(false);
                    jbnRedownload.setEnabled(true);
                    jbnOpen.setEnabled(false);
                    jbnOpenFolder.setEnabled(true);
                    break;
                case Downloader.ERROR:
                    jbnPause.setEnabled(false);
                    jbnResume.setEnabled(true);
                    jbnCancel.setEnabled(false);
                    jbnRemove.setEnabled(true);
                    jbnRedownload.setEnabled(true);
                    jbnOpen.setEnabled(false);
                    jbnOpenFolder.setEnabled(true);
                    break;
                case Downloader.COMPLETED:
                    jbnPause.setEnabled(false);
                    jbnResume.setEnabled(false);
                    jbnCancel.setEnabled(false);
                    jbnRemove.setEnabled(true);
                    jbnRedownload.setEnabled(true);
                    jbnOpen.setEnabled(true);
                    jbnOpenFolder.setEnabled(true);
                    break;
                default: // CANCELLED
                    jbnPause.setEnabled(false);
                    jbnResume.setEnabled(false);
                    jbnCancel.setEnabled(false);
                    jbnRemove.setEnabled(true);
                    jbnRedownload.setEnabled(false);
                    jbnOpen.setEnabled(false);
                    jbnOpenFolder.setEnabled(true);
                    break;
            }
        } else {
            // No download is selected in table.
            jbnPause.setEnabled(false);
            jbnResume.setEnabled(false);
            jbnCancel.setEnabled(false);
            jbnRemove.setEnabled(false);
            jbnDetail.setEnabled(false);
            jbnOpen.setEnabled(false);
            jbnOpenFolder.setEnabled(false);
            jbnRedownload.setEnabled(false);
        }
    }

    /**
     * Exit and Save history
     */
    public void exit() {
        DownloadManager.getInstance().writeHistory();
        System.exit(0);
    }

    public static DownloadManagerGUI getInstance() {
        if (mDownloadManagerGUI == null) {
            mDownloadManagerGUI = new DownloadManagerGUI();
        }
        return mDownloadManagerGUI;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        new Thread(new ClipboardTextListener()).start();

        // set to user's look and feel
        try {
//            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        java.awt.EventQueue.invokeLater(() -> {
            getInstance().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnSetting;
    private javax.swing.JCheckBox jCbLocatingDefault;
    private javax.swing.JComboBox<String> jCbSort;
    private javax.swing.JCheckBox jCheckBoxDeleteFileInDisk;
    private javax.swing.JFileChooser jFileChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelClose;
    private javax.swing.JLabel jLabelHide;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelBar;
    private javax.swing.JPanel jPanelRemoveDownload;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTxtLocation;
    private javax.swing.JTextField jTxtSearch;
    private javax.swing.JButton jbnAdd;
    private javax.swing.JButton jbnCancel;
    private javax.swing.JButton jbnDetail;
    private javax.swing.JButton jbnOpen;
    private javax.swing.JButton jbnOpenFolder;
    private javax.swing.JButton jbnPause;
    private javax.swing.JButton jbnRedownload;
    private javax.swing.JButton jbnRemove;
    private javax.swing.JButton jbnResume;
    private javax.swing.JButton jbtnBrowser;
    private javax.swing.JTable jtbDownload;
    private javax.swing.JTextField jtxURL;
    // End of variables declaration//GEN-END:variables
}
