package com.apac.test.apactakehometest.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Converter
public class DateConverter implements AttributeConverter<String, Long> {
    private static final Logger LOGGER = LogManager.getLogger(DateConverter.class);

    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";

    @Override
    public Long convertToDatabaseColumn(String dateString) {

        if(dateString == null || dateString.isEmpty()){
            return 0L;
        }

        Date date;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            date = dateFormat.parse(dateString);
        }catch (Exception ex){
            LOGGER.debug(ex.getMessage(), ex);
            return 0L;
        }

        return date.getTime();
    }

    @Override
    public String convertToEntityAttribute(Long date) {
        return new Date(date).toString();
    }
}
