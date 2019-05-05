package model;

import java.util.LinkedList;
import java.util.List;

public class OrganizationSection extends AbstractSection {
    private List<Organization> organizations = new LinkedList<>();

    public void addOrganization(Organization o) {
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
}
