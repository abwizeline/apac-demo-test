package com.apac.test.apactakehometest.controller;

import com.apac.test.apactakehometest.AbstractTest;
import com.apac.test.apactakehometest.async.AsyncService;
import com.apac.test.apactakehometest.model.rest.CSVRestBodyModel;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RestApiControllerTest extends AbstractTest {

    final String uri = "/v1/csv/parse";
    final String correctFileUrl = "https://wz-tht-java-engineers.s3-ap-southeast-1.amazonaws.com/green_tripdata_2018-01_with_TaxiID.csv";

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void emptyPostRequest() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
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
        assertEquals(400, status);
    }

    @Test
    public void postRequestWithEmptyFile() throws Exception {
        CSVRestBodyModel csvRestBodyModel = new CSVRestBodyModel();
        csvRestBodyModel.setUrlCSV("");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(csvRestBodyModel));

        MvcResult mvcResult = mvc.perform(builder).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void postRequestWithNonCSVFile() throws Exception {
        CSVRestBodyModel csvRestBodyModel = new CSVRestBodyModel();
        csvRestBodyModel.setUrlCSV("roooter.zip");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(csvRestBodyModel));

        MvcResult mvcResult = mvc.perform(builder).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void postRequestWithCorrectCSVFile() throws Exception {
        CSVRestBodyModel csvRestBodyModel = new CSVRestBodyModel();
        csvRestBodyModel.setUrlCSV(correctFileUrl);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(csvRestBodyModel));

        MvcResult mvcResult = mvc.perform(builder).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void postRequestWithUselessCSVFile() throws Exception {
        CSVRestBodyModel csvRestBodyModel = new CSVRestBodyModel();
        csvRestBodyModel.setUrlCSV("https://wz-tht-java-engineers.s3-ap-southeast-1.amazonaws.com/useless.csv");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(csvRestBodyModel));

        MvcResult mvcResult = mvc.perform(builder).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void weActuallyDownloadFile() {
        AsyncService asyncService = new AsyncService();
        CompletableFuture<String> future = asyncService.asyncDownloadFile(correctFileUrl);

        CompletableFuture.allOf(future).join();

        String filePath = null;

        try {
            filePath = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        assertNotEquals(null, filePath);
        assertNotEquals("", filePath);

        File file = new File(filePath);
        assertEquals(true, file.exists());
    }

}
