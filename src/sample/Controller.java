package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {


    public JFXTextField email;
    public JFXButton login;
    public JFXButton back;
    public JFXTextField firstname;
    public JFXTextField lastname;
    public Label status;
    public JFXTextField contact;
    public JFXPasswordField password;


    InetAddress localhost = InetAddress.getLocalHost();
    String ip=(localhost.getHostAddress()).trim();


    public Controller() throws UnknownHostException {
    }

    public void onsubmitclicked(ActionEvent actionEvent) throws IOException {
        List<String> a = Arrays.asList(firstname.getText(), lastname.getText(),password.getText(),email.getText(),ip,contact.getText());
        ArrayList<String> registerValues= new ArrayList<String>();
        registerValues.addAll(a);
        Socket s = new Socket(ip,2082);
        DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
        dataOutputStream.writeUTF("Register");
        OutputStream outputStream = s.getOutputStream();
        ObjectOutputStream ob = new ObjectOutputStream(outputStream);
        ob.writeObject(registerValues);
    }
    public void onbackclicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) back.getScene().getWindow();
                Parent root = null;
                try {

                    root = FXMLLoader.load(getClass().getResource("login.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                primaryStage.setScene(new Scene(root, 1081, 826));
            }
        });
    }

}
