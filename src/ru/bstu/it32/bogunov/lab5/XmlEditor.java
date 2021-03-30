package ru.bstu.it32.bogunov.lab5;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

public class XmlEditor implements IParser{
    private final String path;

    public XmlEditor(String path){ this.path = path; }

    public void writeToXml(ArrayList<School> schools){ DOMWriter.write(path, schools); }
    public  ArrayList<School> readXml(){
        return SAXReader.read(path);
    }

    public void addRecord(School school){
        var list = readXml();
        list.add(school);
        writeToXml(list);
    }

    public void parse(DataBase dataBase){
        ArrayList<School> schools = readXml();
        Statement statement = DataBase.getConnectStatement();
        int i = 0;
        try {
            statement.executeUpdate("TRUNCATE TABLE schools");
            for (School school: schools) {
                System.out.println(i);
                dataBase.addRecord(school);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("FIle successfully parsed");
    }

    public void changeRecord(){
        ArrayList<School> oldSchools = readXml();
        Scanner scanner = new Scanner(System.in);
        int a;
        String changeValue;
        var searchValues = getSearchValues();

        do {
            a = InputController.getIntFromString(6,
                    """
                            <1>Change region <2>Change city  
                            <3>Change street <4>Change name  
                            <5>Change director <6>Exit
                            """);
            switch (a) {
                case 1 -> {
                    System.out.println("Enter new region: ");
                    changeValue = scanner.nextLine();
                    for (int i = 0; i < oldSchools.size(); i++)
                        if (containsField(oldSchools.get(i), searchValues))
                            oldSchools.set(i, updateSchool(changeValue, 1, oldSchools.get(i)));
                }
                case 2 -> {
                    System.out.println("Enter new city: ");
                    changeValue = scanner.nextLine();
                    for (int i = 0; i < oldSchools.size(); i++)
                        if (containsField(oldSchools.get(i), searchValues))
                            oldSchools.set(i, updateSchool(changeValue, 2, oldSchools.get(i)));
                }
                case 3 -> {
                    System.out.println("Enter new street: ");
                    changeValue = scanner.nextLine();
                    for (int i = 0; i < oldSchools.size(); i++)
                        if (containsField(oldSchools.get(i), searchValues))
                            oldSchools.set(i, updateSchool(changeValue, 3, oldSchools.get(i)));
                }
                case 4 -> {
                    System.out.println("Enter new name: ");
                    changeValue = scanner.nextLine();
                    for (int i = 0; i < oldSchools.size(); i++)
                        if (containsField(oldSchools.get(i), searchValues))
                            oldSchools.set(i, updateSchool(changeValue, 4, oldSchools.get(i)));
                }
                case 5 -> {
                    System.out.println("Enter new director: ");
                    changeValue = scanner.nextLine();
                    for (int i = 0; i < oldSchools.size(); i++)
                        if (containsField(oldSchools.get(i), searchValues))
                            oldSchools.set(i, updateSchool(changeValue, 5, oldSchools.get(i)));
                }
            }
        } while (a != 6);
        writeToXml(oldSchools);
        System.out.println("Value successfully changed");
    }

    // get search values to find schools
    private String[] getSearchValues() {
        int a;
        Scanner scanner = new Scanner(System.in);
        String[] searchValues = new String[]{null, null, null, null, null};
        System.out.println("Enter search parameters: ");
        do {
            do {
                a = InputController.getIntFromString(6,
                        """
                                <1>Enter region <2>Enter city
                                <3>Enter street <4>Enter name  
                                <5>Enter director <6>Change chosen schools
                                """);
                switch (a) {
                    case 1 -> {
                        System.out.println("Region: ");
                        searchValues[0] = scanner.nextLine();
                    }
                    case 2 -> {
                        System.out.println("City: ");
                        searchValues[1] = scanner.nextLine();
                    }
                    case 3 -> {
                        System.out.println("Street: ");
                        searchValues[2] = scanner.nextLine();
                    }
                    case 4 -> {
                        System.out.println("Name: ");
                        searchValues[3] = scanner.nextLine();
                    }
                    case 5 -> {
                        System.out.println("Director: ");
                        searchValues[4] = scanner.nextLine();
                    }
                }
            }while (a != 6);
            if (searchValues[0] == null && searchValues[1] == null && searchValues[2] == null && searchValues[3] == null && searchValues[4] == null)
                System.out.println("Enter al least 1 search value");
            else
                return searchValues;
        }while(true);
    }

    // update field in given school according entered new value
    private School updateSchool(String newValue, int valueNum, School oldSchool) {
        try {
            switch (valueNum) {
                case 1 -> {
                    return new School(newValue, oldSchool.getCity(),
                            oldSchool.getStreet(), oldSchool.getName(), oldSchool.getDirectorName());
                }
                case 2 -> {
                    return new School(oldSchool.getRegion(), newValue,
                            oldSchool.getStreet(), oldSchool.getName(), oldSchool.getDirectorName());
                }
                case 3 -> {
                    return new School(oldSchool.getRegion(), oldSchool.getCity(),
                            newValue, oldSchool.getName(), oldSchool.getDirectorName());
                }
                case 4 -> {
                    return new School(oldSchool.getRegion(), oldSchool.getCity(),
                            oldSchool.getStreet(), newValue, oldSchool.getDirectorName());
                }
                case 5 -> {
                    return new School(oldSchool.getRegion(), oldSchool.getCity(),
                            oldSchool.getStreet(), oldSchool.getName(), newValue);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    //is current school contains given fields?
    private boolean containsField(School school, String[] parameters){
        boolean[] isContain = new boolean[parameters.length];
        IntStream.range(0, parameters.length)
                .filter(i -> parameters[i] == null)
                .forEachOrdered(i -> isContain[i] = true);

        for(String parameter : parameters){
            if(school.getRegion().equals(parameter))
                isContain[0] = true;
            else if(school.getCity().equals(parameter))
                isContain[1] = true;
            else if(school.getStreet().equals(parameter))
                isContain[2] = true;
            else if(school.getName().equals(parameter))
                isContain[3] = true;
            else if(school.getDirectorName().equals(parameter))
                isContain[4] = true;
        }
        return isContain[0] && isContain[1] && isContain[2] && isContain[3] && isContain[4];
    }

    public void removeRecord(){
        ArrayList<School> schools = readXml();
        var searchValues = getSearchValues();
        for (int i = 0; i < schools.size(); i++)
            if (containsField(schools.get(i), searchValues)) {
                schools.remove(i);
                i = 0;
            }
        writeToXml(schools);
        System.out.println("Record successfully removed");
    }
}
