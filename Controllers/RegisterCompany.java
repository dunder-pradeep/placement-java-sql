package placement.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import placement.Database;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterCompany implements Initializable {
    public JFXButton registerButton;
    public JFXButton loginRedirectButton;
    public BorderPane registerPage;
    public JFXRadioButton companyToggle;
    public JFXRadioButton studentToggle;
    public JFXTextField nameLabel;
    public JFXTextField emailLabel;
    public JFXTextField useridLabel;
    public JFXTextField passwordLabel;
    public JFXTextArea addressLabel;
    public JFXButton loginButton;
    public ToggleGroup btngroup = new ToggleGroup();


    public void registerStudent(ActionEvent actionEvent) {
    }

    public void redirectLogin(ActionEvent actionEvent) throws IOException {
        registerPage.getChildren().setAll((Pane) FXMLLoader.load(getClass().getResource("../login.fxml")));

    }

    public void registerCompany(ActionEvent actionEvent) throws SQLException {
        String name = nameLabel.getText();
        String email = emailLabel.getText();
        String address = addressLabel.getText();
        String pwd = passwordLabel.getText();


        Connection connection = Database.connectToDB();
        assert connection != null;
        connection.createStatement().executeUpdate(String.format("INSERT INTO COMPANY VALUES('%s','%s','%s','%s')",name,email,address,pwd));

        connection.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        companyToggle.setToggleGroup(btngroup);
        studentToggle.setToggleGroup(btngroup);
        btngroup.selectToggle(companyToggle);
    }

    public void redirectStudent(ActionEvent actionEvent) throws IOException {
        registerPage.getChildren().setAll((Pane) FXMLLoader.load(getClass().getResource("../register.fxml")));

    }
}
