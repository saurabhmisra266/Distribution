package sample;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginClentHandler extends  Thread {
    private Socket socket;
    private Connection connection;
    public LoginClentHandler(Socket socket,Connection connection){
        this.socket = socket;
        this.connection = connection;
    }
    @Override
    public void run(){

        ArrayList<String> loginValues  = new ArrayList<String>();
        try {
            InputStream in = socket.getInputStream();
            ObjectInputStream ob = new ObjectInputStream(in);
            loginValues = (ArrayList<String>) ob.readObject();
            PreparedStatement ps = connection.prepareStatement("select * from users where username=? and paswrd=?");
            ps.setString(1, loginValues.get(0));
            ps.setString(2, loginValues.get(1));
            //String a=username.getText();String b=password.getText();
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("1");
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
