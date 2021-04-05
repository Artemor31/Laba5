package ru.bstu.it32.bogunov.lab5;


import java.util.ArrayList;

public class School {
    private int id;
    private final String region, city, street, name, directorName;

    public School(String _region, String _city, String _street, String _name, String _directorName){
        this.id = 0;
        this.region = _region;
        this.city = _city;
        this.street = _street;
        this.name = _name;
        this.directorName = _directorName;
    }

    public School(int _id, String _region, String _city, String _street, String _name, String _directorName){
        this.id = _id;
        this.region = _region;
        this.city = _city;
        this.street = _street;
        this.name = _name;
        this.directorName = _directorName;
    }

    public static School addSchoolFromConsole(){
        return new School(InputController.getStrFromCon("Enter Region:"),
                InputController.getStrFromCon("Enter City:"),
                InputController.getStrFromCon("Enter Street:"),
                InputController.getStrFromCon("Enter Name:"),
                InputController.getStrFromCon("Enter Director:"));
    }

    public static int getMaxId(ArrayList<School> schools){
        int max = 0;
        for (int i = 0; i < schools.size(); i++)
            if (schools.get(max).getId() < schools.get(i).getId())
                max = i;
        return schools.get(max).getId();
    }


    public int getWriteId(ArrayList<School> schools){
        if (id == 0)
            id = 1 + getMaxId(schools);
        return id;
    }

    public int getId() {return id; }
    public String getRegion(){ return region; }
    public String getCity(){
        return city;
    }
    public String getStreet(){
        return street;
    }
    public String getName(){
        return name;
    }
    public String getDirectorName(){
        return directorName;
    }
}

