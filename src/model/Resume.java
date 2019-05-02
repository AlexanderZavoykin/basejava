package model;

import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {
    private final String uuid; // Unique identifier
    private final String fullName;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(fullName, "Full name can`t be null");
        Objects.requireNonNull(uuid, "UUID can`t be null");
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
        return uuid + ' ' + fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return (uuid.equals(resume.uuid));
    }

    @Override
    public int hashCode() {
        return (uuid + fullName).hashCode();
    }
}
