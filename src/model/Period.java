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
        Objects.requireNonNull(title, "Function must not be null");
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
}
