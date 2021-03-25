package ru.bstu.it32.bogunov.lab5;

public interface IParser {
    void parse(DataBase dataBase);
    void addRecord(School school);
    void changeRecord();
    void removeRecord();
}
