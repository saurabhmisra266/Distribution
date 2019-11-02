package sample.FileOperations;

import java.io.*;

public class CreateExtensionFile {
    String data;
    String filePath;
    public  CreateExtensionFile(String data,String filePath){
        this.data = data;
        this.filePath = filePath;
    }
    public File create() throws IOException {

         File file = new File(filePath);
         if(!file.exists())file.createNewFile();
         FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(),true);
         BufferedWriter hp = new BufferedWriter(fileWriter);
         PrintWriter printWriter = new PrintWriter(hp);
         printWriter.println(data+"\n");
         printWriter.flush();
         printWriter.close();
         hp.close();
         fileWriter.close();
         return file;
    }
}
