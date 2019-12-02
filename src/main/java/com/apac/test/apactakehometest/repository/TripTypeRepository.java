package com.apac.test.apactakehometest.repository;

import com.apac.test.apactakehometest.model.TripTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripTypeRepository extends JpaRepository<TripTypeModel, Long>{
}
