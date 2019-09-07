package sample;

import java.io.*;
import java.net.Socket;
import java.sql.*;

public class DownloadClientHandler extends Thread {

    private Socket socket;
    private Connection connection;
    public DownloadClientHandler(Socket socket,Connection connection){
        this.socket = socket;
        this.connection = connection;
    }
    @Override
    public  void run(){
        String fileName;
        try {
            DataInputStream dataInputStream =new DataInputStream(socket.getInputStream());
            fileName = dataInputStream.readUTF();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from uploadedfiles where fileName = ?");
            preparedStatement.setString(1,fileName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String fileHash = resultSet.getString("fileHash");
                OutputStream outputStream = socket.getOutputStream();
                File file = new File("E://",fileHash);
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream hp = new BufferedInputStream(fileInputStream);
                byte buffer[] = new byte[(int)file.length()];
                hp.read(buffer,0,buffer.length);
                outputStream.write(buffer,0,buffer.length);
                outputStream.flush();
                outputStream.close();
                hp.close();
                fileInputStream.close();

            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }
}
