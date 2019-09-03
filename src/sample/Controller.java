package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {


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

    InetAddress localhost = InetAddress.getLocalHost();
    String ip=(localhost.getHostAddress()).trim();



    ObservableList<String> gen= FXCollections.observableArrayList("Male","Female");

    public Controller() throws UnknownHostException {
    }

    public void shutdown(){
        System.out.println("System closing.");
    }


    public void initialize() {
             gender.setItems(gen);
    }
    @FXML
    public void next(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("login.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    @FXML
    public void addData(ActionEvent event) throws IOException {
        List<String> a = Arrays.asList(user.getText(), fname.getText(), lname.getText(), state.getText(), cntry.getText(), paswrd.getText(),
                city.getText(), address.getText(), email.getText(), gender.getValue().toString(), ip);
        ArrayList<String> registerValues= new ArrayList<String>();
        registerValues.addAll(a);
        Socket s = new Socket("192.168.31.44",2082);
        OutputStream outputStream = s.getOutputStream();
        ObjectOutputStream ob = new ObjectOutputStream(outputStream);
        ob.writeObject(registerValues);
    }

}
