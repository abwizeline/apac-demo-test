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

    @Override
    public int hashCode() {
        return (int) id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        SimpleIDModel newObj = (SimpleIDModel) obj;
        return newObj.id == this.id;
    }
}
