package model;

import java.time.YearMonth;

public class Organization {
    private String name;
    private YearMonth startDate;
    private YearMonth endDate;
    private String function;
    private String description;

    {
        name = "";
        startDate = YearMonth.of(1900, 1);
        endDate = YearMonth.of(1900, 12);
        function = "";
        description = "";
    }

    public Organization() {

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
}
