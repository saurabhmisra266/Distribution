package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.ServerSocket;

public class Dashboard extends Thread {
    AnchorPane rootPane;

    public Dashboard(AnchorPane rootPane){
        this.rootPane = rootPane;
    }

    @Override
    public void run() {
        try {
            openDashboard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   public void openDashboard() throws IOException {
       Platform.runLater(new Runnable() {
           @Override public void run() {
               AnchorPane pane = null;
               try {
                   pane = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
               } catch (IOException e) {
                   e.printStackTrace();
               }
               rootPane.getChildren().setAll(pane);
           }
       });

    }
}
