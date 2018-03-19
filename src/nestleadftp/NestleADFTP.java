/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nestleadftp;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import libs.CompressFile;
import libs.ConnectionFTP;
import org.apache.commons.net.ftp.FTP;

/**
 *
 * @author MAURICIO
 */
public class NestleADFTP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            JsonParser parser = new JsonParser();
            
            Object obj = parser.parse(new FileReader("DataFTP.json"));
            JsonObject jsonObject = (JsonObject) obj;
            
            if (CompressFile.compressEntry(jsonObject.get("directoryToCompress").getAsString(), jsonObject.get("directoryFileCompressed").getAsString())) {
                ConnectionFTP c = new ConnectionFTP();
                if (c.connectTo(jsonObject.get("nameServerFTP").getAsString(), jsonObject.get("user").getAsString(), jsonObject.get("password").getAsString())) {
                    c.uploadFile(jsonObject.get("directoryFileCompressed").getAsString(), "pruebajava.zip", FTP.BINARY_FILE_TYPE);
                    c.closeConnection();
                }
            } else {
                System.out.println("[..OK..]: El archivo no pudo ser comprimido.");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NestleADFTP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
