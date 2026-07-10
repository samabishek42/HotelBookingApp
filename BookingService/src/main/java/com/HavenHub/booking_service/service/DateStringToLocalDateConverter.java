package com.HavenHub.booking_service.service;
import org.springframework.core.convert.converter.Converter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateStringToLocalDateConverter implements Converter<String, LocalDate> {

      @Override
      public LocalDate convert(String source) {
            String[] dateParts = source.split("-");
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int day = Integer.parseInt(dateParts[2]);
            return LocalDate.of(year, month, day);
      }
}
