package com.apac.test.apactakehometest.repository;

import com.apac.test.apactakehometest.model.TaxiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiRepository extends JpaRepository<TaxiModel, Long> {
}
