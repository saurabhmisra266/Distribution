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
        byte[] buffer;
        SplitFile splitFile = new SplitFile();
        List<File>files = splitFile.split(file);
        Connection connection = DBConnector.getConnection();
        Socket s = new Socket("192.168.31.44",2082);
        DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
        dataOutputStream.writeUTF("Retreive Ips");
        InputStream inputStream = s.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        ArrayList<String> ips = new ArrayList<String>();
       ips  = (ArrayList<String>)objectInputStream.readObject();
        for(String z:ips)System.out.println(z);
    }
}
