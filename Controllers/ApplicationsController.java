package placement.Controllers;

import com.jfoenix.controls.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import placement.App;
import placement.Application;
import placement.Database;
import placement.Job;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ApplicationsController implements Initializable {

    public JFXButton postTab;
    public BorderPane applicationPage;
    public JFXTreeTableView<Application> applicationTable;
    public ObservableList<Application> applications = FXCollections.observableArrayList();
    public JFXButton applyfilterbutton;
    public ChoiceBox genderLabel;
    public JFXChipView skillsLabel;
    public JFXTextField programLabel;
    public JFXTextField cgpaLabel;
    public JFXTextField qualificationLabel;
    public JFXButton logoutButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            treeviewInitialize();
            Connection connection = Database.connectToDB();

            String query = "SELECT * FROM APPLICATION";
            try {

                assert connection != null;
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    applications.add(new Application(rs.getInt("student_id"), rs.getString("name"), rs.getString("gender"), rs.getInt("age"), rs.getString("program"), rs.getString("title"), rs.getString("email"), rs.getString("qualification")));
                }

                TreeItem<Application> root = new TreeItem<>(new Application(0,"","",0,"","","",""));

                for(int i=0;i<applications.size();i++)
                    root.getChildren().add(i,new TreeItem<Application>(applications.get(i)));

                applicationTable.setRoot(root);
                applicationTable.setShowRoot(false);

                connection.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
    }


    public void redirectToPost(MouseEvent mouseEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("../companyPosts.fxml"));
        applicationPage.getChildren().setAll(pane);
    }
    public void treeviewInitialize(){
        String styling = "-fx-background-color:#27282c;-fx-text-fill:white;";

        JFXTreeTableColumn<Application,String> colId= new JFXTreeTableColumn<>("App.no.");
        colId.setPrefWidth(116.0);
        colId.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Application, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Application, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().applicationId;
            }
        });
        colId.setStyle(styling);

        JFXTreeTableColumn<Application,String> colName= new JFXTreeTableColumn<>("Name");
        colName.setPrefWidth(116.0);
        colName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Application, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Application, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().name;
            }
        });
        colName.setStyle(styling);

        JFXTreeTableColumn<Application,String> colGender= new JFXTreeTableColumn<>("Gender");
        colGender.setPrefWidth(116.0);
        colGender.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Application, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Application, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().gender;
            }
        });
        colGender.setStyle(styling);

        JFXTreeTableColumn<Application,String> colAge= new JFXTreeTableColumn<>("Age");
        colAge.setPrefWidth(116.0);
        colAge.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Application, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Application, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().age;
            }
        });
        colAge.setStyle(styling);

        JFXTreeTableColumn<Application,String> colProgram= new JFXTreeTableColumn<>("Program");
        colProgram.setPrefWidth(116.0);
        colProgram.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Application, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Application, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().program;
            }
        });
        colProgram.setStyle(styling);

        JFXTreeTableColumn<Application,String> colTitle= new JFXTreeTableColumn<>("Job Title");
        colTitle.setPrefWidth(116.0);
        colTitle.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Application, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Application, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().jobTitle;
            }
        });
        colTitle.setStyle(styling);

        JFXTreeTableColumn<Application,String> colEmail= new JFXTreeTableColumn<>("Email");
        colEmail.setPrefWidth(116.0);
        colEmail.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Application, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Application, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().email;
            }
        });
        colEmail.setStyle(styling);

        JFXTreeTableColumn<Application,String> colQualification= new JFXTreeTableColumn<>("Qualification");
        colQualification.setPrefWidth(135.0);
        colQualification.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Application, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Application, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().qualification;
            }
        });
        colQualification.setStyle(styling);

        applicationTable.getColumns().setAll(colId,colName,colGender,colAge,colProgram,colTitle,colEmail,colQualification);
    }

    public void applyfilter(MouseEvent mouseEvent) {
        applications.clear();
        Connection connection = Database.connectToDB();

        String query = Database.buildQueryApplications(qualificationLabel.getText(),"",programLabel.getText());
        try {

            assert connection != null;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                applications.add(new Application(rs.getInt("id"), rs.getString("name"), rs.getString("gender"), rs.getInt("age"), rs.getString("program"), rs.getString("title"), rs.getString("email"), rs.getString("qualification")));
            }

            TreeItem<Application> root = new TreeItem<>(new Application(0,"","",0,"","","",""));

            for(int i=0;i<applications.size();i++)
                root.getChildren().add(i,new TreeItem<Application>(applications.get(i)));

            applicationTable.setRoot(root);
            applicationTable.setShowRoot(false);

            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void logoutCompany(ActionEvent actionEvent) throws IOException, InterruptedException {
        App.company = null;
        App.setAuthCompany(false);
        applicationPage.getChildren().setAll((Pane) FXMLLoader.load(getClass().getResource("../login.fxml")));
    }
}
