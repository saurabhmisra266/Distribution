package sample.DistributionClient;

import sample.CentralServer.MainServer;
import sample.FileOperations.CreateExtensionFile;
import sample.FileOperations.SplitFile;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UploadClient {
    private static String filePath;
    public UploadClient(String filePath) throws IOException {
        this.filePath = filePath;
    }
    public void upload() throws IOException, ClassNotFoundException {
        File file = new File(filePath);
        String path = System.getProperty("user.home") + "//Downloads//uploadedFiles.txt";
        CreateExtensionFile createExtensionFile = new CreateExtensionFile(file.getName(),path);
        createExtensionFile.create();
        MainServer mainServer = new MainServer();
        String ip= mainServer.getIP();
        Socket s = new Socket(ip,2082);
        DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
        dataOutputStream.writeUTF("UpdateFileDatabase");
        dataOutputStream.writeUTF(file.getName());
        s= new Socket(ip,2082);
        dataOutputStream = new DataOutputStream(s.getOutputStream());
        dataOutputStream.writeUTF("Retreive Ips");
        InputStream inputStream = s.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        ArrayList<String> ips;
        ips  = (ArrayList<String>)objectInputStream.readObject();
        SplitFile splitFile = new SplitFile();
        int size  = (int)file.length()/(9);
        List<File> files = splitFile.split(file,size);
        int z;
        for(int i=0;i<files.size();i++){
            Thread t =new UploadFileHandler(files.get(i),ips);
            t.start();
        }
    }
}
