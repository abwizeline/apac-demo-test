package com.apac.test.apactakehometest.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "taxi")
public class TaxiModel implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    public long id;

    public TaxiModel() { }

    public TaxiModel(long id) {
        this.id = id;
    }

}
