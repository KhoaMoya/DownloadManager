/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author khoar
 */
public class MyThread extends Thread implements Serializable{

    public MyThread(Runnable r) {
        super(r);
    }
    
}
