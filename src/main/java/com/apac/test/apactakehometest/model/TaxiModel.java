package com.apac.test.apactakehometest.model;

import javax.persistence.Entity;

@Entity(name = "taxi")
public class TaxiModel extends SimpleIDModel {
    public TaxiModel(){}
    public TaxiModel(long id) {
        super(id);
    }
}
