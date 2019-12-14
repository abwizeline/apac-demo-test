package com.apac.test.apactakehometest.model.rest;

import javax.validation.constraints.NotBlank;

public class CSVRestBodyModel {

    @NotBlank
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
