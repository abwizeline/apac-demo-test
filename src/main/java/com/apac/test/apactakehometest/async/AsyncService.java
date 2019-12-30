package com.apac.test.apactakehometest.async;

import com.apac.test.apactakehometest.CSVReader;
import com.apac.test.apactakehometest.model.TaxiTripsModel;
import com.apac.test.apactakehometest.repository.TaxiTripsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {

    private static final Logger LOGGER = LogManager.getLogger(AsyncService.class);

    @Value("${com.apac.file_extension}")
    private String mFileExtension;

    @Value("${com.apac.byte_buffer}")
    private int mByteBufferSize;

    @Value("${com.apac.comma_delimiter}")
    private String mCommaDelimiter;

    @Autowired
    private TaxiTripsRepository mTaxiTripsRepository;

    @Async(AsyncConfiguration.mAsyncThreadName)
    public CompletableFuture<Boolean> asyncParseFileUrl(@NotNull String csvUrl) {

        if (!csvUrl.endsWith(mFileExtension)) {
            LOGGER.error("Not a csv file");
            return CompletableFuture.completedFuture(false);
        }

        ArrayList<TaxiTripsModel> tripsModels = new ArrayList<>();
        try {
            tripsModels = readFromStream(new URL(csvUrl).openStream());
        } catch (IOException e) {
            LOGGER.debug(e.getMessage(), e);
        }

        if (tripsModels.size() > 0) {
            saveTaxiTrips(mTaxiTripsRepository, tripsModels);
        } else {
            LOGGER.debug("No data");
            return CompletableFuture.completedFuture(false);
        }

        return CompletableFuture.completedFuture(true);
    }

    public ArrayList<TaxiTripsModel> readFromStream(InputStream inputStream) {

        if (mByteBufferSize == 0) {
            mByteBufferSize = 1024;
        }

        if(mCommaDelimiter == null){
            mCommaDelimiter = ",";
        }

        ArrayList<TaxiTripsModel> taxiTripsModels = new ArrayList<>();
        try (BufferedInputStream in = new BufferedInputStream(inputStream);) {

            byte[] dataBuffer = new byte[mByteBufferSize];
            int bytesRead;

            String rowData = "";
            CSVReader csvReader = null;

            synchronized (in) {
                while ((bytesRead = in.read(dataBuffer, 0, mByteBufferSize)) != -1) {

                    String data = new String(dataBuffer);

                    for (int i = 0; i < bytesRead; i++) {
                        String currSymbol = String.valueOf(data.charAt(i));
                        if (currSymbol.equalsIgnoreCase(System.lineSeparator())) {

                            if (csvReader == null) { // we know CSV first line is header
                                csvReader = new CSVReader(TaxiTripsModel.class, rowData, mCommaDelimiter.charAt(0));
                                rowData = "";
                                continue;
                            }

                            if (!rowData.isEmpty()) {
                                try {
                                    TaxiTripsModel taxiTripsModel = csvReader.read(rowData);
                                    taxiTripsModels.add(taxiTripsModel);
                                } catch (Exception ex) { // in any case if something wrong with data we will ignore it and process with next row
                                    LOGGER.debug(ex.getMessage(), ex);
                                }
                            }

                            rowData = "";
                            continue;
                        }
                        rowData += currSymbol;
                    }
                }
            }
        } catch (IOException ex) {
            LOGGER.debug(ex.getMessage(), ex);
        }

        return taxiTripsModels;
    }

    public void saveTaxiTrips(@NotNull TaxiTripsRepository tripsRepository, @NotNull TaxiTripsModel model) {
        if (model != null) {
            tripsRepository.saveAndFlush(model);
        }
    }

    public void saveTaxiTrips(@NotNull TaxiTripsRepository tripsRepository, @NotNull ArrayList<TaxiTripsModel> model) {
        tripsRepository.saveAll(model);
        tripsRepository.flush();
    }
}
