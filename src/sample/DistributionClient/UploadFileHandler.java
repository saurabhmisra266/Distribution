package sample.DistributionClient;

import sample.CentralServer.MainServer;
import sample.FileOperations.SplitFile;

import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class UploadFileHandler extends Thread {

    private File file;
    private ArrayList<String> ips;
    public UploadFileHandler(File file,ArrayList<String>ips){
        this.file = file;
        this.ips=ips;
    }
    @Override
    public void run() {
        try {
            MainServer mainServer = new MainServer();
            String ip= mainServer.getIP();
            Socket socket = new Socket(ip,2082);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            SplitFile splitFile = new SplitFile();
            String hashName=splitFile.generateHash(file);
            dataOutputStream.writeUTF("UpdateChunkDatabase");
            dataOutputStream.writeUTF(file.getName());
            dataOutputStream.writeUTF(hashName);
            Random random = new Random();
            int z;
            for(int i=0;i<ips.size();i++) {
                z=random.nextInt(ips.size());
                ip = ips.get(z);
                if (sendFileToServer(ip)) {
                    AddExtensionRequest addExtensionRequest = new AddExtensionRequest(hashName, ip);
                    addExtensionRequest.add();
                    break;
                }
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public boolean sendFileToServer(String ip) {
        Socket s = null;
        try {
            s = new Socket(ip, 3081);
            FileInputStream f = new FileInputStream(file);
            DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
            dataOutputStream.writeUTF("Upload");
            dataOutputStream.writeUTF(file.getName());
            BufferedInputStream bu = new BufferedInputStream(f);
            OutputStream o = s.getOutputStream();
            byte buffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = bu.read(buffer)) != -1) {
                o.write(buffer, 0, bytesRead);
            }
            o.flush();
            o.close();
            bu.close();
            f.close();
            if (file.delete()) System.out.println("File deleted successfully");
            else System.out.println("File not deleted");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
