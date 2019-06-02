package model;

public enum SectionType {

    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATION("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static SectionType getSectionType(String title) {
        for (SectionType type : SectionType.values()) {
            if (type.getTitle().equals(title)) {
                return type;
            }
        }
        return null;
    }

}
