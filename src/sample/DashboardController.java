package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import sample.CentralServer.MainServer;
import sample.DistributionClient.DownloadClient;
import sample.DistributionClient.DownloadPieceRequest;
import sample.DistributionClient.UploadClient;

import java.io.*;

import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    private String emailId;
    public DashboardController(String emailId){
        this.emailId = emailId;
    }
    @FXML
    public JFXListView downloadedfiles, sharedfiles;
    public JFXTextField firstname, lastname, email, phone;
    public JFXButton sharefile,download,logout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String ip = null;
        MainServer mainServer = new MainServer();
        ArrayList<String> userDetails  = new ArrayList<String>();
        try {
            ip = mainServer.getIP();
            Socket socket = new Socket(ip, 2082);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("UserDetailsRequest");
            dataOutputStream.writeUTF(emailId);
            InputStream in = socket.getInputStream();
            ObjectInputStream ob = new ObjectInputStream(in);
            userDetails = (ArrayList<String>) ob.readObject();
            firstname.setText(userDetails.get(0));
            lastname.setText(userDetails.get(1));
            email.setText(userDetails.get(2));
            phone.setText(userDetails.get(3));
        } catch (IOException| ClassNotFoundException e) {
            e.printStackTrace();
        }


        String path = System.getProperty("user.home") + "//Downloads";
            File downloadedFiles = new File(path, "downloadedFiles.txt");
            File uploadedFiles = new File(path, "uploadedFiles.txt");
            try {
                BufferedReader hp = new BufferedReader(new FileReader(downloadedFiles));
                for (String fileName; (fileName = hp.readLine()) != null; ) {
                    downloadedfiles.getItems().add(fileName);
                }
                hp.close();
            } catch (IOException e) {
                downloadedfiles.getItems().add("Empty");
            }
            try{
                BufferedReader br = new BufferedReader(new FileReader(uploadedFiles));
                for (String fileName; (fileName = br.readLine()) != null; ) {
                    sharedfiles.getItems().add(fileName);
                }
            } catch (IOException e) {
                sharedfiles.getItems().add("Empty");
            }


    }

    public void onshareclicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) sharefile.getScene().getWindow();
                AnchorPane anchorPane = null;
                try {
                    UploadFileController uploadFileController =new UploadFileController(emailId);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("uploadFile.fxml"));
                    loader.setController(uploadFileController);
                    anchorPane= loader.load();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                primaryStage.setScene(new Scene(anchorPane, 1081, 826));
            }
        });
    }

    public void ondownloadclicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) download.getScene().getWindow();
                AnchorPane anchorPane = null;
                try {
                    SearchFileController searchFileController =new SearchFileController(emailId);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("searchFile.fxml"));
                    loader.setController(searchFileController);
                    anchorPane= loader.load();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                primaryStage.setScene(new Scene(anchorPane, 1081, 826));
            }
        });
    }

    @FXML
    public void onlogoutclicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) firstname.getScene().getWindow();
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

    public void onfiledclicked(MouseEvent mouseEvent){
    }
    public void onfilesclicked(MouseEvent mouseEvent){
    }
}
