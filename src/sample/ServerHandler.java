package sample;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerHandler extends Thread {

    private File file;
    private String ip;
    private String request;
    public ServerHandler(File file,String ip,String request){
        this.file = file;
        this.ip = ip;
        this.request = request;
    }
    @Override
    public void run() {
        if(request.equals("Upload"))
            uploadClient();
        else
            downloadServer();

    }
    public void uploadClient(){
        Socket s = null;
        try {
            s = new Socket(ip, 3081);
            FileInputStream f=new FileInputStream(file);
            DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
            dataOutputStream.writeUTF("Upload");
            dataOutputStream.writeUTF(file.getName());
            BufferedInputStream bu =new BufferedInputStream(f);
            OutputStream o=s.getOutputStream();
            byte buffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = bu.read(buffer)) != -1) {
                o.write(buffer, 0, bytesRead);
            }
            o.close();
            f.close();
            if(file.delete())System.out.println("File deleted successfully");
            else System.out.println("File not deleted");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downloadServer(){

        try{
            System.out.println("downloadServer function called");
            Socket socket = new Socket(ip,3081);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(request);
            System.out.println(request+"Client");
            file = new File("F://",request);
            OutputStream outputStream  = new FileOutputStream(file);
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while((bytesRead = inputStream.read(buffer))!=-1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            outputStream.close();

        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
