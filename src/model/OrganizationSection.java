package model;

import java.util.LinkedList;
import java.util.List;

public class OrganizationSection extends Section {
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

}
