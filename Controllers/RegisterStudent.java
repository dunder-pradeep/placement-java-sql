package placement.Controllers;

import com.gluonhq.charm.glisten.control.ToggleButtonGroup;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import placement.Database;

import javax.swing.plaf.basic.BasicBorders;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterStudent implements Initializable {
    public JFXButton registerButton;
    public JFXButton loginButton;
    public BorderPane registerPage;
    public ToggleGroup btngroup = new ToggleGroup();
    public JFXRadioButton companyToggle;
    public JFXRadioButton studentToggle;
    public JFXTextField nameLabel;
    public JFXTextField emailLabel;
    public JFXTextField collegeLabel;
    public JFXTextField cgpaLabel;
    public JFXTextField cgpa3Label;
    public JFXTextField cgpa2Label;
    public JFXTextField programLabel;
    public JFXTextField passwordLabel;

    public void registerStudent(ActionEvent actionEvent) throws SQLException {
        String name = nameLabel.getText();
        String email = emailLabel.getText();
        String college = collegeLabel.getText();
        String cgpa = cgpaLabel.getText();
        String cgpa10 = cgpa2Label.getText();
        String cgpa12 = cgpa3Label.getText();
        String program = programLabel.getText();
        String pwd = passwordLabel.getText();

        Connection connection = Database.connectToDB();
        assert connection != null;
        connection.createStatement().executeUpdate(String.format("INSERT INTO STUDENT VALUES('%s','%s','%s','%d','%d','%d','%s','%s')",name,email,college,Integer.parseInt(cgpa),Integer.parseInt(cgpa10),Integer.parseInt(cgpa12),program,pwd));

        connection.close();

    }

    public void redirectLogin(ActionEvent actionEvent) throws IOException {
        registerPage.getChildren().setAll((Pane) FXMLLoader.load(getClass().getResource("../login.fxml")));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        companyToggle.setToggleGroup(btngroup);
        studentToggle.setToggleGroup(btngroup);
        btngroup.selectToggle(studentToggle);

    }

    public void redirectCompany(MouseEvent mouseEvent) throws IOException {
        registerPage.getChildren().setAll((Pane) FXMLLoader.load(getClass().getResource("../registercompany.fxml")));
    }
}
