package com.apac.test.apactakehometest.controller;

import com.apac.test.apactakehometest.model.rest.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WhiteLabelController {
    @GetMapping("/")
    public ResponseEntity<?> whiteLabel(){
        return ResponseEntity.ok(new ApiResponse(true, "I am here!"));
    }
}
