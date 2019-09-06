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
    public Client(String filePath) throws IOException {
        this.filePath = filePath;
    }
    public static void upload() throws IOException, SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        File file = new File(filePath);
        ArrayList<String> extension = new ArrayList<String>();
        SplitFile splitFile = new SplitFile();
        List<File>files = splitFile.split(file);
        String hash = splitFile.generateHash(file)+".txt";
        extension.add(hash);
        extension.add(files.size()+"");
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
            Thread t=new ServerHandler(files.get(i),ips.get(z));
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
}
