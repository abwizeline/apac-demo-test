package com.apac.test.apactakehometest.model;

import com.apac.test.apactakehometest.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "taxiTrips")
public class TaxiTripsModel extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(cascade = {CascadeType.MERGE}) // this fields my be added before inserting to this table
    @JoinColumn(name = "TaxiID", referencedColumnName = "id")
    TaxiModel taxiModel;

    @NotNull(message = "Please enter pick up time")
    @Convert(converter = DateConverter.class)
    @Column(nullable = false)
    String lpep_pickup_datetime;

    @Convert(converter = DateConverter.class)
    String lpep_dropoff_datetime;

    @Size(max = 40)
    String store_and_fwd_flag;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "RatecodeID")
    RateCodeModel rateCodeModel;

    @NotNull(message = "Please enter PU Location")
    long PULocationID;
    @NotNull(message = "Please enter DO Location")
    long DOLocationID;
    int passenger_count = 0;
    double trip_distance = 0.0D;
    double fare_amount = 0.0D;
    double extra = 0.0D;
    double mta_tax = 0.0D;
    double tip_amount = 0.0D;
    double tolls_amount = 0.0D;
    String ehail_fee = "";
    double improvement_surcharge = 0.0D;
    double total_amount = 0.0D;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "payment_type")
    PaymentTypeModel paymentTypeModel;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "trip_type")
    TripTypeModel tripTypeModel;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "VendorID")
    VendorModel vendorModel;

    public TaxiTripsModel() {
    }

    public void setTaxiModel(TaxiModel taxiModel) {
        this.taxiModel = taxiModel;
    }

    public void setRateCodeModel(RateCodeModel rateCodeModel) {
        this.rateCodeModel = rateCodeModel;
    }

    public void setPaymentTypeModel(PaymentTypeModel paymentTypeModel) {
        this.paymentTypeModel = paymentTypeModel;
    }

    public void setTripTypeModel(TripTypeModel tripTypeModel) {
        this.tripTypeModel = tripTypeModel;
    }

    public VendorModel getVendorModel() {
        return vendorModel;
    }

    public void setVendorModel(VendorModel vendorModel) {
        this.vendorModel = vendorModel;
    }


    public String getLpep_pickup_datetime() {
        return lpep_pickup_datetime;
    }

    public String getLpep_dropoff_datetime() {
        return lpep_dropoff_datetime;
    }

    public String getStore_and_fwd_flag() {
        return store_and_fwd_flag;
    }

    public long getPULocationID() {
        return PULocationID;
    }

    public long getDOLocationID() {
        return DOLocationID;
    }

    public int getPassenger_count() {
        return passenger_count;
    }

    public double getTrip_distance() {
        return trip_distance;
    }

    public double getFare_amount() {
        return fare_amount;
    }

    public double getExtra() {
        return extra;
    }

    public double getMta_tax() {
        return mta_tax;
    }

    public double getTip_amount() {
        return tip_amount;
    }

    public double getTolls_amount() {
        return tolls_amount;
    }

    public String getEhail_fee() {
        return ehail_fee;
    }

    public double getImprovement_surcharge() {
        return improvement_surcharge;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setLpep_pickup_datetime(String lpep_pickup_datetime) {
        this.lpep_pickup_datetime = lpep_pickup_datetime;
    }

    public void setLpep_dropoff_datetime(String lpep_dropoff_datetime) {
        this.lpep_dropoff_datetime = lpep_dropoff_datetime;
    }

    public void setStore_and_fwd_flag(String store_and_fwd_flag) {
        this.store_and_fwd_flag = store_and_fwd_flag;
    }

    public void setPULocationID(long PULocationID) {
        this.PULocationID = PULocationID;
    }

    public void setDOLocationID(long DOLocationID) {
        this.DOLocationID = DOLocationID;
    }

    public void setPassenger_count(int passenger_count) {
        this.passenger_count = passenger_count;
    }

    public void setTrip_distance(double trip_distance) {
        this.trip_distance = trip_distance;
    }

    public void setFare_amount(double fare_amount) {
        this.fare_amount = fare_amount;
    }

    public void setExtra(double extra) {
        this.extra = extra;
    }

    public void setMta_tax(double mta_tax) {
        this.mta_tax = mta_tax;
    }

    public void setTip_amount(double tip_amount) {
        this.tip_amount = tip_amount;
    }

    public void setTolls_amount(double tolls_amount) {
        this.tolls_amount = tolls_amount;
    }

    public void setEhail_fee(String ehail_fee) {
        this.ehail_fee = ehail_fee;
    }

    public void setImprovement_surcharge(double improvement_surcharge) {
        this.improvement_surcharge = improvement_surcharge;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

}
