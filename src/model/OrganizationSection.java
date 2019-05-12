package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private List<Organization> organizations = new LinkedList<>();

    public void addOrganization(Organization o) {
        Objects.requireNonNull(o, "Content must not be null");
        organizations.add(o);
    }

    public void removeOrganization(Organization o) {
        organizations.remove(o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationSection)) return false;

        OrganizationSection that = (OrganizationSection) o;

        return organizations.equals(that.organizations);

    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }

    @Override
    public String toString() {
        String result = "";
        for (Organization o : organizations) {
            result += o.toString() + "\n";
        }
        return result;
    }
}
