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

    public static ArrayList<School> parseFromXML() throws ParserConfigurationException, SAXException, IOException {
        // Получение фабрики, чтобы после получить билдер документов.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // Получили из фабрики билдер, который парсит XML, создает структуру Document в виде иерархического дерева.
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Запарсили XML, создав структуру Document. Теперь у нас есть доступ ко всем элементам, каким нам нужно.
        Document document = builder.parse(new File("resource/xml_file1.xml"));

        // Получение списка всех элементов employee внутри корневого элемента (getDocumentElement возвращает ROOT элемент XML файла).
        NodeList schoolElements = document.getDocumentElement().getElementsByTagName("employee");

        ArrayList<School> schools = new ArrayList<>();
        // Перебор всех элементов employee
        for (int i = 0; i < schoolElements.getLength(); i++) {
            Node employee = schoolElements.item(i);

            // Получение атрибутов каждого элемента
            NamedNodeMap attributes = ((Node) employee).getAttributes();

            // Добавление сотрудника. Атрибут - тоже Node, потому нам нужно получить значение атрибута с помощью метода getNodeValue()
            schools.add(new School(
                    attributes.getNamedItem("region").getNodeValue(),
                    attributes.getNamedItem("city").getNodeValue(),
                    attributes.getNamedItem("street").getNodeValue(),
                    attributes.getNamedItem("name").getNodeValue(),
                    attributes.getNamedItem("directorName").getNodeValue()));
        }
        return schools;
    }
}

