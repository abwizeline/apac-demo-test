package com.apac.test.apactakehometest.repository;

import com.apac.test.apactakehometest.async.AsyncService;
import com.apac.test.apactakehometest.model.TaxiTripsModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaxiTripsRepositoryTest {
    private static final Logger LOGGER = LogManager.getLogger(TaxiTripsRepositoryTest.class);

    private final String defaultDateStr = "Thu Jan 01 00:00:00 UTC 1970";

    @Autowired
    TaxiTripsRepository taxiTripsRepository;

    @Test
    public void correctPickupTimeFormatWillSave() {
        taxiTripsRepository.deleteAll();

        AsyncService asyncService = new AsyncService();
        try {
            TaxiTripsModel taxiTripsModel = new TaxiTripsModel();
            taxiTripsModel.setLpepPickupDatetime("2018-05-08 17:18:50");

            asyncService.saveTaxiTrips(taxiTripsRepository, taxiTripsModel);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            assertFalse(ex.getMessage(), true);
        }
        assertEquals(1, taxiTripsRepository.findAll().size());
        assertEquals("Tue May 08 17:18:50 UTC 2018", taxiTripsRepository.findAll().get(0).getLpepPickupDatetime());
        assertEquals(defaultDateStr, taxiTripsRepository.findAll().get(0).getLpepDropoffDatetime());
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
        assertEquals(defaultDateStr, taxiTripsRepository.findAll().get(0).getLpepDropoffDatetime());
        assertEquals(defaultDateStr, taxiTripsRepository.findAll().get(0).getLpepPickupDatetime());
    }

}
