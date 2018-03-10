/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nestleadftp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import libs.CompressFile;

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
            CompressFile compressFile = new CompressFile(new File("D:\\dirprueba.zip"));
            compressFile.zip(new File("D:\\pruebajava"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
