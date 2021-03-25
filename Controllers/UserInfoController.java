package placement.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import placement.App;
import placement.Database;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UserInfoController implements Initializable {
    public BorderPane userpage;
    public Pane leftpane;
    public Label profilenamelabel;
    public JFXButton viewposts;
    public AnchorPane parentmiddle;
    public JFXTextField emailLabel;
    public JFXTextField phoneLabel;
    public JFXTextField passwordLabel;
    public JFXTextField skillsLabel;
    public JFXTextField nameLabel;
    public JFXButton saveChangesButton;
    public Label sqlLabel;
    public ImageView profilePicture;
    public JFXButton logoutButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //setting username
        nameLabel.setText(App.user.getName());
        profilenamelabel.setText(App.user.getName());
        emailLabel.setText(App.user.getEmail());
        passwordLabel.setText(App.user.getPassword());
        skillsLabel.setText(App.user.getSkills());
        phoneLabel.setText(String.valueOf(App.user.getPhone()));

        sqlLabel.setText(String.format("SQL>SELECT * FROM USER WHERE ID=%d",App.user.getId()));

        profilePicture.setClip(new Circle(100,50,50));
    }

    public void leftpaneMouseclick(MouseEvent mouseEvent) {
    }

    public void displayposts(MouseEvent mouseEvent) throws IOException {
        Pane temp = FXMLLoader.load(getClass().getResource("../user.fxml"));
        userpage.getChildren().setAll(temp);
    }

    public void saveChanges(MouseEvent mouseEvent) throws SQLException {
        //initialize with getters
        String insertName = nameLabel.getText();
        String insertPwd = passwordLabel.getText();
        int insertPhone = Integer.parseInt(phoneLabel.getText());
        String insertSkills = skillsLabel.getText();
        String insertEmail = emailLabel.getText();
        //call to querybuild
        String query = Database.makeUpdateQuery(insertName,insertPwd,insertEmail,insertPhone,insertSkills);

        //updating query label;
        sqlLabel.setText(query);


        Connection connection = Database.connectToDB();
        Statement statement = connection.createStatement();

        statement.executeUpdate(query);

        connection.close();
    }

    public void logoutUser(ActionEvent actionEvent) throws IOException {
        App.user = null;
        App.setAuthUser(false);
        userpage.getChildren().setAll((Pane)FXMLLoader.load(getClass().getResource("../login.fxml")));

    }
}
