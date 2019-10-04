/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Label;
import model.Downloader;

/**
 *
 * @author khoar
 */
public class MyLabel extends Label {
    
    public MyLabel(String state) throws HeadlessException {
        Font font = new Font("Tohoma", Font.BOLD, 14);
        setFont(font);
        setText(state);
        switch (state) {
            case "Downloading" :
                setForeground(Color.BLACK);
                System.out.println("downloading");
                break;
            case "Cancelled" :
                setForeground(Color.RED);
                break;
            case "Paused":
                 setForeground(Color.BLUE);
                break;
            case "Error":
                setForeground(Color.ORANGE);
                break;
            case "Completed":
                setForeground(new java.awt.Color(0, 153, 153));
                break;
            default:
                System.out.println("default");
                break;
        }
    }
}
