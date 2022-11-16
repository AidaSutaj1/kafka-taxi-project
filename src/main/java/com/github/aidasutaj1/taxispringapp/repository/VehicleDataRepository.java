package com.github.aidasutaj1.taxispringapp.repository;

import com.github.aidasutaj1.taxispringapp.model.VehicleData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleDataRepository extends MongoRepository<VehicleData, Long> {

}
