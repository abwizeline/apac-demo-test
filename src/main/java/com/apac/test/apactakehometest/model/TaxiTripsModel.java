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
    private Long id;

    @JsonProperty("TaxiID")
    private Long taxiID;

    @JsonProperty("VendorID")
    private long vendorID;

    @Convert(converter = DateConverter.class)
    private String lpep_pickup_datetime;

    @Convert(converter = DateConverter.class)
    private String lpep_dropoff_datetime;

    @Size(max = 40)
    private String store_and_fwd_flag;

    @JsonProperty("RatecodeID")
    private Long ratecodeID;

    @JsonProperty("PULocationID")
    private Long pulocationID;

    @JsonProperty("DOLocationID")
    private Long dolocationID;

    private Integer passenger_count;

    private Double trip_distance;

    private Double fare_amount;

    private Double extra;

    private Double mta_tax;

    private Double tip_amount;

    private Double tolls_amount;

    @Size(max = 40)
    private String ehail_fee;

    private Double improvement_surcharge;

    private Double total_amount;

    private Long payment_type;

    private Long trip_type;

    public TaxiTripsModel() {
    }

}
