package ru.bstu.it32.bogunov.lab5;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SAXReader {
    public static void read(String path) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            // анонимный класс, расширяющий класс DefaultHandler
            DefaultHandler handler = new DefaultHandler() {
                String tag = "";

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    if (qName.equalsIgnoreCase("school"))
                        System.out.println("\nЭлемент " + qName);
                    tag = qName;
                }

                // Метод вызывается когда SAXParser считывает текст между тегами
                @Override
                public void characters(char[] ch, int start, int length) {
                    if (tag.equalsIgnoreCase("region"))
                        System.out.println("region: " + new String(ch, start, length));
                    else if (tag.equalsIgnoreCase("city"))
                        System.out.println("city: " + new String(ch, start, length));
                    else if (tag.equalsIgnoreCase("street"))
                        System.out.println("street: " + new String(ch, start, length));
                    else if (tag.equalsIgnoreCase("name"))
                        System.out.println("name: " + new String(ch, start, length));
                    else if (tag.equalsIgnoreCase("directorName"))
                        System.out.println("directorName: " + new String(ch, start, length));
                }
                // Метод вызывается когда SAXParser генерирует конец тега
                @Override
                public void endElement(String uri, String localName, String qName) {tag = "";}
            };
            saxParser.parse(path, handler);

        } catch (Exception e) {
            System.out.println("Nice file, awesome text");
            e.printStackTrace();
        }
    }
}



