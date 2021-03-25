package placement;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Application extends RecursiveTreeObject<Application> {
    public StringProperty applicationId;
    public StringProperty name;
    public StringProperty gender;
    public StringProperty age;
    public StringProperty program;
    public StringProperty jobTitle;
    public StringProperty email;
    public StringProperty qualification;


    public Application(int applicationId, String name, String gender, int age, String program, String jobTitle, String email, String qualification) {
        this.applicationId =  new SimpleStringProperty(String.valueOf(applicationId));
        this.name = new SimpleStringProperty(name);
        this.gender =  new SimpleStringProperty(gender);
        this.age = new SimpleStringProperty(String.valueOf(age));
        this.program = new SimpleStringProperty(program);
        this.jobTitle =  new SimpleStringProperty(jobTitle);
        this.email = new SimpleStringProperty(email);
        this.qualification = new SimpleStringProperty(qualification);
    }


}
