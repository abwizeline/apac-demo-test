package com.apac.test.apactakehometest.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Converter
public class DateConverter implements AttributeConverter<String, Long> {

    public final static String DATE_TIME_FORMAT = "YYYY-MM-DD kk:mm:ss";

    @Override
    public Long convertToDatabaseColumn(String dateString) {

        if(dateString == null || dateString.isEmpty()){
            return 0L;
        }

        Date date;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-04:00")); // convert to EDT
            date = dateFormat.parse(dateString);
        }catch (Exception ex){
            ex.printStackTrace();
            return 0L;
        }

        return date.getTime();
    }

    @Override
    public String convertToEntityAttribute(Long date) {
        return new Date(date).toString();
    }
}
