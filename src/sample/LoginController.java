package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleAction;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.DBConnector;

import java.io.IOException;
import java.net.ServerSocket;
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
            System.out.print(ps.toString());
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("start.fxml"));
                rootPane.getChildren().setAll(pane);
                while(true) {
                    ServerSocket ss = new ServerSocket(2081);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
