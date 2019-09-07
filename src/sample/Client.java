package sample;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private static String filePath;
    public Client(){

    }
    public Client(String filePath) throws IOException {
        this.filePath = filePath;
    }
    public static void upload() throws IOException, SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        File file = new File(filePath);
        ArrayList<String> extension = new ArrayList<String>();
        SplitFile splitFile = new SplitFile();
        List<File>files = splitFile.split(file);
        String hash = splitFile.generateHash(file)+".txt";
        Socket s = new Socket("192.168.31.44",2082);
        DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
        dataOutputStream.writeUTF("Retreive Ips");
        InputStream inputStream = s.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        ArrayList<String> ips = new ArrayList<String>();
       ips  = (ArrayList<String>)objectInputStream.readObject();
       int z=0;
        for(int i=0;i<files.size();i++){
            System.out.println(ips.get(z));
            extension.add(files.get(i).getName());
            extension.add(ips.get(z));
            Thread t=new ServerHandler(files.get(i),ips.get(z),"Upload");
            t.start();
            z=(z+1)%ips.size();
        }
        String fileName = file.getName();
        System.out.println(fileName);
       // s= new Socket("192.168.32.44",2082);
         file = new File("F:\\",hash);
        addExtension(extension,hash,file);
        dataOutputStream.writeUTF(hash);
        dataOutputStream.writeUTF(fileName);
        OutputStream out = s.getOutputStream();
        BufferedInputStream hp = new BufferedInputStream(new FileInputStream(file));
        byte buffer[] = new byte[(int)file.length()];
        hp.read(buffer,0,buffer.length);
        out.write(buffer,0 ,buffer.length);
        out.flush();
        out.close();
        hp.close();
        if(file.delete())System.out.println("Hash deleted successfuly");
    }

    public static void addExtension(List<String> details,String hash,File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedWriter hp = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
        for(String z : details){
            hp.write(z);
            hp.newLine();
        }
        hp.close();
    }

    public void download(String fileName) throws IOException, InterruptedException {
        Socket socket = new Socket("192.168.31.44",2082);
        DataOutputStream dataOutputStream  =new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF("Retreive Extension");
        dataOutputStream.writeUTF(fileName);
        File file = new File("F://",fileName+".txt");
        OutputStream outputStream = new FileOutputStream(file);
        InputStream in = socket.getInputStream();
        byte[] buffer  = new byte[1024];
        int bytesRead;
        while((bytesRead = in.read(buffer))!=-1){
            outputStream.write(buffer,0,bytesRead);
        }
        outputStream.flush();
        outputStream.close();
        in.close();
        List<String>fileNames = new ArrayList<String>();
        List<String> ips = new ArrayList<String>();
        BufferedReader hp = new BufferedReader(new FileReader(file));
        int z=1;
        for(String line;(line = hp.readLine())!=null;){
            System.out.println(line);
            if(z%2==0)
                ips.add(line);
            else fileNames.add(line);
            z++;
        }
        hp.close();
        System.out.println(ips.size());
        System.out.println("File Read");
        for(int i=0;i<ips.size();i++){
            System.out.println(ips.get(i)+"    "+fileNames.get(i));
            Thread t = new ServerHandler(file,ips.get(i),fileNames.get(i));
            t.start();
            t.join();
        }
        System.out.println("Merging Started");
        File downloadedFile = new File("F://","tttt"+fileName);
        List<File> fileList = new ArrayList<File>();
        for(int i=0;i<fileNames.size();i++){
            file = new File("F://",fileNames.get(i));
            fileList.add(file);
        }
        SplitFile splitFile = new SplitFile();
        splitFile.mergeFiles(fileList,downloadedFile);

    }
}
