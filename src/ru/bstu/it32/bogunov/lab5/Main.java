package ru.bstu.it32.bogunov.lab5;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<School> schools = new ArrayList<>();
        schools.add(new School("регион1", "город1", "улица1", "имя1", "директор1"));
        schools.add(new School("регион2", "город2", "улица2", "имя2", "директор2"));
        schools.add(new School("регион3", "город3", "улица3", "имя3", "директор3"));

        DOMWriter.write("./schools.xml", schools);
        SAXReader.read("./schools.xml");

        for (School school: schools) {
            System.out.println("Region: " + school.getRegion());
            System.out.println("City: " + school.getCity());
            System.out.println("Street: " + school.getStreet());
            System.out.println("Name: " + school.getName());
            System.out.println("Director: " + school.getDirectorName());
        }
    }
}
