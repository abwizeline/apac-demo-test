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

    @Column(name = "lpep_pickup_datetime")
    @JsonProperty("lpep_pickup_datetime")
    @Convert(converter = DateConverter.class)
    private String lpepPickupDatetime;

    @Column(name = "lpep_dropoff_datetime")
    @JsonProperty("lpep_dropoff_datetime")
    @Convert(converter = DateConverter.class)
    private String lpepDropoffDatetime;

    @Column(name = "store_and_fwd_flag")
    @JsonProperty("store_and_fwd_flag")
    @Size(max = 40)
    private String storeAndFwdFlag;

    @JsonProperty("RatecodeID")
    private Long ratecodeID;

    @JsonProperty("PULocationID")
    private Long pulocationID;

    @JsonProperty("DOLocationID")
    private Long dolocationID;

    @Column(name = "passenger_count")
    @JsonProperty("passenger_count")
    private Integer passengerCount;

    @Column(name = "trip_distance")
    @JsonProperty("trip_distance")
    private Double tripDistance;

    @Column(name = "fare_amount")
    @JsonProperty("fare_amount")
    private Double fareAmount;

    private Double extra;

    @Column(name = "mta_tax")
    @JsonProperty("mta_tax")
    private Double mtaTax;

    @Column(name = "tip_amount")
    @JsonProperty("tip_amount")
    private Double tipAmount;

    @Column(name = "tolls_amount")
    @JsonProperty("tolls_amount")
    private Double tollsAmount;

    @Column(name = "ehail_fee")
    @JsonProperty("ehail_fee")
    @Size(max = 40)
    private String ehailFee;

    @Column(name = "improvement_surcharge")
    @JsonProperty("improvement_surcharge")
    private Double improvementSurcharge;

    @Column(name = "total_amount")
    @JsonProperty("total_amount")
    private Double totalAmount;

    @Column(name = "payment_type")
    @JsonProperty("payment_type")
    private Long paymentType;

    @Column(name = "trip_type")
    @JsonProperty("trip_type")
    private Long tripType;

    public TaxiTripsModel() {
    }

}
