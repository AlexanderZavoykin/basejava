package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class Organization {
    private Link link;
    private List<Period> periods;

    public Organization(Link link, Period period) {
        this.link = link;
        periods = new LinkedList();
        addPeriod(period);
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public void addPeriod(Period period) {
        Objects.requireNonNull(period, "Period must not be null");
        periods.add(period);
    }

    public void removePeriod(Period period) {
        periods.remove(period);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;

        Organization that = (Organization) o;

        if (!link.equals(that.link)) return false;
        return periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        int result = link.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String result = "";
        result += link.toString();
        for (Period p : periods) {
            result += p.toString();
        }
        result += "\n";
        return result;

    }
}
