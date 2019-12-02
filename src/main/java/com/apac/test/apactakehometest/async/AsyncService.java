package com.apac.test.apactakehometest.async;

import com.apac.test.apactakehometest.model.*;
import com.apac.test.apactakehometest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {

    private static final String COMMA_DELIMITER = ",";
    private static final int FILE_COLUMNS_COUNT = 20;
    private static final String EXTENSION = ".csv";

    @Autowired
    TaxiTripsRepository taxiTripsRepository;
    @Autowired
    PaymentTypeRepository paymentTypeRepository;
    @Autowired
    RateCodeRepository rateCodeRepository;
    @Autowired
    TaxiRepository taxiRepository;
    @Autowired
    TripTypeRepository tripTypeRepository;
    @Autowired
    VendorRepository vendorRepository;

    @Async("asyncExecutor")
    public CompletableFuture<String> asyncDownloadFile(String csvFileURL) {

        if (!csvFileURL.endsWith(EXTENSION)) {
            return CompletableFuture.completedFuture(null);
        }
        String tempDir = System.getProperty("java.io.tmpdir");
        if (!tempDir.endsWith("/") && !tempDir.endsWith("\\")) {
            tempDir = tempDir + "/";
        }

        String FILE_NAME = tempDir + System.currentTimeMillis() + EXTENSION;
        File csvFile = new File(FILE_NAME);
        if (!csvFile.exists()) {
            try {
                csvFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return CompletableFuture.completedFuture(null);
            }
        }
        try (BufferedInputStream in = new BufferedInputStream(new URL(csvFileURL).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.completedFuture(csvFile.getAbsolutePath());
    }

    @Async("asyncExecutor")
    public CompletableFuture<Boolean> insertData(File csvFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile.getAbsolutePath()));
            String line;
            int firstLineCounter = 0;

            while ((line = br.readLine()) != null) {
                if (line == null || line.isEmpty()) {
                    continue;
                }

                String[] dataArray = line.split(COMMA_DELIMITER);
                if (firstLineCounter == 0) { // first line always headers in csv
                    if (dataArray.length < FILE_COLUMNS_COUNT) {
                        return CompletableFuture.completedFuture(false);
                    }

                    firstLineCounter++;
                    continue;
                }

                try {
                    TaxiTripsModel taxiTripsModel = fromDataArray(dataArray);
                    saveTaxiTrips(taxiTripsRepository, taxiTripsModel);
                } catch (Exception ex) { // for now we just ignore incorrect data types and violation exceptions
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return CompletableFuture.completedFuture(false);
        }

        return CompletableFuture.completedFuture(true);
    }

    public TaxiTripsModel fromDataArray(String[] dataArray) throws ArrayIndexOutOfBoundsException, NumberFormatException {
        TaxiTripsModel taxiTripsModel = new TaxiTripsModel();

        long taxiId = Long.parseLong(dataArray[0]);
        //taxiTripsModel.setTaxiModel(taxiRepository.findById(taxiId).orElse(new TaxiModel(taxiId)));

        long vendorId = Long.parseLong(dataArray[1]);
        //taxiTripsModel.setVendorModel(vendorRepository.findById(vendorId).orElse(new VendorModel(vendorId)));

        taxiTripsModel.setLpep_pickup_datetime(dataArray[2]);
        taxiTripsModel.setLpep_dropoff_datetime(dataArray[3]);
        taxiTripsModel.setStore_and_fwd_flag(dataArray[4]);

        long rateCode = Long.parseLong(dataArray[5]);
        //taxiTripsModel.setRateCodeModel(rateCodeRepository.findById(rateCode).orElse(new RateCodeModel(rateCode)));

        taxiTripsModel.setPULocationID(Long.parseLong(dataArray[6]));
        taxiTripsModel.setDOLocationID(Long.parseLong(dataArray[7]));
        taxiTripsModel.setPassenger_count(Integer.parseInt(dataArray[8]));
        taxiTripsModel.setTrip_distance(Double.parseDouble(dataArray[9]));
        taxiTripsModel.setFare_amount(Double.parseDouble(dataArray[10]));
        taxiTripsModel.setExtra(Double.parseDouble(dataArray[11]));
        taxiTripsModel.setMta_tax(Double.parseDouble(dataArray[12]));
        taxiTripsModel.setTip_amount(Double.parseDouble(dataArray[13]));
        taxiTripsModel.setTolls_amount(Double.parseDouble(dataArray[14]));
        taxiTripsModel.setEhail_fee(dataArray[15]);
        taxiTripsModel.setImprovement_surcharge(Double.parseDouble(dataArray[16]));
        taxiTripsModel.setTotal_amount(Double.parseDouble(dataArray[17]));

        long paymentType = Long.parseLong(dataArray[18]);
        //taxiTripsModel.setPaymentTypeModel(paymentTypeRepository.findById(paymentType).orElse(new PaymentTypeModel(paymentType)));

        long tripType = Long.parseLong(dataArray[19]);
        //taxiTripsModel.setTripTypeModel(tripTypeRepository.findById(tripType).orElse(new TripTypeModel(tripType)));

        return taxiTripsModel;
    }

    @Transactional
    public void saveTaxiTrips(@NotNull TaxiTripsRepository tripsRepository, @NotNull TaxiTripsModel model) throws Exception {
        if (model != null) {
            tripsRepository.saveAndFlush(model);
        }
    }

}
