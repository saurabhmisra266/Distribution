package sample.CentralServer;

import javafx.fxml.Initializable;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RetrieveIpRequest extends  Thread {
    private Socket socket;
    private Connection connection;
    public RetrieveIpRequest(Socket socket, Connection connection){
        this.socket = socket;
        this.connection = connection;
    }
    @Override
    public void run(){


        List<String> ip = new ArrayList<String>();
        String query= "select * from users";
        try {
            Statement statement =null;
            statement = connection.createStatement();
            ResultSet res = statement.executeQuery(query);
            while(res.next()){
                ip.add(res.getString("ip"));
            }
            ArrayList<String> ips  = new ArrayList<String>();
            ips.addAll(ip);
            OutputStream out  = socket.getOutputStream();
            ObjectOutputStream objectOutputStream  = new ObjectOutputStream(out);
            objectOutputStream.writeObject(ips);

        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
