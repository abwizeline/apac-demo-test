package com.apac.test.apactakehometest.controller;

import com.apac.test.apactakehometest.async.AsyncService;
import com.apac.test.apactakehometest.model.TaxiTripsModel;
import com.apac.test.apactakehometest.model.rest.ApiResponse;
import com.apac.test.apactakehometest.model.rest.CSVRestBodyModel;
import com.apac.test.apactakehometest.repository.TaxiTripsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/v1/taxitrips")
public class TaxiTripsController {
    private static final Logger LOGGER = LogManager.getLogger(TaxiTripsController.class);

    @Autowired
    private TaxiTripsService mTaxiTripsService;

    @Autowired
    private AsyncService mAsyncService;

    @GetMapping()
    public List<TaxiTripsModel> findAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return mTaxiTripsService.getAll(page, size);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        mTaxiTripsService.deleteByID(id);
    }

    @PostMapping()
    public ResponseEntity<TaxiTripsModel> addTaxiTrip(@RequestBody TaxiTripsModel taxiTripsModel) {
        TaxiTripsModel savedTaxiTripsModel = mTaxiTripsService.save(taxiTripsModel);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("get/{id}")
            .buildAndExpand(savedTaxiTripsModel.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping()
    public ResponseEntity<TaxiTripsModel> updateTaxiTrip(@RequestBody TaxiTripsModel taxiTripsModel, @PathVariable Long id) {

        TaxiTripsModel taxiTrips = mTaxiTripsService.getByID(id);
        if (taxiTrips == null) {
            return ResponseEntity.notFound().build();
        }
        taxiTripsModel.setId(id);
        mTaxiTripsService.save(taxiTripsModel);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public @ResponseBody
    ResponseEntity<TaxiTripsModel> getById(@PathVariable("id") Long id) {
        TaxiTripsModel taxiTripsModel = mTaxiTripsService.getByID(id);
        if (taxiTripsModel == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(taxiTripsModel);
    }

    @GetMapping("searchByTaxi/{taxiID}")
    public List<TaxiTripsModel> searchByTaxi(@PathVariable("taxiID") Long taxiID, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return mTaxiTripsService.searchByTaxiID(taxiID, page, size);
    }

    @PostMapping("import-csv")
    public ResponseEntity<?> importCsv(@Valid @RequestBody CSVRestBodyModel csvRestBodyModel) {

        LOGGER.info("Start time file download " + new Date(System.currentTimeMillis()));

        String csvUrl = csvRestBodyModel.getUrl();

        CompletableFuture<Boolean> asyncParseUrlFuture = mAsyncService.asyncParseFileUrl(csvUrl);
        CompletableFuture.allOf(asyncParseUrlFuture).join();

        Boolean dataUploaded = false;
        try {
            dataUploaded = asyncParseUrlFuture.get();

            LOGGER.info("-Stop time file download " + new Date(System.currentTimeMillis()));
        } catch (InterruptedException e) {
            LOGGER.debug(e.getMessage(), e);
        } catch (ExecutionException e) {
            LOGGER.debug(e.getMessage(), e);
        } finally {
            if (!dataUploaded) {
                return new ResponseEntity(new ApiResponse(csvUrl,false, "Something goes wrong!"), HttpStatus.BAD_REQUEST);
            }
        }

        return ResponseEntity.ok(new ApiResponse(csvUrl, true, "Uploaded!"));
    }

}
