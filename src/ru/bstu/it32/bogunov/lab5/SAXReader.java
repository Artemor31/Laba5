package ru.bstu.it32.bogunov.lab5;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;

public class SAXReader {
    public static List<School> read(String path) {
        List<School> schools = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                String tag = "";

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    tag = qName;
                }
                // Метод вызывается когда SAXParser считывает текст между тегами
                @Override
                public void characters(char[] ch, int start, int length) {
                    String[] str = new String[5];

                    if (tag.equalsIgnoreCase("region")) {
                        str[0] = new String(ch, start, length);
                    }
                    else if (tag.equalsIgnoreCase("city")) {
                        str[1] = new String(ch, start, length);
                    }
                    else if (tag.equalsIgnoreCase("street")) {
                        str[2] = new String(ch, start, length);
                    }
                    else if (tag.equalsIgnoreCase("name")) {
                        str[3] = new String(ch, start, length);
                    }
                    else if (tag.equalsIgnoreCase("directorName")) {
                        str[4] = new String(ch, start, length);
                    }
                    School school = new School(str[0], str[1], str[2], str[3], str[4]);
                    schools.add(school);
                }

                @Override
                public void endElement(String uri, String localName, String qName) {tag = "";}
            };
            saxParser.parse(path, handler);

        } catch (Exception e) {
            System.out.println("Nice file, awesome text");
            e.printStackTrace();
        }
        return schools;
    }
}



