package ru.bstu.it32.bogunov.lab5;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;


// TODO IF DB => autoincrement id
// TODO IF XML => Find max and make max+1

public class Main {
    public static void main(String[] args) {
        Properties.initialize();
        int a = InputController.getIntFromString(3,
                "<1> Work with DataBase\n<2> Work with XML\n<3> Exit");
        IParser parser = null;
        switch (a) {
            case 1 -> parser = new DataBase(Properties.userName, Properties.password, Properties.URL);
            case 2 -> parser = new XmlEditor(Properties.FilePath);
            case 3 -> {
                return;
            }
        }
        int b = InputController.getIntFromString(5, """
                <1> Add record
                <2> Change record
                <3> Remove record
                <4> Parse Data
                <5> Exit""");
        if (parser == null)
            throw new NullPointerException();
        switch (b) {
            case 1 -> parser.addRecord(School.addSchoolFromConsole());
            case 2 -> parser.changeRecord();
            case 3 -> parser.removeRecord();
            case 4 -> {
                DataBase dataBase= new DataBase(Properties.userName, Properties.password, Properties.URL);
                parser.parse(dataBase);
            }
        }
        main(null);
    }


    public static class Properties{
        public static String FilePath, userName, password, URL;
        public static String INPUT_ERROR, SQL_ERROR, ENTITY_ERROR;
        public static int MaxValuesLength;

        public static void initialize() {
            java.util.Properties prop = new java.util.Properties();
            loadProperties(prop);
            FilePath = getNextProperty("filePath", prop);
            userName = getNextProperty("userName", prop);
            password = getNextProperty("password", prop);
            URL = getNextProperty("URL", prop);
            MaxValuesLength = Integer.parseInt(Objects.requireNonNull(getNextProperty("maxLength", prop)));
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