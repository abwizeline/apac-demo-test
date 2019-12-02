package com.apac.test.apactakehometest.repository;

import com.apac.test.apactakehometest.async.AsyncService;
import com.apac.test.apactakehometest.model.TaxiTripsModel;
import com.apac.test.apactakehometest.repository.TaxiTripsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaxiTripsRepositoryTest {

    @Autowired
    TaxiTripsRepository taxiTripsRepository;

    @Test
    public void emptyTaxiID() {

        taxiTripsRepository.deleteAll();

        AsyncService asyncService = new AsyncService();
        try {
            TaxiTripsModel taxiTripsModel = new TaxiTripsModel();
            asyncService.saveTaxiTrips(taxiTripsRepository, taxiTripsModel);
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
        }

        assertEquals(0, taxiTripsRepository.findAll().size());
    }

    @Test
    public void wrongPickupTimeFormat() {
        taxiTripsRepository.deleteAll();

        AsyncService asyncService = new AsyncService();
        try {
            TaxiTripsModel taxiTripsModel = new TaxiTripsModel();
            taxiTripsModel.setLpep_pickup_datetime("01-01-2019 00:18:50");

            asyncService.saveTaxiTrips(taxiTripsRepository, taxiTripsModel);
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
        }
        assertEquals(1, taxiTripsRepository.findAll().size());
    }

    @Test
    public void wrongInputArray() {
        AsyncService asyncService = new AsyncService();
        TaxiTripsModel taxiTripsModel = null;
        try {
            taxiTripsModel = asyncService.fromDataArray(new String[]{});
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        assertEquals(null, taxiTripsModel);
    }

    @Test
    public void correctInputArray() {
        AsyncService asyncService = new AsyncService();
        String[] inputData = new String[]{"17083", "2"
                , "2018-01-01 00:18:50"
                , "2018-01-01 00:24:39"
                , "N"
                , "1"
                , "236"
                , "236"
                , "5"
                , "0.7"
                , "6"
                , "0.5"
                , "0.5"
                , "0"
                , "0"
                , ""
                , "0.3"
                , "7.3"
                , "2"
                , "1"};

        TaxiTripsModel taxiTripsModel = null;
        try {
            taxiTripsModel = asyncService.fromDataArray(inputData);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        assertNotEquals(null, taxiTripsModel);
        assertEquals(236, taxiTripsModel.getDOLocationID());
        assertEquals(0.5D, taxiTripsModel.getExtra(), 0.0D);
    }

    @Test
    public void missedValuesInInputArray() {
        AsyncService asyncService = new AsyncService();
        String[] inputData = new String[]{"17083", "2"
                , "2018-01-01 00:18:50"
                , "2018-01-01 00:24:39"
                , "N"
                , "1"
                , "5"
                , "0.7"
                , "6"
                , "0.5"
                , "0.5"
                , "0"
                , "0"
                , ""
                , "0.3"
                , "7.3"
                , "2"
                , "1"};

        TaxiTripsModel taxiTripsModel = null;
        try {
            taxiTripsModel = asyncService.fromDataArray(inputData);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        assertEquals(null, taxiTripsModel);
    }

    @Test
    public void correctInputArrayDataValidButNoRefferentTables() {

        taxiTripsRepository.deleteAll();

        AsyncService asyncService = new AsyncService();
        String[] inputData = new String[]{"17083", "2"
                , "2018-01-01 00:18:50"
                , "2018-01-01 00:24:39"
                , "N"
                , "1"
                , "236"
                , "236"
                , "5"
                , "0.7"
                , "6"
                , "0.5"
                , "0.5"
                , "0"
                , "0"
                , ""
                , "0.3"
                , "7.3"
                , "2"
                , "1"};

        TaxiTripsModel taxiTripsModel = null;
        try {
            taxiTripsModel = asyncService.fromDataArray(inputData);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        assertNotEquals(null, taxiTripsModel);
        assertEquals(236, taxiTripsModel.getDOLocationID());
        assertEquals(0.5D, taxiTripsModel.getExtra(), 0.0D);

        try {
            ArrayList<TaxiTripsModel> arrayList = new ArrayList<>();
            arrayList.add(taxiTripsModel);

            asyncService.saveTaxiTrips(taxiTripsRepository, arrayList);
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
        }

        assertEquals(0, taxiTripsRepository.findAll().size());
    }

}
