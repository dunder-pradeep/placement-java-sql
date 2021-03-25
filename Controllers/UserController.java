package placement.Controllers;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import placement.App;
import placement.Database;
import placement.Job;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML public Pane leftpane;
    @FXML public Pane rightpane;

    public Border focused = new Border(new BorderStroke(Color.web("#583bcd"),BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderWidths.DEFAULT));
    public BorderPane userpage;
    public Label profilenamelabel;
    public MenuItem exitlabel;
    public JFXButton viewposts;
    public AnchorPane parentmiddle;

    public JFXTreeTableView<Job> viewTable;
    public ObservableList<Job> jobList = FXCollections.observableArrayList();
    public JFXButton infoPage;
    public Label sqlQueryLabel;
    public JFXTextField filterPreference;
    public JFXButton applyfilterButton;
    public JFXTextField salaryText;
    public JFXButton applySelected;
    public ImageView profilePicture;
    public Circle clipCircle;
    public JFXButton logoutButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //setting username
        profilenamelabel.setText(App.user.getName());


        viewTableInitialize();

        //get data from db
        try {
            initializePosts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        salaryText.setText("0");

        profilePicture.setClip(new Circle(100,50,50));

    }

    public void leftpaneMouseclick(MouseEvent mouseEvent) {
        toggleBorder(leftpane);
    }

    public void rightpaneMouseclick(MouseEvent mouseEvent) {
        toggleBorder(rightpane);
    }


    public void toggleBorder(Pane p){
        if(p.getBorder()==null)
            p.setBorder(focused);
        else
            p.setBorder(null);
    }


    public void displayposts(MouseEvent mouseEvent) throws SQLException {

        jobList.clear();
        Connection connection = Database.connectToDB();
        String query = "SELECT * FROM COMPANY";
        //setting query to label
        sqlQueryLabel.setText("SQL>"+query);
        //done
        assert connection != null;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while(rs.next()){
           jobList.add(new Job(rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6)));
        }
        TreeItem<Job> root = new TreeItem<>(new Job("","",0,"",""));

        for(int i=0;i<jobList.size();i++)
            root.getChildren().add(i,new TreeItem<Job>(jobList.get(i)));

        viewTable.setRoot(root);
        viewTable.setShowRoot(false);

        connection.close();
    }

    public void initializePosts() throws SQLException {
        Connection connection = Database.connectToDB();
        String query = "SELECT * FROM COMPANY";

        sqlQueryLabel.setText("SQL>"+query);

        assert connection != null;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);


        while(rs.next()){
            jobList.add(new Job(rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6)));
        }
        TreeItem<Job> root = new TreeItem<>(new Job("","",0,"",""));

        for(int i=0;i<jobList.size();i++)
            root.getChildren().add(i,new TreeItem<Job>(jobList.get(i)));



        viewTable.setRoot(root);
        viewTable.setShowRoot(false);

        connection.close();
    }

    public void viewTableInitialize(){
        String styling = "-fx-background-color:#27282c;-fx-text-fill:white;";

        JFXTreeTableColumn<Job,JFXCheckBox> colID= new JFXTreeTableColumn<>("Apply");
          colID.setPrefWidth(75.0);
          colID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Job, JFXCheckBox>, ObservableValue<JFXCheckBox>>() {
              @Override
              public ObservableValue<JFXCheckBox> call(TreeTableColumn.CellDataFeatures<Job, JFXCheckBox> jobJFXCheckBoxCellDataFeatures) {
                  return jobJFXCheckBoxCellDataFeatures.getValue().getValue().jfxCheckBox;
              }
          });
        colID.setStyle(styling);


        JFXTreeTableColumn<Job,String> colName= new JFXTreeTableColumn<>("Company Name");
        colName.setPrefWidth(116.0);
        colName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Job, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Job, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().jobName;
            }
        });
        colName.setStyle(styling);


        JFXTreeTableColumn<Job,String> colSkills= new JFXTreeTableColumn<>("Requirements");
        colSkills.setPrefWidth(136.0);
        colSkills.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Job, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Job, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().jobSkills;
            }
        });
        colSkills.setStyle(styling);

        JFXTreeTableColumn<Job,String> colPay= new JFXTreeTableColumn<>("Payscale");
        colPay.setPrefWidth(81.0);
        colPay.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Job, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Job, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().jobSalary;
            }
        });
        colPay.setStyle(styling);

        JFXTreeTableColumn<Job,String> colDesc= new JFXTreeTableColumn<>("Position offered");
        colDesc.setPrefWidth(142.0);
        //colID.setPrefWidth(10);
        colDesc.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Job, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Job, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().jobTitle;
            }
        });
        colDesc.setStyle(styling);

        JFXTreeTableColumn<Job,String> colDate= new JFXTreeTableColumn<>("Application Due");
        colDate.setPrefWidth(131.0);
        colDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Job, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Job, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().jobDate;
            }
        });
        colDate.setStyle(styling);


        viewTable.getColumns().setAll(colID, colName, colSkills, colPay, colDesc,colDate);
    }




    public void redirectToInfo(MouseEvent mouseEvent) throws IOException {
        Pane temp = FXMLLoader.load(getClass().getResource("../user_info.fxml"));
        userpage.getChildren().setAll(temp);
    }

    public void applyFilter(MouseEvent mouseEvent) throws SQLException {

        jobList.clear();
        sqlQueryLabel.setText("SQL>"+Database.makeQuery(filterPreference.getText(),Integer.parseInt(salaryText.getText()),""));

        jobList = Database.filteredResults(filterPreference.getText(),Integer.parseInt(salaryText.getText()),"");

        TreeItem<Job> root = new TreeItem<>(new Job("","",0,"",""));

        for(int i=0;i<jobList.size();i++)
            root.getChildren().add(i,new TreeItem<Job>(jobList.get(i)));

        viewTable.setRoot(root);
        System.out.println(viewTable.getStyle());
        viewTable.setShowRoot(false);

    }

    public void applySelectedJobs(MouseEvent mouseEvent) {
    }

    public void logoutUser(ActionEvent actionEvent) throws IOException {
        App.user = null;
        App.setAuthUser(false);
        userpage.getChildren().setAll((Pane)FXMLLoader.load(getClass().getResource("../login.fxml")));

    }
}




