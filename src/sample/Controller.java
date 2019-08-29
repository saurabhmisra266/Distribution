package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.sql.*;

public class Controller{


    @FXML
    private TextField user;

    @FXML private AnchorPane rootPane;

    @FXML
    private TextField fname;

    @FXML
    private TextField lname;

    @FXML
    private TextField state;

    @FXML
    private TextField cntry;

    @FXML
    private PasswordField paswrd;

    @FXML
    private TextField city;

    @FXML
    private TextField address;

    @FXML
    private TextField email;

    @FXML private ComboBox gender;

    ObservableList<String> gen= FXCollections.observableArrayList("Male","Female");

    public Controller() {
    }

    public void initialize() {
             gender.setItems(gen);
    }
    @FXML
    public void next(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("details.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    @FXML
    public void addData(ActionEvent event){
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement PStatement = connection.prepareStatement("insert into users values(?,?,?,?,?,?,?,?,?,?)");
            PStatement.setString(1, user.getText());
            PStatement.setString(2, fname.getText());
            PStatement.setString(3, lname.getText());
            PStatement.setString(4, paswrd.getText());
            PStatement.setString(5, address.getText());
            PStatement.setString(6, city.getText());
            PStatement.setString(7, state.getText());
            PStatement.setString(8, cntry.getText());
            PStatement.setString(9, email.getText());
            PStatement.setString(10,gender.getValue().toString());
            PStatement.executeUpdate();
        }
        catch (SQLException e1){
            e1.printStackTrace();
        }
    }

}
