package de.codecentric.psd.worblehat.acceptancetests.adapter;

import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

import io.cucumber.java.ParameterType;


public class ParameterTypes {

    @ParameterType("\\d{4}-\\d{2}-\\d{2}")
    public LocalDate date(String aDate) {
        return LocalDate.from(ISO_LOCAL_DATE.parse(aDate));
    }
}
