package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleAction;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.DBConnector;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginController {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @FXML
    public void login(ActionEvent event) throws IOException {

        List<String> a = Arrays.asList(username.getText(),password.getText());
        ArrayList<String> registerValues= new ArrayList<String>();
        registerValues.addAll(a);
        Socket s = new Socket("192.168.31.44",2082);
        DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
        dataOutputStream.writeUTF("Login");
        OutputStream outputStream = s.getOutputStream();
        ObjectOutputStream ob = new ObjectOutputStream(outputStream);
        ob.writeObject(registerValues);
        DataInputStream dataInputStream = new DataInputStream(s.getInputStream());
        String received = dataInputStream.readUTF();
        System.out.println(received);
        if(received.equals("1")){
            Thread t1 = new Dashboard(rootPane);
            Thread t2 = new Server();
            t1.start();
            t2.start();
        }

    }

}
