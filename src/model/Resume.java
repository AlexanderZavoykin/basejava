package model;

import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {
    private String uuid; // Unique identifier
    private String fullName;

    public Resume() {
        this(UUID.randomUUID().toString());
    }

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return (fullName.equals(resume.fullName))&(uuid.equals(resume.uuid));
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
