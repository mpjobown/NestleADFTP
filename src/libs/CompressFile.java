/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author MAURICIO
 */
public class CompressFile {

    private static List<String> fileList;

    public static boolean compressEntry(String sourceFolder, String outputZipFile) {
        fileList = new ArrayList<String>();
        if (generateFileList(new File(sourceFolder), sourceFolder)) {
            return zipIt(sourceFolder, outputZipFile);
        } else {
            return false;
        }

    }

    private static boolean zipIt(String sourceFolder, String zipFile) {
        byte[] buffer = new byte[1024];
        String source = new File(sourceFolder).getName();
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);

            FileInputStream in = null;
            
            System.out.println("Compresión de archivos.");
            for (String file : fileList) {
                System.out.println("[..OK..]: Comprimiendo archivo: " + file);
                ZipEntry ze = new ZipEntry(source + File.separator + file);
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(sourceFolder + File.separator + file);
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                } finally {
                    in.close();
                }
            }
            
            zos.closeEntry();
            System.out.println("[..OK..]: Folder comprimido satisfactoriamente.");
            System.out.println("Ubicación del archivo: " + zipFile);


        } catch (IOException ex) {
            System.out.println("[..OK..]: Error al comprimir el archivo " + zipFile);
            ex.printStackTrace();
            return false;
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
                System.out.println("[..OK..]: Error al comprimir el archivo " + zipFile);
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private static boolean generateFileList(File node, String sourceFolder) {
        // add file only
        try {
            if (node.isFile()) {
                fileList.add(generateZipEntry(node.toString(), sourceFolder));
            }

            if (node.isDirectory()) {
                String[] subNote = node.list();
                for (String filename : subNote) {
                    generateFileList(new File(node, filename), sourceFolder);
                }
            }
        } catch (Exception e) {
            System.out.println("[..OK..]: Error al comprimir el archivo");
            return false;
        }
        return true;
    }

    private static String generateZipEntry(String file, String sourceFolder) {
        return file.substring(sourceFolder.length() + 1, file.length());
    }

}
