package com.apac.test.apactakehometest.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class SimpleIDModel implements Serializable {
    @Id
    public long id;

    public SimpleIDModel(){}
    public SimpleIDModel(long id){
        setId(id);
    }

    public void setId(long id) {
        this.id = id;
    }
}
