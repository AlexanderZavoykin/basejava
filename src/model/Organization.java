package model;

import java.time.LocalDate;



public class Organization {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String function;
    private String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
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
}
