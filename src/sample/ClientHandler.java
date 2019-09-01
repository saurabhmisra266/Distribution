package sample;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    final InputStream dis;
    final OutputStream dos;
    final Socket s;

    public ClientHandler(Socket s, InputStream dis, OutputStream dos)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }
    @Override
    public void run(){
        System.out.println("Client Connected");
        int bytesRead = 0;
        byte b[] = new byte[1024];

        byte[] buffer = new byte[1024];

        while (true) {
            try {
                if (!((bytesRead = dis.read(buffer))!=-1)) break;
                dos.write(buffer,0,bytesRead);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            dos.flush();
            dos.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
