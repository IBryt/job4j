package ru.job4j.pseudo;

public class Triagle implements Shape {
    @Override
    public String pic() {
        StringBuilder builder = new StringBuilder();
        builder.append("  *  " + System.lineSeparator());
        builder.append(" *** " + System.lineSeparator());
        builder.append("*****");
        return builder.toString();
    }
}
