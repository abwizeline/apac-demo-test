package com.apac.test.apactakehometest.repository;

import com.apac.test.apactakehometest.model.TaxiTripsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiTripsRepository extends JpaRepository<TaxiTripsModel, Long>{
}
