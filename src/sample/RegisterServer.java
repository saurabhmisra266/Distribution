package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegisterServer {

    public static void main(String[] args) throws IOException,ClassNotFoundException,SQLException {
        ServerSocket ss = null;

            ss = new ServerSocket(2082);
        System.out.println("Server Started");
        while(true){
            Socket s= null;
                s = ss.accept();
                System.out.println("Client connected for registration");
                InputStream in = null;
                DataInputStream din  = new DataInputStream(s.getInputStream());
                String received = din.readUTF();
                System.out.println(received);
            Connection connection = DBConnector.getConnection();
                if(received.equals("Register")) {
                    in = s.getInputStream();
                    ArrayList<String> registerValues  = new ArrayList<String>();
                    ObjectInputStream ob = new ObjectInputStream(in);
                    registerValues = (ArrayList<String>) ob.readObject();
                    //for (String z : registerValues) System.out.println(z);
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
                }
                else{
                    List<String> ip = new ArrayList<String>();
                    Statement statement =null;
                    String query= "select * from users";
                    statement = connection.createStatement();
                    ResultSet res = statement.executeQuery(query);
                    while(res.next()){
                        ip.add(res.getString("ip"));
                    }
                    ArrayList<String> ips  = new ArrayList<String>();
                    ips.addAll(ip);
                    OutputStream out  = s.getOutputStream();
                    ObjectOutputStream objectOutputStream  = new ObjectOutputStream(out);
                    objectOutputStream.writeObject(ips);
                    for(String z:ip){
                        System.out.println(z);
                    }
                }
            }
        }
    }
