package model;

import java.util.LinkedList;
import java.util.List;

public class ListSection extends AbstractSection {
    private List<String> skills;

    public ListSection() {
        skills = new LinkedList<>();
    }

    public void addSkill(String skill) {
        skills.add(skill);
    }

    public void removeSkill(String skill) {
        skills.remove(skill);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListSection)) return false;

        ListSection that = (ListSection) o;

        return skills.equals(that.skills);

    }

    @Override
    public int hashCode() {
        return skills.hashCode();
    }

    @Override
    public String toString() {
        String result = "";
        for (String skill : skills) {
            result += skill + "\n";
        }
        return result;
    }
}
