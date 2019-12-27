package com.apac.test.apactakehometest.controller;

import com.apac.test.apactakehometest.AbstractTest;
import com.apac.test.apactakehometest.async.AsyncService;
import com.apac.test.apactakehometest.model.TaxiTripsModel;
import com.apac.test.apactakehometest.model.rest.CSVRestBodyModel;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class RestApiControllerTest extends AbstractTest {

    final String uri = "/v1/csv/parser";

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void emptyPostRequest() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }

    @Test
    public void postRequestWithWrongBody() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bla", "blabalbla");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString());

        MvcResult mvcResult = mvc.perform(builder).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }

    @Test
    public void postRequestWithEmptyFile() throws Exception {
        CSVRestBodyModel csvRestBodyModel = new CSVRestBodyModel();
        csvRestBodyModel.setUrl("");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(csvRestBodyModel));

        MvcResult mvcResult = mvc.perform(builder).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }

    @Test
    public void postRequestWithNonCSVFile() throws Exception {
        CSVRestBodyModel csvRestBodyModel = new CSVRestBodyModel();
        csvRestBodyModel.setUrl("roooter.zip");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(csvRestBodyModel));

        MvcResult mvcResult = mvc.perform(builder).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }

    @Test
    public void postRequestWithCorrectCSVFile() throws Exception {
        String resourceName = "sample_file.csv";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        String absolutePath = file.getAbsolutePath();

        assertTrue(absolutePath.endsWith("/sample_file.csv"));

        AsyncService asyncService = new AsyncService();
        ArrayList<TaxiTripsModel> arrayList = asyncService.readFromStream(new FileInputStream(absolutePath));
        assertTrue(arrayList.size() == 19);
    }

    @Test
    public void postRequestWithCorrectCSVFileAndCheckOneRow() throws Exception {
        String resourceName = "sample_file.csv";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        String absolutePath = file.getAbsolutePath();

        assertTrue(absolutePath.endsWith("/sample_file.csv"));

        AsyncService asyncService = new AsyncService();
        ArrayList<TaxiTripsModel> arrayList = asyncService.readFromStream(new FileInputStream(absolutePath));
        assertTrue(arrayList.size() == 19);

        TaxiTripsModel taxiTripsModel = arrayList.get(4); // get random value

        assertTrue(taxiTripsModel.getTaxiID() == 13185);
        assertTrue(taxiTripsModel.getVendorID() == 2);
        assertTrue(taxiTripsModel.getStore_and_fwd_flag().equalsIgnoreCase("N"));
        assertTrue(taxiTripsModel.getRatecodeID() == 1);
        assertTrue(taxiTripsModel.getPulocationID() == 255);
        assertTrue(taxiTripsModel.getDolocationID() == 255);
        assertTrue(taxiTripsModel.getPassenger_count() == 1);
        assertTrue(taxiTripsModel.getTrip_distance() == 0.03D);
        assertTrue(taxiTripsModel.getFare_amount() == -3);
        assertTrue(taxiTripsModel.getExtra() == -0.5D);
        assertTrue(taxiTripsModel.getMta_tax() == -0.5D);
        assertTrue(taxiTripsModel.getTip_amount() == 0);
        assertTrue(taxiTripsModel.getTolls_amount() == 0);
        assertTrue(taxiTripsModel.getEhail_fee().isEmpty());
        assertTrue(taxiTripsModel.getImprovement_surcharge() == -0.3D);
        assertTrue(taxiTripsModel.getTotal_amount() == -4.3D);
        assertTrue(taxiTripsModel.getPayment_type() == 3);
        assertTrue(taxiTripsModel.getTrip_type() == 1);
    }

    @Test
    public void postRequestWithUselessCSVFile() throws Exception {
        CSVRestBodyModel csvRestBodyModel = new CSVRestBodyModel();
        csvRestBodyModel.setUrl("https://wz-tht-java-engineers.s3-ap-southeast-1.amazonaws.com/useless.csv");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(csvRestBodyModel));

        MvcResult mvcResult = mvc.perform(builder).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }
}
