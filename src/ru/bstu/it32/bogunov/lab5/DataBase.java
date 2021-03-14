package ru.bstu.it32.bogunov.lab5;

import java.sql.*;

public class DataBase {
    public static Statement connect() {

        String userName = "root";
        String password = "1234";
        String URL = "jdbc:mysql://localhost:3306/SchoolsTest";
        Statement statement = null;
        Connection connection;

        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) { e.printStackTrace(); }

        try{
            connection = DriverManager.getConnection(URL, userName, password);
            statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Schools( " +
                    "id int auto_increment primary key," +
                    "region varchar(30) not null," +
                    "city varchar(30) not null," +
                    "street varchar(30) not null," +
                    "name varchar(30) not null," +
                    "directorName varchar(30) not null)");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return statement;
    }

    public void addSchoolToDb()
    {
        School school = School.addSchoolFromConsole();
        Statement statement = DataBase.connect();
        if(statement == null) {
            System.out.println("Error to return statement");
            return;
        }
        try {
            statement.executeUpdate("insert into schools (region, city, street, name, directorName) " +
                    "VALUES ('" + school.getRegion() +
                    "', '" + school.getCity() +
                    "', '" + school.getStreet() +
                    "', '" + school.getName() +
                    "', '" + school.getDirectorName() + "')");
        } catch (SQLException e) {
            System.out.println("Error to EXECUTE statement");
            e.printStackTrace();
        }
    }

    public void changeSchoolInDB(){

    }

    public void removeSchoolFromDB(){


    }
}

