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

    public static Statement getConnectStatement() {
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
        } catch (SQLException ex) { ex.printStackTrace(); }
        return statement;
    }

    public void addSchoolToDb(School school) {
        Statement statement = DataBase.getConnectStatement();
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
            System.out.println(Main.Properties.SQL_ERROR);
            e.printStackTrace();
        }
        System.out.println("Record successfully added");
    }

    public ArrayList<School> parseDatabaseToXml(){
        var schools = new ArrayList<School>();
        Statement statement = DataBase.getConnectStatement();
        ResultSet resultSet;

        try {
            resultSet = statement.executeQuery("select * from schools");

            while (resultSet.next()) {
                schools.add(new School(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)));
            }
        }catch (SQLException e) {
            System.out.println(Main.Properties.SQL_ERROR);
            e.printStackTrace();
        }
        System.out.println("Table successfully parsed");
        return schools;
    }

    private List<Integer> findEntityInDB() {
        int a;
        Request request = new Request();
        Scanner scanner = new Scanner(System.in);
        Statement statement = DataBase.getConnectStatement();
        ResultSet resultSet;
        ArrayList<Integer> ids = new ArrayList<>();
        do {
            do {
                a = InputController.getIntFromString(7,
                        """
                                <1>Enter id    <2>Enter region
                                <3>Enter city  <4>Enter street
                                <5>Enter name  <6>Enter director
                                <7>Start search
                                """);
                switch (a) {
                    case 1 -> {
                        String id = scanner.nextLine();
                        request.idReq = " id = '" + id + "'";
                    }
                    case 2 -> {
                        String region = scanner.nextLine();
                        request.regReq = " region = '" + region + "'";
                    }
                    case 3 -> {
                        String city = scanner.nextLine();
                        request.cityReq = " city = '" + city + "'";
                    }
                    case 4 -> {
                        String street = scanner.nextLine();
                        request.streetReq = " street = '" + street + "'";
                    }
                    case 5 -> {
                        String name = scanner.nextLine();
                        request.nameReq = " name = '" + name + "'";
                    }
                    case 6 -> {
                        String director = scanner.nextLine();
                        request.directorReq = " directorName = '" + director + "'";
                    }
                }
            } while (a != 7);
            try {
                String req = request.buildRequest();
                resultSet = statement.executeQuery(req);

                while (resultSet.next()) {
                    ids.add(resultSet.getInt(1));
                }

            } catch (SQLException e) {
                System.out.println(Main.Properties.ENTITY_ERROR);
                Main.main(new String[]{"bcd"});
            }
        } while (ids.size() == 0);
        return ids;
    }

    public void changeSchoolInDB() {
        List<Integer> ids = findEntityInDB();
        Scanner scanner = new Scanner(System.in);
        Statement statement = DataBase.getConnectStatement();
        int a;

        do {
            a = InputController.getIntFromString(6,
                    """
                            <1>Change region <2>Change city  
                            <3>Change street <4>Change name  
                            <5>Change director <6>Accept changes
                            """);
            switch (a) {
                case 1 -> {
                    for(Integer i : ids){
                        System.out.println("Enter new region for id: " + i);
                        updateRequest(statement, i, "region", scanner.nextLine());
                    }
                }
                case 2 -> {
                    for(Integer i : ids){
                        System.out.println("Enter new city for id: " + i);
                        updateRequest(statement, i, "city", scanner.nextLine());
                    }
                }
                case 3 -> {
                    for(Integer i : ids){
                        System.out.println("Enter new street for id: " + i);
                        updateRequest(statement, i, "street", scanner.nextLine());
                    }
                }
                case 4 -> {
                    for(Integer i : ids){
                        System.out.println("Enter new name for id: " + i);
                        updateRequest(statement, i, "name", scanner.nextLine());
                    }
                }
                case 5 -> {
                    for(Integer i : ids){
                        System.out.println("Enter new director name for id: " + i);
                        updateRequest(statement, i, "directorName", scanner.nextLine());
                    }
                }
            }
        } while (a != 6);
        System.out.println("Value successfully changed");
    }

    private void updateRequest(Statement statement, int id, String column, String newValues){
        try {
            statement.executeUpdate(" UPDATE schools " +
                    "SET " + column + " = '" + newValues + "' " +
                    "WHERE id = '" + id + "'");
        } catch (SQLException e) {
            System.out.println(Main.Properties.SQL_ERROR);
        }
    }

    public void removeSchoolFromDB(){
        List<Integer> ids = findEntityInDB();
        Statement statement = DataBase.getConnectStatement();

        for(int id : ids){
            try {
                statement.executeUpdate("delete from schools " +
                                            "where id = " + id);
            } catch (SQLException e) {
                System.out.println(Main.Properties.ENTITY_ERROR);
                e.printStackTrace();
            }
        }
        System.out.println("Entities successfully removed");
    }


    private static class Request{
        public boolean isFirst = true;
        public String startReq = "select * from schools where ";
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
            else if(!req.equals("")) {
                startReq += " and " + req;
            }
        }
    }
}

//    public void printAll(){
//        Statement statement = DataBase.getConnectStatement();
//        ResultSet resultSet;
//        try {
//            resultSet = statement.executeQuery("select * from schools");
//
//            while (resultSet.next()) {
//                System.out.println(resultSet.getInt(1));
//                System.out.println(resultSet.getString(2));
//                System.out.println(resultSet.getString(3));
//                System.out.println(resultSet.getString(4));
//                System.out.println("---------------------------------");
//            }
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
