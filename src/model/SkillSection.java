package model;

import java.util.LinkedList;
import java.util.List;

public class SkillSection extends AbstractSection {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkillSection)) return false;

        SkillSection that = (SkillSection) o;

        return skills.equals(that.skills);

    }

    @Override
    public int hashCode() {
        return skills.hashCode();
    }
}
