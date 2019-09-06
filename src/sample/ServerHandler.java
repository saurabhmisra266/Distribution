package sample;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServerHandler extends Thread {

    private File file;
    private String ip;
    public ServerHandler(File file,String ip){
         this.file = file;
         this.ip = ip;
    }
    @Override
    public void run() {
        Socket s = null;

        try {
            s = new Socket(ip, 3081);
            FileInputStream f=new FileInputStream(file);
            BufferedInputStream bu =new BufferedInputStream(f);
            OutputStream o=s.getOutputStream();
            byte buffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = bu.read(buffer)) != -1) {
                o.write(buffer, 0, bytesRead);
            }
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
