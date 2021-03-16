package ru.bstu.it32.bogunov.lab5;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class XmlEditor {

    public void writeToXml(String path, ArrayList<School> schools){
        DOMWriter.write(path, schools);
    }

    public  List<School> readXml(String path){
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

}
