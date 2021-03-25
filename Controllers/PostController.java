package placement.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import placement.App;
import placement.Database;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PostController implements Initializable {
    public JFXTextField titleLabel;
    public JFXTextArea descLabel;
    public JFXTextField salaryLabel;
    public JFXDatePicker deadlineLabel;
    public JFXButton applicationTab;
    public BorderPane companyPage;
    public JFXButton postButton;
    public JFXButton logoutButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void redirectToApplications(MouseEvent mouseEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("../company-application.fxml"));
        companyPage.getChildren().setAll(pane);

    }

    public void postJob(MouseEvent mouseEvent) throws SQLException {
        String insertName = titleLabel.getText();
        String insertDesc = descLabel.getText();
        String insertSal = salaryLabel.getText();
        String insertDate = deadlineLabel.getValue().toString();

        Connection connection = Database.connectToDB();
        assert connection != null;
        connection.createStatement().executeUpdate(String.format("INSERT INTO JOBS VALUES(10,'%s','%s','%s',%d,'%s','%s',%d)",App.company.companyName,insertName,insertDesc,Integer.parseInt(insertSal),insertDate,"",0));

        connection.close();
    }

    public void logoutCompany(ActionEvent actionEvent) throws IOException {
        App.company = null;
        App.setAuthCompany(false);
        companyPage.getChildren().setAll((Pane) FXMLLoader.load(getClass().getResource("../login.fxml")));
    }
}
