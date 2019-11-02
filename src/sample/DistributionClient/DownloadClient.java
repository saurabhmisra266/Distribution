package sample.DistributionClient;

import sample.FileOperations.CreateExtensionFile;
import sample.FileOperations.SplitFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DownloadClient {
    private String downloadFileName;
   public int downloadedPieces;
    public DownloadClient(String downloadFileName){
        this.downloadFileName = downloadFileName;
    }
    public void download() throws InterruptedException, IOException {
        downloadedPieces=0;
        for(int i=0;i<10;i++){
            System.out.println(downloadFileName+i);
            Thread t= new DownloadFileHandler(downloadFileName+i);
            t.start();
            t.join();
            if(!t.isAlive())downloadedPieces++;
        }
        String path = System.getProperty("user.home")+"//Downloads";
        String downloadPiecesPath = System.getProperty("user.home")+"//Documents";
        File downloadedFile = new File(path,downloadFileName);
        List<File> fileList = new ArrayList<File>();
        File file;
        for(int i=0;i<10;i++){
            file = new File(downloadPiecesPath,downloadFileName+i);
            fileList.add(file);
        }
        SplitFile splitFile = new SplitFile();
        splitFile.mergeFiles(fileList,downloadedFile);
        path+="//downloadedFiles.txt";
        CreateExtensionFile createExtensionFile = new CreateExtensionFile(downloadFileName,path);
        createExtensionFile.create();
    }

}
