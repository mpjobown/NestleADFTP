/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author MAURICIO
 */
public class ConnectionFTP {

    private FTPClient client;

    public boolean connectTo(String host, String user, String password) {

        client = new FTPClient();
        int reply;

        try {

            client.connect(host);
            reply = client.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                System.out.println("[..OK..]: Conectado al servidor correctamente.");
                client.login(user, password);
                reply = client.getReplyCode();
                if (FTPReply.isPositiveCompletion(reply)) {
                    System.out.println("[..OK..]: Iniciado sesión correctamente.");
                } else {
                    System.out.println("[..Error..]: No se ha podido iniciar sesión con el usuario: " + user);
                    return false;
                }
            } else {
                System.out.println("[..Error..]: No se ha podido establecer conexión con el servidor: " + host + ".");
                return false;
            }

            return true;

        } catch (IOException e) {
            System.out.println("[..Error..]: No se ha podido establecer conexión con el servidor, se ha agotatado el tiempo de espera.");
            return false;
        }

    }

    public boolean uploadFile(String file, String fileName, int typeFile) {

        int reply;
        System.out.println("Cargando archivo por favor espere...");

        try {
            client.setFileType(typeFile);

            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));

            client.enterLocalPassiveMode();
            client.storeFile(fileName, bufferedInputStream);

            reply = client.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                System.out.println("[..OK..]: Archivo cargado al servidor correctamente.");
            }else{
                System.out.println("[..Error..]: El archivo no se pudo cargar al servidor.");
                return false;
            }

            bufferedInputStream.close();

            return true;

        } catch (IOException ex) {
            System.out.println("[..Error..]: El archivo no se pudo cargar al servidor.");
            return false;
        }

    }

    public boolean closeConnection() {

        try {

            client.logout();
            client.disconnect();
            return true;

        } catch (IOException e) {
            System.out.println("[..Error..]: No se ha podido desconectar del servidor.");
            return false;
        }

    }

}
