package com.apac.test.apactakehometest.controller;

import com.apac.test.apactakehometest.async.AsyncService;
import com.apac.test.apactakehometest.model.rest.ApiResponse;
import com.apac.test.apactakehometest.model.rest.CSVRestBodyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.apac.test.apactakehometest.ApacTakeHomeTestApplication.LOGGER;

@RestController
@RequestMapping("/v1/csv")
public class RestAPIController {

    @Autowired
    private AsyncService mAsyncService;

    @PostMapping("parser")
    public ResponseEntity<?> parser(@Valid @RequestBody CSVRestBodyModel csvRestBodyModel) {

        LOGGER.debug("Start time file download " + new Date(System.currentTimeMillis()));

        String csvUrl = csvRestBodyModel.getUrl();

        CompletableFuture<Boolean> asyncParseUrlFuture = mAsyncService.asyncParseFileUrl(csvUrl);
        CompletableFuture.allOf(asyncParseUrlFuture).join();

        Boolean dataUploaded = false;
        try {
            dataUploaded = asyncParseUrlFuture.get();

            LOGGER.debug("-Stop time file download " + new Date(System.currentTimeMillis()));
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (ExecutionException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (!dataUploaded) {
                return new ResponseEntity(new ApiResponse(csvUrl,false, "Something goes wrong!"), HttpStatus.BAD_REQUEST);
            }
        }

        return ResponseEntity.ok(new ApiResponse(csvUrl, true, "Uploaded!"));
    }
}
