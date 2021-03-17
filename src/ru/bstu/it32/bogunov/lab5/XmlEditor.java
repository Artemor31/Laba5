package ru.bstu.it32.bogunov.lab5;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class XmlEditor {

    public void writeToXml(String path, ArrayList<School> schools){
        DOMWriter.write(path, schools);
    }

    public  ArrayList<School> readXml(String path){
        return SAXReader.read(path);
    }

    public void parseFromXmlToDatabase(List<School> schools, DataBase dataBase){
        Statement statement = DataBase.getConnectStatement();
        int i = 0;
        try {
            statement.executeUpdate("TRUNCATE TABLE schools");
            for (School school: schools) {
                System.out.println(i);
                dataBase.addSchoolToDb(school);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeSchool(ArrayList<School> schools){
        int a;
        Scanner scanner = new Scanner(System.in);
        do {
            do {
                a = InputController.getIntFromString(6,
                        """
                                <1>Enter region <2>Enter city
                                <3>Enter street <4>Enter name  
                                <5>Enter director <6>Start search
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
                System.out.println("Cant find this entity, try again");
                Main.main(new String[]{"abc", "bcd"});
            }
        } while (ids.size() == 0);
        return
    }

    private ArrayList<School> findTargetSchools(String[] parameters, ArrayList<School> allSchools){
        ArrayList<School> schools = new ArrayList<>();
        for(String ignored : parameters){

            for(School school : allSchools){

                if(containsField(school, parameters[0])){
                    schools.add(school);
                    break;
                }
            }
        }
        return schools;
    }

    private boolean containsField(School school, String parameter){
        if(school.getRegion().equals(parameter))
            return true;
        else if(school.getCity().equals(parameter))
            return true;
        else if(school.getStreet().equals(parameter))
            return true;
        else if(school.getName().equals(parameter))
            return true;
        else return school.getDirectorName().equals(parameter);
    }

}
