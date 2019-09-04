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
    public static void upload() throws IOException, SQLException,ClassNotFoundException {
        File file = new File(filePath);
        SplitFile splitFile = new SplitFile();
        List<File>files = splitFile.split(file);
        Socket s = new Socket("192.168.31.44",2082);
        DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
        dataOutputStream.writeUTF("Retreive Ips");
        InputStream inputStream = s.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        ArrayList<String> ips = new ArrayList<String>();
       ips  = (ArrayList<String>)objectInputStream.readObject();
       int z=0;
        for(int i=0;i<files.size();i++){
            Thread t=new ServerHandler(files.get(i),ips.get(z));
            t.start();
            z=(z+1)%ips.size();
        }
    }
}
