package com.apac.test.apactakehometest.model;

import javax.persistence.*;

@Entity
@Table(name = "paymentType")
public class PaymentTypeModel extends SimpleIDModel{
    public PaymentTypeModel() {}
    public PaymentTypeModel(long id) {
        super(id);
    }
}
