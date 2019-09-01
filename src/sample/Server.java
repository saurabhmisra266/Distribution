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
        try {
            ss = new ServerSocket(2081);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            Socket s = null;
            try {
                s = ss.accept();
                InputStream dis =s.getInputStream();
                OutputStream dos = new FileOutputStream("F://ssss.pdf");
                Thread t = new ClientHandler(s, dis, dos);
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
