package com.apac.test.apactakehometest.repository;

import com.apac.test.apactakehometest.model.TaxiTripsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaxiTripsService {

    @Autowired
    private TaxiTripsRepository mTaxiTripsRepository;

    public List<TaxiTripsModel> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TaxiTripsModel> pagedResult = mTaxiTripsRepository.findAll(pageable);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    public void deleteByID(Long id) {
        mTaxiTripsRepository.deleteById(id);
    }

    public TaxiTripsModel save(TaxiTripsModel taxiTripsModel) {
        return mTaxiTripsRepository.save(taxiTripsModel);
    }

    public TaxiTripsModel getByID(Long id) {
        Optional<TaxiTripsModel> taxiTripsModelOptional = mTaxiTripsRepository.findById(id);
        if (!taxiTripsModelOptional.isPresent()) {
            return null;
        }

        return taxiTripsModelOptional.get();
    }

    public List<TaxiTripsModel> searchByTaxiID(Long taxiID, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TaxiTripsModel> pagedResult = mTaxiTripsRepository.searchByTaxiID(taxiID, pageable);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }
}
