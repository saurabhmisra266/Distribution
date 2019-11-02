package sample.CentralServer;

import java.io.*;
import java.net.Socket;

public class AddExtensionRequest extends Thread {
    Socket socket;
    public AddExtensionRequest(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String fileName = dataInputStream.readUTF();
            String data =dataInputStream.readUTF();
            String filePath = "E://"+fileName+".txt";
            File file = new File(filePath);
            if(!file.exists())file.createNewFile();
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter hp = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(hp);
            printWriter.println(data+"\n");
            printWriter.flush();
            printWriter.close();
            hp.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
