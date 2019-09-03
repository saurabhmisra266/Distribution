package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleAction;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.DBConnector;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @FXML
    public void login(ActionEvent event) throws IOException {
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from users where username=? and paswrd=?");
            ps.setString(1, username.getText());
            ps.setString(2, password.getText());
            String a=username.getText();String b=password.getText();
            if(a.equals("sa24")&&b.equals("1234")){
                Thread registerServer =new RegisterServer(2082);
                registerServer.start();
                AnchorPane pane = FXMLLoader.load(getClass().getResource("sample.fxml"));
                rootPane.getChildren().setAll(pane);
            }
           else {
                ResultSet res = ps.executeQuery();
                if (res.next()) {
                    Thread t1 = new Dashboard(rootPane);
                    Thread t2 = new Server();
                    t1.start();
                    t2.start();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
