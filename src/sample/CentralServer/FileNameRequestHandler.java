package sample.CentralServer;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FileNameRequestHandler extends Thread {

    private Socket socket;
    private Connection connection;
    public FileNameRequestHandler(Socket socket,Connection connection){
        this.socket = socket;
        this.connection = connection;
    }
    @Override
    public void run(){
        List<String> fileName= new ArrayList<String>();
        String query= "select * from uploadedfiles";
        try {
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet res = statement.executeQuery(query);
            while (res.next()) {
                fileName.add(res.getString("fileName"));
            }
            ArrayList<String> fileNames = new ArrayList<String>();
            fileNames.addAll(fileName);
            OutputStream out = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            objectOutputStream.writeObject(fileNames);
        }catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
