package placement;


import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.plaf.nimbus.State;
import java.sql.*;


public class Database {

    public static Connection connectToDB() {
        
        String db_userID = "root";
        String db_password = "root";
        String db_url = "jdbc:mysql://localhost:3306/";
        String db_name = "jobportal";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(db_url+db_name,db_userID,db_password);

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        
    }


    public static String makeQuery(String cname,int salary,String skills) throws SQLException {
        boolean queryCombine = false;
        String queryBuild = "SELECT * FROM jobs";

        if(!cname.equals("")){
            Connection conn = connectToDB();
            assert conn != null;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT COMPANY_ID FROM COMPANY WHERE COMPANY_NAME = '%s' ",cname));
            if(rs.next())
            queryBuild = queryCombine?queryBuild.concat(String.format(" AND COMPANY_ID=%s",rs.getString(1)
            )):queryBuild.concat(String.format(" WHERE COMPANY_ID =%s",rs.getString(1)
            ));
            else
                return "Company not found!!";
            conn.close();
        queryCombine = true;
        }

        if(salary!=0){
            queryBuild = queryCombine?queryBuild.concat(String.format(" AND SALARY>'%d'",salary)):queryBuild.concat(String.format(" WHERE SALARY>'%d'",salary));
            queryCombine = true;
        }

        if(!skills.equals("")){
            queryBuild = queryCombine?queryBuild.concat(String.format(" AND SKILLS LIKE '%s'",skills)):queryBuild.concat(String.format(" WHERE SKILLS LIKE '%s'",skills));
            queryCombine = true;
        }
        return queryBuild;
    }

    public static String makeUpdateQuery(String insertName,String insertPwd,String insertEmail,int insertPhone,String insertSkills){
        return String.format("UPDATE STUDENT SET STUDENT_FIRST_NAME='%s',EMAIL='%s' ,PHONE=%d WHERE STUDENT_ID=%d ",insertName,insertEmail,insertPhone,App.user.getId());
    }


    public static String buildQueryApplications(String qualification,String gender,String program){
        boolean queryCombine = false;
        String queryBuild = "SELECT * FROM APPLICATION";

        if(!qualification.equals("")){
            queryBuild = queryCombine?queryBuild.concat(String.format(" AND QUALIFICATION='%s'",qualification)):queryBuild.concat(String.format(" WHERE QUALIFICATION='%s'",qualification));
            queryCombine = true;
        }


        if(!program.equals("")){
            queryBuild = queryCombine?queryBuild.concat(String.format(" AND PROGRAM LIKE '%s'",program)):queryBuild.concat(String.format(" WHERE PROGRAM LIKE '%s'",program));
            queryCombine = true;
        }

        return queryBuild;
    }

    public static ObservableList<Job> filteredResults(String cname,int salary,String skills) throws SQLException {

        ObservableList<Job> jobList = FXCollections.observableArrayList();
        Connection connection = connectToDB();
        assert connection != null;

        String query = makeQuery(cname, salary, skills);
        if(!query.contains("SELECT"))
            return jobList;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);


        while(rs.next()){
            jobList.add(new Job(rs.getString(2),rs.getString("position"),rs.getInt("salary"),rs.getString(6),rs.getString("apply_deadline"),rs.getString("description"),rs.getString("company_id"),rs.getString("job_id")));
        }
        for (Job job : jobList) {
            rs = st.executeQuery(String.format("SELECT NAME FROM SKILLS JOIN REQUIRED_SKILLS ON SKILL_ID = SKILLS_ID WHERE JOB_ID = %d", Integer.parseInt(job.jobId.getValue())));
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append(rs.getString(1)).append(" ");
            }
            job.jobSkills = new SimpleStringProperty(sb.toString());
        }
        for (Job job:
                jobList) {
            rs = st.executeQuery("SELECT COMPANY_NAME FROM COMPANY WHERE COMPANY_ID = "+job.jobcompID.getValue());
            rs.next();
            job.jobName = new SimpleStringProperty(rs.getString(1));
            job.jobTitle = new SimpleStringProperty(job.jobTitle.getValue().concat(String.format(" , %s",job.jobName.getValue())));
        }

        connection.close();
        return jobList;

    }
}
