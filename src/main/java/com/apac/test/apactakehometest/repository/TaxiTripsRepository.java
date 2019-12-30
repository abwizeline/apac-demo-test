package com.apac.test.apactakehometest.repository;

import com.apac.test.apactakehometest.model.TaxiTripsModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiTripsRepository extends JpaRepository<TaxiTripsModel, Long> {
    @Query(value = "select tableData from TaxiTripsModel tableData where tableData.taxiID = :taxiID",
        countQuery = "select count(tableData) from TaxiTripsModel tableData where tableData.taxiID = :taxiID")
    Page<TaxiTripsModel> searchByTaxiID(@Param("taxiID") Long taxiID, Pageable pageable);
}
