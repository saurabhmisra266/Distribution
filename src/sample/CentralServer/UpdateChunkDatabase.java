package sample.CentralServer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateChunkDatabase extends Thread {
    private Socket socket;
    private Connection connection;
    public UpdateChunkDatabase(Socket socket, Connection connection){
        this.socket = socket;
        this.connection = connection;
    }
    @Override
    public void run(){

        try {
            PreparedStatement PStatement = null;
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String fileName = dataInputStream.readUTF();
            String hashName = dataInputStream.readUTF();
            PStatement = connection.prepareStatement("insert into chunkfiles values(?,?)");
            PStatement.setString(1,fileName);
            PStatement.setString(2,hashName);
            PStatement.executeUpdate();
        } catch (IOException | SQLException e) {
           // e.printStackTrace();
        }
    }

}
