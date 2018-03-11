/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nestleadftp;

import libs.CompressFile;
import libs.ConnectionFTP;

/**
 *
 * @author MAURICIO
 */
public class NestleADFTP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.out.println(CompressFile.compressEntry("D:\\pruebajava", "D:\\dirpruebaasd.zip"));
        ConnectionFTP c = new ConnectionFTP();
        if (c.connectTo("apps.grupobit.net", "Nestle_210226", "Ts210226X")) {
            c.uploadFile("file.txt");
            c.closeConnection();
        }

    }

}
