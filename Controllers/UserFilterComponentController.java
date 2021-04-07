package placement.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import placement.Database;
import placement.Job;

import java.sql.SQLException;

public class UserFilterComponentController {
    public JFXTextField salaryText;
    public JFXButton applyfilterButton;
    public JFXTextField filterPreference;
    public ObservableList<Job> jobList = FXCollections.observableArrayList();
    public Label sqlQueryLabel;
    public JFXTreeTableView viewTable;
    public void applyFilter(MouseEvent mouseEvent) throws SQLException {
        jobList.clear();
        sqlQueryLabel.setText("SQL>"+ Database.makeQuery(filterPreference.getText(),Integer.parseInt(salaryText.getText()),""));

        jobList = Database.filteredResults(filterPreference.getText(),Integer.parseInt(salaryText.getText()),"");

        TreeItem<Job> root = new TreeItem<>(new Job("","",0,"","","","",""));

        for(int i=0;i<jobList.size();i++)
            root.getChildren().add(i,new TreeItem<Job>(jobList.get(i)));

        viewTable.setRoot(root);
        System.out.println(viewTable.getStyle());
        viewTable.setShowRoot(false);
    }
}
