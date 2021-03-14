package ru.bstu.it32.bogunov.lab5;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Properties.initialize();
       // DataBase.connect();
        ArrayList<School> schools = new ArrayList<>();
        DataBase dataBase = new DataBase();

        dataBase.addSchoolToDb(
                new School(
                        "регион1",
                        "город1",
                        "улица1",
                        "имя1",
                        "директор1"));

        //schools.add(new School("регион1", "город1", "улица1", "имя1", "директор1"));
        //schools.add(new School("регион2", "город2", "улица2", "имя2", "директор2"));
        //schools.add(new School("регион3", "город3", "улица3", "имя3", "директор3"));

        schools.add(addSchoolFromConsole());
        schools.add(addSchoolFromConsole());

        DOMWriter.write(Properties.FilePath, schools);
        SAXReader.read(Properties.FilePath);

        printSchoolsList(schools);
    }


    private static void printSchoolsList(ArrayList<School> schools) {
        for (School school: schools) {
            System.out.println("Region: " + school.getRegion());
            System.out.println("City: " + school.getCity());
            System.out.println("Street: " + school.getStreet());
            System.out.println("Name: " + school.getName());
            System.out.println("Director: " + school.getDirectorName());
        }
    }


    public static School addSchoolFromConsole(){
        return new School(InputController.getStrFromCon("Enter Region:"),
                InputController.getStrFromCon("Enter City:"),
                InputController.getStrFromCon("Enter Street:"),
                InputController.getStrFromCon("Enter Name:"),
                InputController.getStrFromCon("Enter Director:"));
    }



    public static class Properties{
        public static String FilePath;
        public static int MaxValuesLength;

        public static void initialize(){
            java.util.Properties prop = new java.util.Properties();
            try {
                FileInputStream fis = new FileInputStream("./laba5.properties");
                prop.load(fis);
                FilePath = new String(prop.getProperty("filePath").getBytes("ISO8859-1"));
                String val = new String(prop.getProperty("maxLength").getBytes("ISO8859-1"));
                MaxValuesLength =  Integer.parseInt(val);
            } catch (IOException e) {
                System.out.println("Properties Not Found");
                e.printStackTrace();
            }
        }
    }
}
