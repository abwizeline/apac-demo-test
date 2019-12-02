package com.apac.test.apactakehometest.repository;

import com.apac.test.apactakehometest.model.PaymentTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentTypeModel, Long> {
}
