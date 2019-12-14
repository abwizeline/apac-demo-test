package com.apac.test.apactakehometest.model.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private String url;
    private Boolean success;
    private String message;

    public ApiResponse(String url, Boolean success, String message) {
        this.url = url;
        this.success = success;
        this.message = message;
    }
}
