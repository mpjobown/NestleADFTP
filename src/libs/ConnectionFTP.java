/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author MAURICIO
 */
public class ConnectionFTP {

    private FTPClient client;

    public boolean connectTo(String host, String user, String password) {

        client = new FTPClient();

        try {

            client.connect(host);
            client.login(user, password);

            return true;

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }

    }

    public boolean uploadFile(String fileName) {

        try {
            client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
            client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();

            FileInputStream fileInput = new FileInputStream(fileName);

            // Guardando el archivo en el servidor
            client.storeFile(fileName, fileInput);

            return true;

        } catch (IOException ex) {
            Logger.getLogger(ConnectionFTP.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean closeConnection() {

        try {

            client.logout();
            client.disconnect();
            return true;

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }

    }

}
