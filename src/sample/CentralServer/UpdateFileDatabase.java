package sample.CentralServer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateFileDatabase extends Thread {
    private Socket socket;
    private Connection connection;
    public UpdateFileDatabase(Socket socket, Connection connection){
        this.socket = socket;
        this.connection = connection;
    }
    @Override
    public void run(){

        try {
            PreparedStatement PStatement = null;
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String fileName = dataInputStream.readUTF();
            PStatement = connection.prepareStatement("insert into uploadedfiles values(?,?)");
            PStatement.setString(1,fileName);
            PStatement.setString(2,"");
            PStatement.executeUpdate();
        } catch (IOException | SQLException e) {
           // e.printStackTrace();
        }
    }
}
