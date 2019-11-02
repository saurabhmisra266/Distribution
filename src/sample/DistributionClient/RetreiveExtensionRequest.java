package sample.DistributionClient;

import sample.CentralServer.MainServer;

import java.io.*;
import java.net.Socket;

public class RetreiveExtensionRequest {
    String fileName;
    public RetreiveExtensionRequest(String fileName){
        this.fileName = fileName;
    }
    public File retreive(){
         File file = null;
        try {
            MainServer mainServer = new MainServer();
            String ip = mainServer.getIP();
            Socket socket = new Socket(ip,2082);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("Retreive Extension");
            dataOutputStream.writeUTF(fileName);
            String home = System.getProperty("user.home");
             file = new File(home+"//Downloads",fileName+".txt");
            OutputStream outputStream = new FileOutputStream(file);
            InputStream in = socket.getInputStream();
            byte[] buffer  = new byte[1024];
            int bytesRead;
            while((bytesRead = in.read(buffer))!=-1){
                outputStream.write(buffer,0,bytesRead);
            }
            outputStream.flush();
            outputStream.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
