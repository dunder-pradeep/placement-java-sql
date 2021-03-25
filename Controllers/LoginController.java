package placement.Controllers;

import com.gluonhq.charm.glisten.control.ToggleButtonGroup;
import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Pane;
import placement.Database;
import placement.App;
import placement.User;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML public Button submit;
    @FXML public TextField userID;
    @FXML public PasswordField passwordID;
    @FXML public Pane loginpage;
    public ToggleGroup toggleGroup = new ToggleGroup();
    public JFXRadioButton toggleStudent;
    public JFXRadioButton toggleAdmin;
    public JFXRadioButton toggleCompany;
    public JFXAutoCompletePopup popup;

    public void handleSubmit(MouseEvent mouseEvent) throws Exception{
        String UserID = userID.getText();
        String PasswordID =  passwordID.getText();

        String SQL;
        ResultSet rs;

        Connection connection = Database.connectToDB();
        Statement statement = connection.createStatement();

        JFXRadioButton selectedToggle = (JFXRadioButton) toggleGroup.getSelectedToggle();

        if(selectedToggle==toggleStudent) {
            SQL = String.format("SELECT * FROM USER WHERE ID='%s' AND PWD=%s", userID.getText(), passwordID.getText());
            rs = statement.executeQuery(SQL);
            if (rs.next()) {
                App.setAuthUser(true);
                App.newUser(rs.getInt("id"), rs.getString("name"), "", rs.getString("pwd"), Integer.parseInt(rs.getString("phone")), rs.getString("skills"), rs.getString("email"), rs.getString("applications"));
                Pane pane = FXMLLoader.load(getClass().getResource("../user_info.fxml"));
                loginpage.getChildren().setAll(pane);
                return;
            }
        }
        if(selectedToggle==toggleCompany) {
            //verify company..
            SQL = String.format("SELECT * FROM COMPANY WHERE ID='%s' AND PWD=%s",UserID,PasswordID );
            rs = statement.executeQuery(SQL);
            if (rs.next()) {
                App.setAuthCompany(true);
                App.newCompany(rs.getString("name"), rs.getString("id"),"laterb",rs.getString("jobs"));
                Pane pane = FXMLLoader.load(getClass().getResource("../companyPosts.fxml"));
                loginpage.getChildren().setAll(pane);
                return;
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleAdmin.setToggleGroup(toggleGroup);
        toggleCompany.setToggleGroup(toggleGroup);
        toggleStudent.setToggleGroup(toggleGroup);
        toggleGroup.selectToggle(null);
        toggleGroup.selectToggle(toggleStudent);
        }

    public void redirectRegister(ActionEvent actionEvent) throws IOException {
        loginpage.getChildren().setAll((Pane) FXMLLoader.load(getClass().getResource("../register.fxml")));
    }
}
