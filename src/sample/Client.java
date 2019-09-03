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
    public static void upload() throws IOException, SQLException {
        File file = new File(filePath);
        byte[] buffer;
        SplitFile splitFile = new SplitFile();
        List<File>files = splitFile.split(file);
        Connection connection = DBConnector.getConnection();
        List<String>ips = new ArrayList<String>();
        Statement statement =null;
        String query= "select * from users";
        statement = connection.createStatement();
        ResultSet res = statement.executeQuery(query);
        while(res.next()){
            ips.add(res.getString("ip"));
        }
        for(String z:ips){
            System.out.println(z);
        }
    }
}
