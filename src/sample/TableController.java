package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TableController implements Initializable {

    @FXML  private TableView<ModelTable> table;
    @FXML private TableColumn<ModelTable,String> cname;
    @FXML private TableColumn<ModelTable,String> cemail;
    @FXML private TableColumn<ModelTable,String> ccity;

     @FXML
    public void open(ActionEvent e)throws IOException {
       DirectoryChooser directoryChooser = new DirectoryChooser();
       directoryChooser.setInitialDirectory(new File("src"));
         Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
         File selectedDirectory = directoryChooser.showDialog(stage);
       System.out.println(selectedDirectory.getAbsolutePath());
   }
    ObservableList<ModelTable> obList=FXCollections.observableArrayList();


    public void initialize(URL location, ResourceBundle resources) {
        try {

            Connection con=DBConnector.getConnection();

            ResultSet rs=con.createStatement().executeQuery("select username,email,city from users");
            while(rs.next()){
                obList.add(new ModelTable(rs.getString("username"),rs.getString("email"),rs.getString("city")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cname.setCellValueFactory(new PropertyValueFactory<>("name"));
        cemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        ccity.setCellValueFactory(new PropertyValueFactory<>("city"));
        table.setItems(obList);
    }

}