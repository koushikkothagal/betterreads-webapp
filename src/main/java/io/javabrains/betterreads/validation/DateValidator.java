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
        if (LocalDate.parse(startDate).isAfter(LocalDate.parse(completedDate))) {
            return false;
        }
        return true;
    }
}