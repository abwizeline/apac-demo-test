package com.apac.test.apactakehometest.repository;

import com.apac.test.apactakehometest.model.RateCodeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateCodeRepository extends JpaRepository<RateCodeModel, Long>{
}
