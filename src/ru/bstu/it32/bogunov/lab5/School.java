package ru.bstu.it32.bogunov.lab5;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class School {
    private String id;
    private String region;
    private String city;
    private String street;
    private String name;
    private String directorName;

    public School(String _region, String _city, String _street, String _name, String _directorName){
        this.region = _region;
        this.city = _city;
        this.street = _street;
        this.name = _name;
        this.directorName = _directorName;
    }

    public void setId(String _id){
        this.id = _id;
    }
}

