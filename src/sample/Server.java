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
            ss = new ServerSocket(2081);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            Socket s = null;
            try {
                s = ss.accept();
                InputStream is =s.getInputStream();

                OutputStream os = new FileOutputStream("F://ssss"+i++);
                Thread t = new ClientHandler(s, is, os);
                t.start();
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
