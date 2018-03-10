/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author MAURICIO
 */
public class ConnectionFTP {

    public void conectarFTP() {

        FTPClient client = new FTPClient();

        // Datos para conectar al servidor FTP
        String ftp = "ftp.miservidor.com"; // También puede ir la IP
        String user = "usuario";
        String password = "password";

        try {
            // Conactando al servidor
            client.connect(ftp);

            // Logueado un usuario (true = pudo conectarse, false = no pudo
            // conectarse)
            boolean login = client.login(user, password);

            System.out.println(login);

            // Cerrando sesión
            client.logout();

            // Desconectandose con el servidor
            client.disconnect();

        } catch (IOException ioe) {

        }

    }
}
