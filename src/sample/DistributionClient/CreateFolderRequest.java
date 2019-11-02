package sample.DistributionClient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CreateFolderRequest extends Thread {
    Socket socket;
    String folderName;
    public CreateFolderRequest(Socket socket,String folderName){
        this.socket = socket;
        this.folderName = folderName;
    }
    @Override
    public void run() {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(folderName);
            dataOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
