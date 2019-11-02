package sample.DistributionClient;

import java.io.*;
import java.net.Socket;

public class DownloadPieceRequest {
    String fileName;
    String ip;
    public DownloadPieceRequest(String fileName,String ip){
        this.fileName = fileName;
        this.ip = ip;
    }
    public boolean download(){
        try {
            Socket socket = new Socket(ip,3081);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(fileName);
            String path = System.getProperty("user.home")+"//Downloads";
            String downloadPiecesPath = System.getProperty("user.home")+"//Documents";
            File file = new File(downloadPiecesPath,fileName);
            OutputStream outputStream  = new FileOutputStream(file);
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while((bytesRead = inputStream.read(buffer))!=-1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
