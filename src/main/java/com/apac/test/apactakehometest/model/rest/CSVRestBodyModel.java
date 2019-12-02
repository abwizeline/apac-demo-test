package com.apac.test.apactakehometest.model.rest;

import javax.validation.constraints.NotBlank;

public class CSVRestBodyModel {

    @NotBlank
    private String urlCSV;

    public String getUrlCSV() {
        return urlCSV;
    }

    public void setUrlCSV(String urlCSV) {
        this.urlCSV = urlCSV;
    }
}
