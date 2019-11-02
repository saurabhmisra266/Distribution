package sample.DistributionServer;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    final InputStream is;
    final OutputStream os;
    final Socket s;
    private  String request;
    public ClientHandler(Socket s, InputStream is, OutputStream os,String request)
    {
        this.s = s;
        this.is = is;
        this.os = os;
        this.request = request;
    }
    @Override
    public void run(){
        System.out.println("Client Connected");
        if(request.equals("Upload"))
            serverUpload();
        else clientDownload();
    }

    public void serverUpload(){
        int bytesRead = 0;
        byte[] buffer = new byte[1024];

        while (true) {
            try {
                if (!((bytesRead = is.read(buffer))!=-1)) break;
                os.write(buffer,0,bytesRead);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            os.flush();
            os.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clientDownload() {
        try {
            System.out.println("clientDownload function called");
            String fileName =request;
            System.out.println(fileName+"Server");
            String home=System.getProperty("user.home");
            home+="//Downloads";
            File file  = new File(home,fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream hp = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while((bytesRead = hp.read(buffer))!=-1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
            os.close();
            hp.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
