package sample;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    final InputStream is;
    final OutputStream os;
    final Socket s;

    public ClientHandler(Socket s, InputStream is, OutputStream os)
    {
        this.s = s;
        this.is = is;
        this.os = os;
    }
    @Override
    public void run(){
        System.out.println("Client Connected");
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

}
