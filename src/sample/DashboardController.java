package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class DashboardController {

    @FXML
    public void upload(ActionEvent event) throws IOException, NoSuchAlgorithmException, SQLException,ClassNotFoundException{
        FileChooser fileChooser =new FileChooser();
        fileChooser.setInitialDirectory(new File("src"));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        Client client =new Client(file.getAbsolutePath());
        System.out.println(file.getAbsolutePath());
        client.upload();
    }

    @FXML
    public void download(){

    }
}
