package com.gmail.aazavoykin.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@XmlRootElement
public class ListSection extends AbstractSection {
    private List<String> skills;

    public ListSection() {
        skills = new LinkedList<>();
    }

    public ListSection(String... s) {
        this(Arrays.asList(s));
    }

    public ListSection(List<String> skills) {
        this.skills = skills;
    }

    public void addSkill(String skill) {
        Objects.requireNonNull(skill, "Content must not be null");
        skills.add(skill);
    }

    public List<String> getSkills() {
        return skills;
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
        //return skills.stream().collect(Collectors.joining("\n"));
        return String.join("\n", skills);
    }

}