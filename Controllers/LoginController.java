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

    public void handleSubmit(MouseEvent mouseEvent) throws Exception{
        JFXRadioButton selectedToggle = (JFXRadioButton) toggleGroup.getSelectedToggle();
        String UserID = userID.getText();
        String PasswordID =  passwordID.getText();
        int type = selectedToggle==toggleStudent?0:selectedToggle==toggleCompany?1:2;

        String SQL;
        ResultSet rs;

        Connection connection = Database.connectToDB();
        assert connection != null;
        Statement statement = connection.createStatement();


        SQL = String.format("SELECT * FROM ACCOUNTS WHERE ACCOUNT_ID=%d AND PASSWORD=%s AND TYPE=%d", Integer.parseInt(userID.getText()), passwordID.getText(),type);
        rs = statement.executeQuery(SQL);
        if(rs.next()){
            if(type==0){
                //student login
                SQL = String.format("SELECT * FROM STUDENT WHERE ACCOUNT_ID=%d",Integer.parseInt(userID.getText()));
                rs = statement.executeQuery(SQL);
                if(rs.next()) {
                    App.setAuthUser(true);
                    App.newUser(rs.getInt("student_id"), rs.getString("student_first_name"), rs.getString("student_last_name"), passwordID.getText(), Integer.parseInt(rs.getString("phone")),"", rs.getString("email"), "");

                    rs = statement.executeQuery("SELECT name FROM skills JOIN has_skills WHERE has_skills.student_id = "+App.user.getId());
                    StringBuilder newString = new StringBuilder();
                    while(rs.next()) {
                        newString.append(rs.getString("name")).append(" ");
                        System.out.println(rs.getString(1));
                    }
                    App.user.setSkills(newString.toString());

                    Pane pane = FXMLLoader.load(getClass().getResource("../user_info.fxml"));
                    loginpage.getChildren().setAll(pane);
                    return;
                }
                else {
                    System.out.println("login failed");
                }
            }
            else if (type==1){
                //company login
                SQL = String.format("SELECT * FROM COMPANY WHERE ACCOUNT_ID=%d",Integer.parseInt(userID.getText()));
                rs = statement.executeQuery(SQL);
                if(rs.next()){
                App.setAuthCompany(true);
                App.newCompany(rs.getString("company_name"), rs.getString("company_id"),"laterb","");
                Pane pane = FXMLLoader.load(getClass().getResource("../companyPosts.fxml"));
                loginpage.getChildren().setAll(pane);
                return;}
                else {
                    System.out.println("login failed");
                }
            }
            else {
                System.out.println("admin not yet created");
            }
        }

        connection.close();
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
