package model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@XmlRootElement
public class OrganizationSection extends AbstractSection {
    private List<Organization> organizations = new LinkedList<>();

    public OrganizationSection() {
    }

    public OrganizationSection(Organization... o) {
        for (int i = 0; i < o.length; i++) {
            organizations.add(o[i]);
        }
    }

    public OrganizationSection(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public void addOrganization(Organization o) {
        Objects.requireNonNull(o, "Content must not be null");
        organizations.add(o);
    }

    public void removeOrganization(Organization o) {
        organizations.remove(o);
    }

    public List<Organization> getOrganizations() {
        return organizations;
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
        StringBuilder result = new StringBuilder();
        for (Organization o : organizations) {
            result.append(o.toString() + "\n");
        }
        return result.toString();
    }
}
