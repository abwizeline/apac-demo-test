package com.apac.test.apactakehometest.controller;

import com.apac.test.apactakehometest.async.AsyncService;
import com.apac.test.apactakehometest.model.PaymentTypeModel;
import com.apac.test.apactakehometest.model.TaxiModel;
import com.apac.test.apactakehometest.model.TaxiTripsModel;
import com.apac.test.apactakehometest.model.VendorModel;
import com.apac.test.apactakehometest.model.rest.ApiResponse;
import com.apac.test.apactakehometest.model.rest.CSVRestBodyModel;
import com.apac.test.apactakehometest.repository.PaymentTypeRepository;
import com.apac.test.apactakehometest.repository.TaxiRepository;
import com.apac.test.apactakehometest.repository.TaxiTripsRepository;
import com.apac.test.apactakehometest.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/csv")
public class RestAPIController {

    @Autowired
    private AsyncService asyncService;

    @Autowired
    TaxiTripsRepository taxiTripsRepository;

    @Autowired
    VendorRepository vendorRepository;
    @Autowired
    PaymentTypeRepository paymentTypeRepository;
    @Autowired
    TaxiRepository taxiRepository;


    @PostMapping("test")
    public ResponseEntity<?> test() {

        for(int i = 0; i< 20; i++) {
            TaxiTripsModel taxiTripsModel = new TaxiTripsModel();
            taxiTripsModel.setLpep_pickup_datetime("");
            taxiTripsModel.setTaxiModel(taxiRepository.findById(1L).orElse(new TaxiModel(1L)));
            taxiTripsModel.setVendorModel(vendorRepository.findById(1L).orElse(new VendorModel(1L)));
            taxiTripsModel.setPaymentTypeModel(paymentTypeRepository.findById(1L).orElse(new PaymentTypeModel(1L)));

            taxiTripsRepository.save(taxiTripsModel);
        }

        return ResponseEntity.ok(new ApiResponse(true, "Parsed!"));
    };

    @PostMapping("parse")
    public ResponseEntity<?> parse(@RequestBody CSVRestBodyModel csvRestBodyModel) {

        System.out.println("Start time file download " + new Date(System.currentTimeMillis()));
        String csvFileURL = csvRestBodyModel.getUrlCSV();

        if(csvFileURL == null){
            return new ResponseEntity(new ApiResponse(false, "Wrong file url!"), HttpStatus.BAD_REQUEST);
        }

        CompletableFuture<String> asyncReadFileFuture = asyncService.asyncDownloadFile(csvFileURL);
        CompletableFuture.allOf(asyncReadFileFuture).join();

        String newFilePath = "";
        try {
            newFilePath = asyncReadFileFuture.get();
            if (newFilePath == null || newFilePath.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, "Can't save/read file from url"), HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            return new ResponseEntity(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
        System.out.println("-Stop time file download " + new Date(System.currentTimeMillis()));

        File csvFile = new File(newFilePath);
        if (!csvFile.exists()) {
            return new ResponseEntity(new ApiResponse(false, "Wrong file url!"), HttpStatus.BAD_REQUEST);
        }

        System.out.println("Start time DB upload " + new Date(System.currentTimeMillis()));
        CompletableFuture<Boolean> insertDataFuture = asyncService.insertData(csvFile);
        CompletableFuture.allOf(insertDataFuture).join();

        csvFile.delete();

        System.out.println("-Stop time DB upload " + new Date(System.currentTimeMillis()));
        try {
            Boolean result = insertDataFuture.get();
            if (result) {
                return ResponseEntity.ok(new ApiResponse(true, "Parsed!"));
            } else {
                return new ResponseEntity(new ApiResponse(false, "Data not inserted"), HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            return new ResponseEntity(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


}
