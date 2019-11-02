package sample.DistributionClient;

import sample.CentralServer.MainServer;

import java.io.*;
import java.net.Socket;

public class AddExtensionRequest{

    String fileName;
    String data;

    public AddExtensionRequest(String fileName,String data){
        this.fileName = fileName;
        this.data = data;
    }
    public void add() {
        try {
            MainServer mainServer = new MainServer();
            String ip= mainServer.getIP();
            Socket socket= new Socket(ip,2082);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("Add Extension");
            dataOutputStream.writeUTF(fileName);
            dataOutputStream.writeUTF(data);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
