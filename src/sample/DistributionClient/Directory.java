package sample.DistributionClient;

import java.io.File;
import java.io.IOException;

public class Directory {
    public static void main(String[] args) throws IOException {
        File file = new File("F://Directory");

        if(file.exists()){
            System.out.println("Folder Created");
            File file1 = new File("F://Directory","yy.txt");
            file1.createNewFile();
        }
    }
}
