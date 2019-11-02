package sample.CentralServer;

import sample.DBConnector;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;

public class MainServer {

    public String getIP() throws UnknownHostException {
        InetAddress localhost = InetAddress.getLocalHost();
        return localhost.getHostAddress().trim();
    }
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
            else if(received.equals("Retreive FileNames")){
                Thread t = new FileNameRequestHandler(s,connection);
                t.start();
            }
            else if(received.equals("Create Folder")){
                Thread t = new CreateFolderRequest(s);
                t.start();
            }
            else if(received.equals("Add Extension")){
                Thread t = new AddExtensionRequest(s);
                t.start();
            }
            else if(received.equals("UpdateChunkDatabase")){
                Thread t= new UpdateChunkDatabase(s,connection);
                t.start();
            }
            else if(received.equals("UpdateFileDatabase")){
                Thread t= new UpdateFileDatabase(s,connection);
                t.start();
            }
            else if(received.equals("UserDetailsRequest")){
                Thread t = new UserDetailsRequest(s,connection);
                t.start();
            }
            else{
                Thread t =new RetrieveIpRequest(s,connection);
                t.start();
            }
        }
    }
}
