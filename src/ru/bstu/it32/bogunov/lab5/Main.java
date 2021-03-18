package ru.bstu.it32.bogunov.lab5;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        Properties.initialize();
        int a = InputController.getIntFromString(3, "<1> Work with DataBase\n<2> Work with XML\n<3> Exit");
        switch (a){
            case 1 -> workWithDatabase(Properties.FilePath);
            case 2 -> workWithXml(Properties.FilePath);
        }
    }

    private static void workWithDatabase(String path) {
        DataBase dataBase = new DataBase(Properties.userName, Properties.password, Properties.URL);
        XmlEditor xmlEditor = new XmlEditor();
        int a = InputController.getIntFromString(5, """
                <1> Add record
                <2> Change record
                <3> Remove record
                <4> Parse Database to Xml
                <5> Exit""");

        switch (a) {
            case 1 -> dataBase.addSchoolToDb(School.addSchoolFromConsole());
            case 2 -> dataBase.changeSchoolInDB();
            case 3 -> dataBase.removeSchoolFromDB();
            case 4 -> xmlEditor.writeToXml(path, dataBase.parseDatabaseToXml());
        }
        main(new String[]{"bcd"});
    }

    private static void workWithXml(String path) {
        DataBase dataBase = new DataBase(Properties.userName, Properties.password, Properties.URL);
        XmlEditor xmlEditor = new XmlEditor();
        int a = InputController.getIntFromString(5, """
                <1> Add record
                <2> Change record
                <3> Remove record
                <4> Parse Xml to Database
                <5> Exit""");

        ArrayList<School> schools = xmlEditor.readXml(path);
        switch (a){
            case 1 -> schools.add(School.addSchoolFromConsole());
            case 2 -> xmlEditor.changeSchools(schools);
            case 3 -> xmlEditor.removeSchool(schools);
            case 4 -> xmlEditor.parseFromXmlToDatabase(schools, dataBase);
        }
        xmlEditor.writeToXml(path, schools);
        main(new String[]{"bcd"});
    }


    public static class Properties{
        public static String FilePath;
        public static String userName;
        public static String password;
        public static String URL;
        public static int MaxValuesLength;
        public static String INPUT_ERROR;
        public static String SQL_ERROR;
        public static String ENTITY_ERROR;

        public static void initialize(){
            java.util.Properties prop = new java.util.Properties();
            loadProperties(prop);
            FilePath = getNextProperty("filePath", prop);
            userName = getNextProperty("userName", prop);
            password = getNextProperty("password", prop);
            URL = getNextProperty("URL", prop);
            MaxValuesLength =  Integer.parseInt(Objects.requireNonNull(getNextProperty("maxLength", prop)));
            INPUT_ERROR = getNextProperty("INPUT_ERROR", prop);
            SQL_ERROR = getNextProperty("SQL_ERROR", prop);
            ENTITY_ERROR = getNextProperty("ENTITY_ERROR", prop);
            }

        private static void loadProperties(java.util.Properties prop) {
            try {
                FileInputStream fis = new FileInputStream("./laba5.properties");
                prop.load(fis);
            }catch (IOException e) {
                System.out.println("Properties Not Found");
                e.printStackTrace();
            }
        }

        private static String getNextProperty(String name, java.util.Properties prop) {
            try {
                return new String(prop.getProperty(name).getBytes("ISO8859-1"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
//    private static void printSchoolsList(ArrayList<School> schools) {
//        for (School school: schools) {
//            System.out.println("Region: " + school.getRegion());
//            System.out.println("City: " + school.getCity());
//            System.out.println("Street: " + school.getStreet());
//            System.out.println("Name: " + school.getName());
//            System.out.println("Director: " + school.getDirectorName());
//        }
//    }