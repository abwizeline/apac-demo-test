package com.apac.test.apactakehometest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
@Entity
@Table(name = "taxiTrips")
@Getter
@Setter
public class TaxiTripsModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonProperty("TaxiID")
    private long taxiID;

    @JsonProperty("VendorID")
    private long vendorID;

    @Convert(converter = DateConverter.class)
    private String lpep_pickup_datetime;

    @Convert(converter = DateConverter.class)
    private String lpep_dropoff_datetime;

    @Size(max = 40)
    private String store_and_fwd_flag;

    @JsonProperty("RatecodeID")
    private long ratecodeID;

    @JsonProperty("PULocationID")
    private long pulocationID;

    @JsonProperty("DOLocationID")
    private long dolocationID;

    private int passenger_count;

    private double trip_distance;

    private double fare_amount;

    private double extra;

    private double mta_tax;

    private double tip_amount;

    private double tolls_amount;

    @Size(max = 40)
    private String ehail_fee;

    private double improvement_surcharge;

    private double total_amount;

    private long payment_type;

    private long trip_type;

    public TaxiTripsModel() {
    }

}
