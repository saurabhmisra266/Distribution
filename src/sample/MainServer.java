package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MainServer {
    public static void main(String[] args) throws IOException, SQLException {
        ServerSocket ss = new ServerSocket(2082);
         Connection connection = DBConnector.getConnection();
        System.out.println("Server Started");
        while(true){
            Socket s= null;
            s = ss.accept();
            System.out.println("Client connected");
            DataInputStream din  = new DataInputStream(s.getInputStream());
            String received = din.readUTF();
            System.out.println(received);
            if(received.equals("Register")) {
                Thread t = new RegisterClientHandler(s,connection);
                t.start();
            }
            else if(received.equals("Login")){
                Thread t = new LoginClentHandler(s,connection);
                t.start();
            }
            else if(received.equals("Retreive Extension")){
                Thread t = new DownloadClientHandler(s,connection);
                t.start();
            }
            else{
                Thread t =new UploadClientHandler(s,connection);
                t.start();
            }
        }
    }
}
