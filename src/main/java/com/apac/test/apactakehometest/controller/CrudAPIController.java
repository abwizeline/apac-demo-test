package com.apac.test.apactakehometest.controller;

import com.apac.test.apactakehometest.model.TaxiTripsModel;
import com.apac.test.apactakehometest.repository.TaxiTripsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/taxitrips")
public class CrudAPIController {

    @Autowired
    private TaxiTripsService mTaxiTripsService;

    @GetMapping(value = "all")
    public List<TaxiTripsModel> all(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return mTaxiTripsService.getAll(page, size);
    }

    @DeleteMapping(value = "delete/{id}")
    public void deleteById(@PathVariable Long id) {
        mTaxiTripsService.deleteByID(id);
    }

    @PostMapping(value = "add")
    public ResponseEntity<TaxiTripsModel> addTaxiTrip(@RequestBody TaxiTripsModel taxiTripsModel) {
        TaxiTripsModel savedTaxiTripsModel = mTaxiTripsService.save(taxiTripsModel);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("get/{id}")
            .buildAndExpand(savedTaxiTripsModel.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "update")
    public ResponseEntity<TaxiTripsModel> updateTaxiTrip(@RequestBody TaxiTripsModel taxiTripsModel, @PathVariable Long id) {

        TaxiTripsModel taxiTrips = mTaxiTripsService.getByID(id);
        if (taxiTrips == null) {
            return ResponseEntity.notFound().build();
        }
        taxiTripsModel.setId(id);
        mTaxiTripsService.save(taxiTripsModel);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("get/{id}")
    public @ResponseBody ResponseEntity<TaxiTripsModel> getById(@PathVariable("id") Long id) {
        TaxiTripsModel taxiTripsModel = mTaxiTripsService.getByID(id);
        if (taxiTripsModel == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(taxiTripsModel);
    }

    @GetMapping("searchByTaxi/{taxiID}")
    public List<TaxiTripsModel> searchByTaxi(@PathVariable("taxiID") Long taxiID, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        return mTaxiTripsService.searchByTaxiID(taxiID, page, size);
    }

}
