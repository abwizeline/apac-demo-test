package com.apac.test.apactakehometest;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Getter
public class CSVUtils {
    private static final CsvMapper mapper = new CsvMapper();

    private CsvSchema mSchema;
    private String mHeader;
    private ObjectReader mReader;

    public <T> CSVUtils(Class<T> clazz, @NotNull String header, @NotNull char commaDelimiter){
        mSchema = CsvSchema.emptySchema()
                .withoutHeader()
                .withColumnSeparator(commaDelimiter);

        mReader = mapper
                .enable(CsvParser.Feature.SKIP_EMPTY_LINES)
                .enable(CsvParser.Feature.TRIM_SPACES)
                .enable(CsvParser.Feature.INSERT_NULLS_FOR_MISSING_COLUMNS)
                .readerFor(clazz)
                .forType(clazz)
                .withoutAttribute("id")
                .with(mSchema);

        this.mHeader = "taxiID,vendorID,lpep_pickup_datetime,lpep_dropoff_datetime,store_and_fwd_flag,ratecodeID,pulocationID,dolocationID,passenger_count,trip_distance,fare_amount,extra,mta_tax,tip_amount,tolls_amount,ehail_fee,improvement_surcharge,total_amount,payment_type,trip_type";
    }

    public <T> T read(String src) throws IOException {
        // return mReader.<T>readValue(src.getBytes());
        return mReader.<T>readValue((mHeader + "\n" + src).getBytes());
    }
}
