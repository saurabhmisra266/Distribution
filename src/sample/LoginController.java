package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import sample.CentralServer.MainServer;
import sample.Dashboard;
import sample.DistributionServer.Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginController {

    public JFXTextField email;
    public JFXPasswordField password;
    public Label status;

    @FXML
    JFXButton login,signup;


    public void onloginclicked(ActionEvent event) throws IOException {
        String emailid = this.email.getText();
        String password = this.password.getText();
        if(password==null){
            status.setText("Password : Null");
        }
        List<String> a = Arrays.asList(emailid,password);
        ArrayList<String> loginValues= new ArrayList<String>();
        loginValues.addAll(a);
        MainServer mainServer = new MainServer();
        String ip= mainServer.getIP();
        Socket s = new Socket(ip,2082);
        DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
        dataOutputStream.writeUTF("Login");
        OutputStream outputStream = s.getOutputStream();
        ObjectOutputStream ob = new ObjectOutputStream(outputStream);
        ob.writeObject(loginValues);
        DataInputStream dataInputStream = new DataInputStream(s.getInputStream());
        String received = dataInputStream.readUTF();
        System.out.println(received);
        if(received.equals("1")){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Stage primaryStage = (Stage) signup.getScene().getWindow();
                    AnchorPane anchorPane = null;
                    try {
                        DashboardController dashboardController = new DashboardController(emailid);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                        loader.setController(dashboardController);
                        anchorPane= loader.load();
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }
                    primaryStage.setScene(new Scene(anchorPane, 1303, 961));
                }
            });
            Thread t2 = new Server();
            t2.start();
        }
        else{
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    status.setText("Error");
                }
            });
        }

    }
    public void onsignupclicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) signup.getScene().getWindow();
                Parent root = null;
                try {

                    root = FXMLLoader.load(getClass().getResource("signup.fxml"));
                }catch(IOException e){
                    e.printStackTrace();
                }
                primaryStage.setScene(new Scene(root, 1081, 826));

            }
        });
    }
    public void onexitclicked(ActionEvent actionEvent) {
        System.exit(0);
    }

}
