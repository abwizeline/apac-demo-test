package com.apac.test.apactakehometest.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity
@Table(name = "taxiTrips")
@Getter
@Setter
public class TaxiTripsModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long TaxiID;

    private long VendorID;

    @Convert(converter = DateConverter.class)
    private String lpep_pickup_datetime;

    @Convert(converter = DateConverter.class)
    private String lpep_dropoff_datetime;

    @Size(max = 40)
    private String store_and_fwd_flag;

    private long RatecodeID;

    private long PULocationID;

    private long DOLocationID;

    private int passenger_count = 0;

    private double trip_distance = 0.0D;

    private double fare_amount = 0.0D;

    private double extra = 0.0D;

    private double mta_tax = 0.0D;

    private double tip_amount = 0.0D;

    private double tolls_amount = 0.0D;

    @Size(max = 40)
    private String ehail_fee;

    private double improvement_surcharge = 0.0D;

    private double total_amount = 0.0D;

    private long payment_type;

    private long trip_type;

    public TaxiTripsModel() {
    }

}
