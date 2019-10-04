/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

/**
 * @author Matthias Hinz
 */
public class ClipboardTextListener extends Observable implements Runnable {

    Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
    String recentContent = "";
    private volatile boolean running = true;

    public void terminate() {
        running = false;
    }

    public String getClipboardText() {
        return recentContent;
    }

    public void run() {
        System.out.println("Listening to clipboard...");
        // the first output will be when a non-empty text is detected

        // continuously perform read from clipboard
        while (running) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                // request what kind of data-flavor is supported
                List<DataFlavor> flavors = Arrays.asList(sysClip.getAvailableDataFlavors());
                // this implementation only supports string-flavor
                if (flavors.contains(DataFlavor.stringFlavor)) {
                    String data = (String) sysClip.getData(DataFlavor.stringFlavor);
                    if (!data.equals(recentContent)) {
                        recentContent = data;
                        // Do whatever you want to do when a clipboard change was detected, e.g.:
                        System.out.println("New clipboard text detected: " + data);
                        setChanged();
                        notifyObservers();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }  
    
//    public static void main(String[] args) {
//        ClipboardTextListener b = new ClipboardTextListener();
//        Thread thread = new Thread(b);
//        thread.start();
//    }
}
