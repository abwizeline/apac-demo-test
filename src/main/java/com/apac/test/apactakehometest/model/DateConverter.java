package com.apac.test.apactakehometest.model;

import com.apac.test.apactakehometest.ApacTakeHomeTestApplication;

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
            ApacTakeHomeTestApplication.LOGGER.debug("before conversion: " + dateString);

            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            date = dateFormat.parse(dateString);

            ApacTakeHomeTestApplication.LOGGER.debug("after conversion: " + date + " timezone " + TimeZone.getTimeZone("UTC"));

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
