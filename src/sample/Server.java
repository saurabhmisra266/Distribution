package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    public Server() throws IOException {
    }

    @Override
    public void run() {
        ServerSocket ss = null;
        int i=0;
        try {
            ss = new ServerSocket(3081);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            Socket s = null;
            try {
                s = ss.accept();
                DataInputStream dataInputStream = new DataInputStream(s.getInputStream());
                String request = dataInputStream.readUTF();
                if(request.equals("Upload")) {
                    String fileName = dataInputStream.readUTF();
                    File file = new File("E://", fileName);
                    InputStream is = s.getInputStream();
                    OutputStream os = new FileOutputStream(file);
                    Thread t = new ClientHandler(s, is, os,"Upload");
                    t.start();
                }
                else{
                    InputStream  is =s.getInputStream();
                    OutputStream os =s.getOutputStream();
                    Thread t = new ClientHandler(s,is,os,request);
                    t.start();
                }
            } catch (Exception e) {
                try {
                    s.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
        }

    }
}
