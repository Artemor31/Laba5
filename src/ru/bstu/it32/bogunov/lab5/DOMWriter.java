package ru.bstu.it32.bogunov.lab5;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class DOMWriter{
    public static void write(String path, ArrayList<School> schools) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element rootElement = doc.createElement("school");
            doc.appendChild(rootElement);

            // добавляем первый дочерний элемент к корневому
            rootElement.appendChild(getSchool(doc,
                    "Belgorodskaya",
                    "Belgorod",
                    "Koneva",
                    "School49",
                    "Lamanova"));
            rootElement.appendChild(getSchool(doc,
                    "Belgorodskaya",
                    "Belgorod",
                    "Koroleva",
                    "School19",
                    "Petrova"));
            rootElement.appendChild(getSchool(doc,
                    "Belgorodskaya",
                    "Belgorod",
                    "Gybkina",
                    "School43",
                    "Ivanova"));
            for(School school : schools){
                rootElement.appendChild(getSchool(doc, school.getRegion(), school.getCity(),
                school.getStreet(), school.getName(), school.getDirectorName()));
            }



            doc.getDocumentElement().normalize();
            //создаем объект TransformerFactory для преобразования документа в файл
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // установка параметров форматирования для красивого вывода
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            //получение исходного кода готового документа
            DOMSource source = new DOMSource(doc);

            //создание объекта для записи - файл
            StreamResult file = new StreamResult(new File(path));
            //запись данных
            transformer.transform(source, file);
            System.out.println("Создание XML файла закончено");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // метод для создания нового узла XML-файла
    private static Node getSchool(Document doc, String region, String city, String street, String name, String directorName) {

        Element school = doc.createElement("school");
        school.appendChild(getSchoolElements(doc, school, "region", region));
        school.appendChild(getSchoolElements(doc, school, "city", city));
        school.appendChild(getSchoolElements(doc, school, "street", street));
        school.appendChild(getSchoolElements(doc, school, "name", name));
        school.appendChild(getSchoolElements(doc, school, "directorName", directorName));
        return school;

    }
    // метод для создания одного узла
    private static Node getSchoolElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}
