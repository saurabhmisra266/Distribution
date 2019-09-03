package sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegisterServer extends Thread  {

    int port;
    public RegisterServer(int port){
        this.port =  port;
    }
    @Override
    public void run() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server Started");
        while(true){
            Socket s= null;
            try {
                s = ss.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Client connected for registration");
            ArrayList<String> registerValues  = new ArrayList<String>();
            InputStream in = null;
                try {
                    in = s.getInputStream();
                    ObjectInputStream ob = new ObjectInputStream(in);
                    registerValues = (ArrayList<String>) ob.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                for (String z : registerValues) System.out.println(z);
                try {
                    Connection connection = DBConnector.getConnection();


                    PreparedStatement PStatement = connection.prepareStatement("insert into users values(?,?,?,?,?,?,?,?,?,?,?)");
                    PStatement.setString(1, registerValues.get(0));
                    PStatement.setString(2, registerValues.get(1));
                    PStatement.setString(3, registerValues.get(2));
                    PStatement.setString(4, registerValues.get(3));
                    PStatement.setString(5, registerValues.get(4));
                    PStatement.setString(6, registerValues.get(5));
                    PStatement.setString(7, registerValues.get(6));
                    PStatement.setString(8, registerValues.get(7));
                    PStatement.setString(9, registerValues.get(8));
                    PStatement.setString(10, registerValues.get(9));
                    PStatement.setString(11, registerValues.get(10));
                    PStatement.executeUpdate();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
