package sample;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private static String filePath;
    public Client(String filePath) throws IOException {
        this.filePath = filePath;
    }
    public static void upload()throws IOException {
        File file = new File(filePath);
        Socket s = new Socket("localhost", 2081);
        FileInputStream f=new FileInputStream(file);
        BufferedInputStream bu =new BufferedInputStream(f);
        OutputStream o=s.getOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = bu.read(buffer)) != -1) {
            o.write(buffer, 0, bytesRead);
        }
        o.close();
    }
}
