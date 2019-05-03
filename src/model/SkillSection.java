package model;

import java.util.LinkedList;
import java.util.List;

public class SkillSection extends Section {
    private List<String> skills;

    public SkillSection() {
        skills = new LinkedList<>();
    }

    public void addSkill(String skill) {
        skills.add(skill);
    }

    public void removeSkill(String skill) {
        skills.remove(skill);
    }

    public List<String> getSkills() {
        return skills;
    }

}
