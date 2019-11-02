package sample.DistributionClient;

import java.io.*;

public class DownloadFileHandler extends Thread {

    String fileName;
    public DownloadFileHandler(String fileName){
        this.fileName = fileName;
    }

    @Override
    public void run(){
       RetreiveExtensionRequest retreiveExtensionRequest = new RetreiveExtensionRequest(fileName);
       File file=retreiveExtensionRequest.retreive();
        try {
            BufferedReader hp = new BufferedReader(new FileReader(file));
            for(String ip;(ip = hp.readLine())!=null;){
                System.out.println(ip);
                DownloadPieceRequest downloadPieceRequest = new DownloadPieceRequest(fileName,ip);
                if(downloadPieceRequest.download())break;
            }
            hp.close();
            if(file.delete())System.out.println("Extension Deleted");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
