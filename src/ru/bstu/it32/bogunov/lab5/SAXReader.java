package ru.bstu.it32.bogunov.lab5;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;



//Чтение документа при помощи SAX
//        Чтение документа XML сводится к переопределению методов, соответствующих событиям, рассмотренным в предыдущем разделе. Для этого используются классы пакета пакет javax.xml.parsers, такие как SAXParserFactory (позволяет получить парсер SAX) и  SAXParser (разбирает XML-документ и генерирует события).
//        Для непосредственной обработки данных используется пакет org.xml.sax и такие его классы как:
//        •	helpers.DefaultHandler – класс, содержащий методы, соответствующие событиям SAX, которые необходимо переопределить.
//        •	Attributes – позволяет работать с атрибутами при наступлении события начала элемента.
//        •	SAXException – генерируемое исключение в случае невозможности разобрать XML-документ.
public class SAXReader {

    public static void main(String args[]) {

        String filepath = "./bin/languages.xml";

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            // анонимный класс, расширяющий класс DefaultHandler
            DefaultHandler handler = new DefaultHandler() {

                String tag = ""; // Строка для хранения имени текущего тега
                // Метод вызывается когда SAXParser генерирует начало тега
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    if (qName.equalsIgnoreCase("Language"))
                        System.out.println("\nЭлемент " + qName);
                    tag = qName;
                }

                // Метод вызывается когда SAXParser считывает текст между тегами
                @Override
                public void characters(char[] ch, int start, int length) {
                    if (tag.equalsIgnoreCase("name"))
                        System.out.println("name: " + new String(ch, start, length));
                    else if (tag.equalsIgnoreCase("age"))
                        System.out.println("age: " + new String(ch, start, length));
                }

                // Метод вызывается когда SAXParser генерирует конец тега
                @Override
                public void endElement(String uri, String localName, String qName) {
                    tag = "";
                }
            };

            // Старт разбора методом parse, которому передается наследник от DefaultHandler
            saxParser.parse(filepath, handler);

        } catch (Exception e) {
            System.out.println("Nice file, awesome text");
            e.printStackTrace();
        }
    }
}



//    Для записи XML-документа потребуется создание пустого объекта Document. Для этого необходимо использовать у объекта DocumentBuilder не метод parse(), а метод newDocument().
//        Далее при помощи вышеописанных методов необходимо создать корректное дерево узлов и записать его в файл или консоль. Для этого необходимо использовать ряд классов из пакета javax.xml.transform, таких как:
//        •	TransformerFactory – конструктор для преобразований.
//        •	Transformer – объект для выполнения фактического преобразования.
//        •	OutputKeys  – позволяет задать параметры форматирования документа.
//        •	dom.DOMSource – получает исходный код созданного документа.
//        •	stream.StreamResult – создает объект для записи в него документа.

class DOMWriter{
    public static void main(String[] args) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();// создаем пустой объект Document
            // создаем корневой элемент
            Element rootElement = doc.createElement("Languages");
            // добавляем корневой элемент в объект Document
            doc.appendChild(rootElement);

            // добавляем первый дочерний элемент к корневому
            rootElement.appendChild(getLanguage(doc, "1", "Java", "21"));
            //добавляем второй дочерний элемент к корневому
            rootElement.appendChild(getLanguage(doc, "2", "C", "44"));

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
            StreamResult file = new StreamResult(new File("./bin/languages_new.xml"));
            //запись данных
            transformer.transform(source, file);
            System.out.println("Создание XML файла закончено");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // метод для создания нового узла XML-файла
    private static Node getLanguage(Document doc, String id, String name, String age) {
        Element language = doc.createElement("Language");
        language.setAttribute("id", id); // устанавливаем атрибут id
        // создаем элементы name и age
        language.appendChild(getLanguageElements(doc, language, "name", name));
        language.appendChild(getLanguageElements(doc, language, "age", age));
        return language;
    }

    // метод для создания одного узла
    private static Node getLanguageElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}

