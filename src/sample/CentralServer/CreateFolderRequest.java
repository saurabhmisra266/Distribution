package sample.CentralServer;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class CreateFolderRequest extends Thread {
    Socket socket;
    public CreateFolderRequest(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String  folderName = dataInputStream.readUTF();
            String path = System.getProperty("user.home");
            path+="//"+"Downloads//"+folderName;
            File file = new File(path);
            if(!file.exists())file.mkdir();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
