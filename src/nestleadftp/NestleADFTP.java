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
import java.util.Calendar;
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

    public static String addZeroToNumber(int number) {

        return number <= 9 ? "0" + number : number + "";

    }

    public static String getNameFile() {

        String nameFile = "";
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = addZeroToNumber(calendar.get(Calendar.MONTH) + 1);
        String day = addZeroToNumber(calendar.get(Calendar.DAY_OF_MONTH));

        nameFile = "NESTLE_210226_" + year + "6" + month + day;

        return nameFile;

    }

    public static void main(String[] args) {

        try {
            JsonParser parser = new JsonParser();

            Object obj = parser.parse(new FileReader("DataFTP.json"));
            JsonObject jsonObject = (JsonObject) obj;

            if (CompressFile.compressEntry(jsonObject.get("directoryToCompress").getAsString(), jsonObject.get("directoryFileCompressed").getAsString() + getNameFile() + jsonObject.get("typeFile").getAsString())) {
                ConnectionFTP c = new ConnectionFTP();
                if (c.connectTo(jsonObject.get("nameServerFTP").getAsString(), jsonObject.get("user").getAsString(), jsonObject.get("password").getAsString())) {
                    c.uploadFile(jsonObject.get("directoryFileCompressed").getAsString(), jsonObject.get("directoryFileCompressed").getAsString() + getNameFile() + jsonObject.get("typeFile").getAsString(), FTP.BINARY_FILE_TYPE);
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
