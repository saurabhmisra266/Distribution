package sample.FileOperations;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
public class SplitFile {

    public SplitFile(){
    }

    public List<File> split(File file,int size) throws IOException {
        List<File> files =new ArrayList<File>();
        int i=0;
        byte[] buffer =new byte[size];
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis =new BufferedInputStream(fis);
            int bytes;
            while((bytes=bis.read(buffer))>0) {
                String filePartName =file.getName()+(i++);
                File fileParts = new File(file.getParent(),filePartName);
                //files.add(fileParts);
                FileOutputStream out = new FileOutputStream(fileParts);
                out.write(buffer, 0,bytes);
                files.add(fileParts);
                out.flush();
                out.close();
            }
           fis.close();
            bis.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return files;
    }


    public static void mergeFiles(List<File> files, File into)
            throws IOException {
        try (FileOutputStream fos = new FileOutputStream(into);
             BufferedOutputStream mergingStream = new BufferedOutputStream(fos)) {
            for (File f : files) {
                Files.copy(f.toPath(), mergingStream);
                if(f.delete())System.out.println("Chunk deleted");
            }
        }
    }
    public static String generateHash(File file) throws NoSuchAlgorithmException, IOException {
        byte[] buffer= new byte[1024];
        int count;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        BufferedInputStream bis;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            while ((count = bis.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }
            bis.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] hash = digest.digest();
        String z =new BigInteger(1,hash).toString(16);
        return z;
    }

}
