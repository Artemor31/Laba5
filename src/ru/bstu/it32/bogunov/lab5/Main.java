package ru.bstu.it32.bogunov.lab5;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<School> schools = new ArrayList<>();

        DOMWriter.write("./schools.xml");
        SAXReader.read("./schools.xml");
    }
}
