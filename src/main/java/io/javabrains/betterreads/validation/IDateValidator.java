package io.javabrains.betterreads.validation;

public interface IDateValidator {
    boolean isValid(String dateStr);
    boolean completedDateIsGreaterThanStartDate(String startDate, String completedDate);
}
