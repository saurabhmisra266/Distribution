package sample.CentralServer;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDetailsRequest extends Thread{
    Socket socket;
    Connection connection;
    public UserDetailsRequest(Socket socket,Connection connection){
        this.socket = socket;
        this.connection = connection;
    }

    @Override
    public void run(){
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String emailId = dataInputStream.readUTF();
            PreparedStatement ps = connection.prepareStatement("select * from users where email=?");
            ps.setString(1,emailId);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                List<String> a = Arrays.asList(res.getString("fname"),res.getString("lname"),res.getString("email")
                        ,res.getString("contact"));
                OutputStream outputStream = socket.getOutputStream();
                ArrayList<String> userDetails= new ArrayList<String>();
                userDetails.addAll(a);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(userDetails);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }


    }

}
