package control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khoar
 */
public class ConfigurationManager {

    public static int NUMBER_THREAD;
    public static int BLOCK_SIZE;
    public static int BUFFER_SIZE;
    public static int MIN_DOWNLOAD_SIZE;

//    public ConfigurationManager() {
//        NUMBER_THREAD = 8;
//        BLOCK_SIZE = 4096;
//        BUFFER_SIZE = 4096;
//        MIN_DOWNLOAD_SIZE = 100 * BLOCK_SIZE;
//    }
    public ConfigurationManager() {
        DataInputStream dataInputStream = null;
        
        try {
            File file = new File("configuration.txt");
            // create configuration file if don't exist
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
                    dataOutputStream.writeInt(8);
                    dataOutputStream.writeInt(4096);
                    dataOutputStream.writeInt(4096);
                    dataOutputStream.writeInt(409600);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            dataInputStream = new DataInputStream(new FileInputStream(file));
            NUMBER_THREAD = dataInputStream.readInt();
            BLOCK_SIZE = dataInputStream.readInt();
            BUFFER_SIZE = dataInputStream.readInt();
            MIN_DOWNLOAD_SIZE = dataInputStream.readInt();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                dataInputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
