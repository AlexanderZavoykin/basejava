package model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;


public class Organization {
    private String name;
    private YearMonth startDate;
    private YearMonth endDate;
    private String function;
    private String description;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/uuuu");

    public Organization() {
    }

    public Organization(String name, YearMonth startDate, YearMonth endDate, String function, String description) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.function = function;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
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
        if (!(o instanceof Organization)) return false;

        Organization that = (Organization) o;

        if (!name.equals(that.name)) return false;
        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (!function.equals(that.function)) return false;
        return description.equals(that.description);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + function.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name + "\n" + startDate.format(FORMATTER) + " - " + endDate.format(FORMATTER) + " " + function + "\n" +
                description;
    }
}
