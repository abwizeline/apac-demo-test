package com.apac.test.apactakehometest.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "rateCodes")
public class RateCodeModel extends SimpleIDModel{
    public RateCodeModel() {}
    public RateCodeModel(long id) {
        super(id);
    }
}
