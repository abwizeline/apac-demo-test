package com.apac.test.apactakehometest.repository;

import com.apac.test.apactakehometest.model.VendorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<VendorModel, Long> {
}
