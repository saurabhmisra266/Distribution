package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.DistributionClient.UploadClient;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class UploadFileController {

    private String emailid;
    public UploadFileController(String emailid){
        this.emailid = emailid;
    }

    @FXML
    public JFXTextField filePath,tags;
    @FXML
    public JFXButton browse,upload,more,back;
    @FXML
    Label status;
    private String path,fileName;
    private File file;
    public void onbrowseclicked()
    {
        Stage stage = (Stage) browse.getScene().getWindow();
        FileChooser fileChooser=new FileChooser();
        file=fileChooser.showOpenDialog(stage);
        if(file!=null)
        {
            filePath.setText(file.getAbsolutePath());
        }
    }

    public void onuploadclicked() throws IOException, ClassNotFoundException {
        UploadClient uploadClient = new UploadClient(file.getAbsolutePath());
        uploadClient.upload();
    }

    public void onbackclicked()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) back.getScene().getWindow();
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
    }
}
