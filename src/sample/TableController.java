package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
