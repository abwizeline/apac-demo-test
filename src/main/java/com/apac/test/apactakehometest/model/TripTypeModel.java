package com.apac.test.apactakehometest.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tripType")
public class TripTypeModel extends SimpleIDModel{
    public TripTypeModel(){}
    public TripTypeModel(long id) {
        super(id);
    }
}
