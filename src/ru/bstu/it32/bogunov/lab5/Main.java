package ru.bstu.it32.bogunov.lab5;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//TODO
// считать с пропертей параетры бд через конструктор
// добавление, удаление, редактирование записей
// парс из бд в XML и обратно
// поиск по параметрам (можно несколько)
// тексты ошибок в пропертях

public class Main {
    public static void main(String[] args) {

        Properties.initialize();
        ArrayList<School> schools = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        int a = 0;
        while(a < 1 || a > 3) {
            System.out.println("<1> Change DataBase\n<2> Change XML File\n<3> Exit");
            a = InputController.getIntFromString(scanner.nextLine());
        }

        if (a == 1){
            workWithDatabase();
            return;
        }
        workWithXml();
    }

    private static void workWithDatabase()
    {
        DataBase dataBase = new DataBase();
        Scanner scanner = new Scanner(System.in);
        int a = 0;
        while(a < 1 || a > 3) {
            System.out.println("<1> Add record\n<2> Change record\n<3> Edit record");
            a = InputController.getIntFromString(scanner.nextLine());
        }
        if(a == 1) {
            dataBase.addSchoolToDb();
            main(new String[]{"abc", "bcd"});
        }
        else if(a == 2) dataBase.changeSchoolInDB();
        else if(a == 3) dataBase.removeSchoolFromDB();





    }

    private static void workWithXml()
    {

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

//schools.add(new School("регион1", "город1", "улица1", "имя1", "директор1"));
//schools.add(addSchoolFromConsole());

// schools.add(addSchoolFromConsole());
// DOMWriter.write(Properties.FilePath, schools);
// SAXReader.read(Properties.FilePath);
//  printSchoolsList(schools);