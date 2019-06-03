package model;

import util.YearMonthAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private final static long serialVersionUID = 1L;
    private Link link;
    private List<Period> periods;

    public Organization() {
    }


    public Organization(Link link, List<Period> periods) {
        this.link = link;
        this.periods = periods;
    }

    public Organization(Link link, Period... p) {
        this(link, Arrays.asList(p));
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

    public List<Period> getPeriods() {
        return periods;
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
        StringBuilder result = new StringBuilder();
        result.append(link.toString());
        for (Period p : periods) {
            result.append(p.toString());
        }
        result.append("\n");
        return result.toString();

    }


    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable {
        private final static long serialVersionUID = 1L;
        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        private YearMonth startDate;
        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        private YearMonth endDate;
        private String title;
        private String description;
        public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/uuuu");

        public Period() {
        }

        public Period(YearMonth startDate, YearMonth endDate, String title, String description) {
            Objects.requireNonNull(startDate, "Start date must not be null");
            Objects.requireNonNull(endDate, "End date must not be null");
            Objects.requireNonNull(title, "Title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        public YearMonth getStartDate() {
            return startDate;
        }

        public void setStartDate(YearMonth startDate) {
            this.startDate = startDate;
        }

        public YearMonth getEndDate() {
            return endDate;
        }

        public void setEndDate(YearMonth endDate) {
            this.endDate = endDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Period)) return false;

            Period period = (Period) o;

            if (!startDate.equals(period.startDate)) return false;
            if (!endDate.equals(period.endDate)) return false;
            if (!title.equals(period.title)) return false;
            return description != null ? description.equals(period.description) : period.description == null;
        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            String s = "";
            if (description != "") {
                s = "\n" + description;
            }
            return startDate.format(FORMATTER) + " - " + endDate.format(FORMATTER) + "   " + title + s;
        }
    }
}
