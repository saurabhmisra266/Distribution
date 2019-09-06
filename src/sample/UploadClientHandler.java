package sample;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UploadClientHandler extends  Thread {
    private Socket socket;
    private Connection connection;
    public  UploadClientHandler(Socket socket, Connection connection){
        this.socket = socket;
        this.connection = connection;
    }
    @Override
    public void run(){


        List<String> ip = new ArrayList<String>();
        String query= "select * from users";
        try {
            Statement statement =null;
            statement = connection.createStatement();
            ResultSet res = statement.executeQuery(query);
            while(res.next()){
                ip.add(res.getString("ip"));
            }
            ArrayList<String> ips  = new ArrayList<String>();
            ips.addAll(ip);
            InputStream in = socket.getInputStream();
            OutputStream out  = socket.getOutputStream();
            DataInputStream din = new DataInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream  = new ObjectOutputStream(out);
            objectOutputStream.writeObject(ips);
//                    for(String z:ip){
//                        System.out.println(z);
            String hashFile = din.readUTF();
            String fileName = din.readUTF();
            System.out.println(hashFile);
            in  = socket.getInputStream();
            File file = new File("E:\\",hashFile);
            out = new FileOutputStream(file);
            int bytesRead;
            byte buffer[] = new byte[1024];
            while((bytesRead=in.read(buffer))!=-1){
                out.write(buffer,0,bytesRead);
            }
            System.out.println(fileName);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into uploadedfiles values(?,?)");
            preparedStatement.setString(1,fileName);
            preparedStatement.setString(2,hashFile);
            preparedStatement.executeUpdate();
            out.flush();
            out.close();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
