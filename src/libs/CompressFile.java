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

            System.out.println("Output to Zip : " + zipFile);
            FileInputStream in = null;

            for (String file : fileList) {
                System.out.println("File Added : " + file);
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
            System.out.println("Folder successfully compressed");

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
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
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }

    private static String generateZipEntry(String file, String sourceFolder) {
        return file.substring(sourceFolder.length() + 1, file.length());
    }

}
