package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import sample.CentralServer.MainServer;
import sample.DistributionClient.DownloadClient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SearchFileController {


    @FXML
    public JFXTextField downloadFileName;
    public JFXListView files;
    public JFXButton back;
    public JFXButton download;

    private String emailId;
    public SearchFileController(String emailId) {
        this.emailId = emailId;
    }
    @FXML
    public void initialize(){
        ArrayList<String> fileNames = new ArrayList<String>();
        try {
            MainServer mainServer = new MainServer();
            String ip = mainServer.getIP();
            Socket socket = new Socket(ip,2082);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("Retreive FileNames");
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            fileNames = (ArrayList<String>)objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(int i = 0 ; i<fileNames.size(); i++){
            System.out.println(fileNames.get(i));
        }
        files.getItems().addAll(fileNames);
        TextFields.bindAutoCompletion(downloadFileName,fileNames);
    }

    public void ondownloadclicked(ActionEvent actionEvent) throws IOException, InterruptedException {
        DownloadClient downloadClient = new DownloadClient(downloadFileName.getText());
        downloadClient.download();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage)download.getScene().getWindow();
                AnchorPane anchorPane = null;
                try {
                    DownloadTrackerController downloadTrackerController = new DownloadTrackerController(emailId,downloadFileName.getText(),downloadClient);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("downloadTracker.fxml"));
                    loader.setController(downloadTrackerController);
                    anchorPane= loader.load();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                primaryStage.setScene(new Scene(anchorPane, 1081, 826));
            }
        });
    }

    public void onbackclicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) back.getScene().getWindow();
                AnchorPane anchorPane = null;
                try {
                    DashboardController dashboardController = new DashboardController(emailId);
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
    }
}
