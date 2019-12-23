package com.apac.test.apactakehometest.repository;

import com.apac.test.apactakehometest.async.AsyncService;
import com.apac.test.apactakehometest.model.TaxiTripsModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static com.apac.test.apactakehometest.ApacTakeHomeTestApplication.LOGGER;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaxiTripsRepositoryTest {

    private final String defaultDateStr = "Thu Jan 01 00:00:00 UTC 1970";

    @Autowired
    TaxiTripsRepository taxiTripsRepository;

    @Test
    public void correctPickupTimeFormatWillSave() {
        taxiTripsRepository.deleteAll();

        AsyncService asyncService = new AsyncService();
        try {
            TaxiTripsModel taxiTripsModel = new TaxiTripsModel();
            taxiTripsModel.setLpep_pickup_datetime("2018-01-01 00:18:50");

            asyncService.saveTaxiTrips(taxiTripsRepository, taxiTripsModel);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            assertFalse(ex.getMessage(), true);
        }
        assertEquals(1, taxiTripsRepository.findAll().size());
        assertEquals("Mon Jan 01 04:18:50 UTC 2018", taxiTripsRepository.findAll().get(0).getLpep_pickup_datetime());
        assertEquals(defaultDateStr, taxiTripsRepository.findAll().get(0).getLpep_dropoff_datetime());
    }

    @Test
    public void emptyDatesWillSaveDefault(){
        taxiTripsRepository.deleteAll();

        AsyncService asyncService = new AsyncService();
        try {
            TaxiTripsModel taxiTripsModel = new TaxiTripsModel();
            asyncService.saveTaxiTrips(taxiTripsRepository, taxiTripsModel);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            assertFalse(ex.getMessage(), true);
        }
        assertEquals(1, taxiTripsRepository.findAll().size());
        assertEquals(defaultDateStr, taxiTripsRepository.findAll().get(0).getLpep_dropoff_datetime());
        assertEquals(defaultDateStr, taxiTripsRepository.findAll().get(0).getLpep_pickup_datetime());
    }

}
