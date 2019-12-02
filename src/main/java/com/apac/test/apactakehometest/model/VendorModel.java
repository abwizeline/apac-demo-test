package com.apac.test.apactakehometest.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vendors")
public class VendorModel extends SimpleIDModel{

    static final long serialVersionUID = 2L;

    public VendorModel(){}
    public VendorModel(long id) {
        super(id);
    }

}
