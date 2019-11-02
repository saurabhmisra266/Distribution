package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.DistributionClient.DownloadClient;

import java.io.IOException;

public class DownloadTrackerController {

    private String emailId;
    private String downloadFileName;
    public DownloadClient downloadClient;
    public DownloadTrackerController(String emailId,String downloadFileName,DownloadClient downloadClient){
        this.emailId = emailId;
        this.downloadFileName = downloadFileName;
        this.downloadClient = downloadClient;
    }
    public JFXButton back;
    public JFXTextField file;
    public ProgressBar progressbar;

    @FXML
    public void initialize(){
        file.setText(downloadFileName);
        new Thread(() -> {
            while (downloadClient.downloadedPieces<=10) {
                double progress = (double)downloadClient.downloadedPieces /10;
                System.out.println(downloadClient.downloadedPieces);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    progressbar.setProgress(progress);

                });
            }
        }).start();
    }

    public void onbackclicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) back.getScene().getWindow();
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


}
