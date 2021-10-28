package io.javabrains.betterreads.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator implements IDateValidator {
    private DateTimeFormatter dateFormatter;

    public DateValidator(DateTimeFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    @Override
    public boolean isValid(String dateStr) {
        try {
            LocalDate.parse(dateStr, this.dateFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean completedDateIsGreaterThanStartDate(String startDate, String completedDate) {
        if (!startDate.equals("") && !completedDate.equals("")) {
            return LocalDate.parse(completedDate).isAfter(LocalDate.parse(startDate));
        }
        return false;
    }
}