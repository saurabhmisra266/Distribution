package sample.CentralServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegisterClientHandler extends Thread {
          private  Socket s;
          private Connection connection;
          public  RegisterClientHandler(Socket s,Connection connection){
              this.s = s;
              this.connection = connection;
          }
    @Override
    public void run(){
        ArrayList<String> registerValues  = new ArrayList<String>();
        try {
            InputStream in = s.getInputStream();
            ObjectInputStream ob = new ObjectInputStream(in);

            registerValues = (ArrayList<String>) ob.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        //for (String z : registerValues) System.out.println(z);
        PreparedStatement PStatement = null;
        try {
            PStatement = connection.prepareStatement("insert into users values(?,?,?,?,?,?)");
            PStatement.setString(1, registerValues.get(0));
            PStatement.setString(2, registerValues.get(1));
            PStatement.setString(3, registerValues.get(2));
            PStatement.setString(4, registerValues.get(3));
            PStatement.setString(5, registerValues.get(4));
            PStatement.setString(6, registerValues.get(5));
            PStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
