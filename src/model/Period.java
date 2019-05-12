package model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Period {
    private YearMonth startDate;
    private YearMonth endDate;
    private String title;
    private String description;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/uuuu");

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
