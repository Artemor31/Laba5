package ru.bstu.it32.bogunov.lab5;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataBase {

    static String userName;
    static String password;
    static String URL;

    public DataBase(String userName, String password, String URL){
        DataBase.userName = userName;
        DataBase.password = password;
        DataBase.URL = URL;
    }
    public static Statement connect() {

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

    public void findEntityInDB() {
        int a = 0;
        Request request = new Request();
        Scanner scanner = new Scanner(System.in);
        do{
            a = InputController.getIntFromString(7,
                    "<1>Enter id  <2>Enter region\n<3>Enter city" +
                            "  <4>Enter street\n<5>Enter name  <6>Enter director\n<7>Start search\n");
            switch (a){
                case 1:
                    String id = scanner.nextLine();
                    request.idReq = " id =" + id;
                    break;
                case 2:
                    String region = scanner.nextLine();
                    request.idReq = " region =" + region;
                    break;
                case 3:
                    String city = scanner.nextLine();
                    request.idReq = " city =" + city;
                    break;
                case 4:
                    String street = scanner.nextLine();
                    request.idReq = " street =" + street;
                    break;
                case 5:
                    String name = scanner.nextLine();
                    request.idReq = " name =" + name;
                    break;
                case 6:
                    String director = scanner.nextLine();
                    request.idReq = " directorName =" + director;
                    break;
            }
        }while(a != 7);
        Statement statement = DataBase.connect();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(request.buildRequest());

            List<String> lines = new ArrayList<>();
            while (resultSet.next()) {
                lines.add(resultSet.getString(2));
            }
            for (String line : lines) {
                System.out.println(line);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeSchoolInDB() {

    }

    public void removeSchoolFromDB(){


    }

    private void readUserSchool(int a){
        School school;
        switch (a){
        }
    }

    private class Request{
        public boolean isFirst = true;
        public String startReq = "select * from schooldb.schools where";
        public String idReq = "";
        public String regReq = "";
        public String cityReq = "";
        public String streetReq = "";
        public String nameReq = "";
        public String directorReq = "";

        public String buildRequest(){
            addReq(idReq);
            addReq(regReq);
            addReq(cityReq);
            addReq(streetReq);
            addReq(nameReq);
            addReq(directorReq);
            return startReq;
        }
        private void addReq(String req){
            if(!req.equals("") && isFirst) {
                startReq += req;
                isFirst = false;
            }
            else if(!req.equals("") && !isFirst) {
                startReq += " and " + req;
            }
        }
    }

}

