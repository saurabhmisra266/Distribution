package sample;

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

    public List<File> split(File file) throws IOException {
        List<File> files =new ArrayList<File>();
        int size=1024*1024;
        int i=0;
        byte[] buffer =new byte[size];
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis =new BufferedInputStream(fis);
            int bytes=0;
            while((bytes=bis.read(buffer))>0) {
                String filePartName = file.getName()+(i++)+".pdf";
                File fileParts = new File(file.getParent(),filePartName);
                //files.add(fileParts);
                FileOutputStream out = new FileOutputStream(fileParts);
                out.write(buffer, 0,bytes);
                files.add(fileParts);
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return files;
    }
    public static void main(String[] args)  throws IOException, NoSuchAlgorithmException{

        // TODO Auto-generated method stub
		File file = new File("F://uu.pdf");
		File file1 = new File("F://panda.pdf");
		List<File> files =new ArrayList<File>();
		int size=1024*1024;
		String z=generateHash(file);
		int i = 0;
		byte[] buffer =new byte[size];

		try {
		 FileInputStream fis = new FileInputStream(file);
		 BufferedInputStream bis =new BufferedInputStream(fis);
		 int bytes=0;
			while((bytes=bis.read(buffer))>0) {
				String filePartName = file.getName()+(i++)+".pdf";
				File fileParts = new File(file.getParent(),filePartName);
				//files.add(fileParts);
				FileOutputStream out = new FileOutputStream(fileParts);
				out.write(buffer, 0,bytes);
                files.add(fileParts);
			}

            		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File mergedFile = new File("F:\\uuu.pdf");
		System.out.println(mergedFile.getAbsolutePath());
		mergeFiles(files,mergedFile);
		String w=generateHash(mergedFile);
		System.out.println(z+"    "+w+"    "+z.equals(w));
    }
    public static void mergeFiles(List<File> files, File into)
            throws IOException {
        try (FileOutputStream fos = new FileOutputStream(into);
             BufferedOutputStream mergingStream = new BufferedOutputStream(fos)) {
            for (File f : files) {
                Files.copy(f.toPath(), mergingStream);
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
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] hash = digest.digest();
        String z =new BigInteger(1,hash).toString(16);
        return z;
    }

}
