/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import control.DownloadManager;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import view.DownloadManagerGUI;

/**
 *
 * @author khoar
 */
public class ClipboardResouceListener implements Observer {

    private static ClipboardResouceListener mInstance;
    private DownloadManagerGUI mDownloadManagerGUI;

    public ClipboardResouceListener() {
        ClipboardTextListener listener = new ClipboardTextListener();
        listener.addObserver(ClipboardResouceListener.this);
        
        if (!SystemTray.isSupported()) {
            System.out.println("System tray is not supported !");
            return;
        }

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
        trayPopupMenu.add(action);

        MenuItem close = new MenuItem("Exit");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DownloadManager.getInstance().writeHistory();
                System.exit(0);
            }
        });
        trayPopupMenu.add(close);

        TrayIcon trayIcon = new TrayIcon(image, "Download Manager", trayPopupMenu);
        trayIcon.setImageAutoSize(true);

        try {
            SystemTray systemTray = SystemTray.getSystemTray();
            systemTray.add(trayIcon);
        } catch (AWTException awtException) {
            awtException.printStackTrace();
        }
    }
    
    public static ClipboardResouceListener createInstance(){
        if(mInstance == null){
            mInstance = new ClipboardResouceListener();
        }
        return mInstance;
    }

    public static void main(String[] args) {
        createInstance();
        
        try {
//           UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
    }

    @Override
    public void update(Observable o, Object object) {
        if (object instanceof URL) {
            URL url  = (URL) object;
            int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn download file từ đường dẫn:\n" + url.toString(), "Download with clipboard", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                if(mDownloadManagerGUI==null){
                    mDownloadManagerGUI = new DownloadManagerGUI();
                }
                if(!mDownloadManagerGUI.isVisible()){
                    mDownloadManagerGUI.setVisible(true);
                }
                
                mDownloadManagerGUI.addDownloadFromURL(url);
            }
        }
    }

    public static class ClipboardTextListener extends Observable implements Runnable {

        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        String recentContent = "";
        private volatile boolean running = true;

        public void terminate() {
            running = false;
        }

        public String getClipboardText() {
            return recentContent;
        }

        public ClipboardTextListener() {
            new Thread(this).start();
        }

        public void run() {
            System.out.println("Listening to clipboard...");
            while (running) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
