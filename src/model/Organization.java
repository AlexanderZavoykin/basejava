package model;

import java.util.LinkedList;
import java.util.List;


public class Organization {
    private Link link;
    private List<Period> periods;

    public Organization(Link link) {
        this.link = link;
        periods = new LinkedList();
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;

        Organization that = (Organization) o;

        if (!link.equals(that.link)) return false;
        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (!title.equals(that.title)) return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = link.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return link.toString() + "\n" + startDate.format(FORMATTER) + " - " + endDate.format(FORMATTER) + " " + title + "\n" +
                description;
    }
}
