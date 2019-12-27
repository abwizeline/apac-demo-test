package com.apac.test.apactakehometest;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Getter
public class CSVReader {

    private CsvSchema mSchema;
    private String mHeader;
    private ObjectReader mReader;

    public <T> CSVReader(Class<T> clazz, @NotNull String header, @NotNull char commaDelimiter){

        CsvMapper mapper = new CsvMapper();
        mSchema = CsvSchema.emptySchema()
                .withHeader()
                .withColumnSeparator(commaDelimiter);

        mReader = mapper
                .enable(CsvParser.Feature.SKIP_EMPTY_LINES)
                .enable(CsvParser.Feature.TRIM_SPACES)
                .enable(CsvParser.Feature.INSERT_NULLS_FOR_MISSING_COLUMNS)
                .readerFor(clazz)
                .forType(clazz)
                .withoutAttribute("id")
                .with(mSchema);

        this.mHeader = header;
    }

    public <T> T read(String src) throws IOException {
        // not a sharp solution, but CSVMapper can't understand what fields to parser
        return mReader.<T>readValue((mHeader + "\n" + src).getBytes());
    }
}
