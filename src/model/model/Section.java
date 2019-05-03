package model.model;

import java.util.LinkedList;

public enum Section {

    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATION("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;
    private LinkedList<String> body;

    Section(String title) {
        this.title = title;
        body = new LinkedList<>();
    }

    public String getTitle() {
        return title;
    }

    public LinkedList<String> getBody() {
        return body;
    }

    public void setBody(LinkedList<String> body) {
        this.body = body;
    }
}
