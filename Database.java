package placement;


import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class Database {

    public static Connection connectToDB() {
        
        String db_userID = "root";
        String db_password = "root";
        String db_url = "jdbc:mysql://localhost:3306/";
        String db_name = "user";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(db_url+db_name,db_userID,db_password);

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        
    }


    public static String makeQuery(String cname,int salary,String skills){
        boolean queryCombine = false;
        String queryBuild = "SELECT * FROM COMPANY";

        if(!cname.equals("")){
        queryBuild = queryCombine?queryBuild.concat(String.format(" AND NAME='%s'",cname)):queryBuild.concat(String.format(" WHERE NAME='%s'",cname));
        queryCombine = true;
        }

        if(salary!=0){
            queryBuild = queryCombine?queryBuild.concat(String.format(" AND SALARY>'%d'",salary)):queryBuild.concat(String.format(" WHERE SALARY>'%d'",salary));
            queryCombine = true;
        }

        if(!skills.equals("")){
            queryBuild = queryCombine?queryBuild.concat(String.format(" AND SKILL LIKE '%s'",skills)):queryBuild.concat(String.format(" WHERE SKILL LIKE '%s'",skills));
            queryCombine = true;
        }

        return queryBuild;
    }

    public static String makeUpdateQuery(String insertName,String insertPwd,String insertEmail,int insertPhone,String insertSkills){
        return String.format("UPDATE USER SET NAME='%s',PWD='%s' ,EMAIL='%s' ,PHONE=%d ,SKILLS='%s' WHERE ID=%d ",insertName,insertPwd,insertEmail,insertPhone,insertSkills,App.user.getId());
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

        ObservableList<Job> tempList = FXCollections.observableArrayList();
        Connection connection = connectToDB();
        assert connection != null;
        Statement statement = connection.createStatement();

        String query = makeQuery(cname, salary, skills);

        ResultSet rs = statement.executeQuery(query);
        while(rs.next()){
          tempList.add(new Job(rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6)));
        }
        connection.close();
        return tempList;

    }
}
