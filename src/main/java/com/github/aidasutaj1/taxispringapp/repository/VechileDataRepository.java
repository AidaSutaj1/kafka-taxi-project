package com.github.aidasutaj1.taxispringapp.repository;

import com.github.aidasutaj1.taxispringapp.documents.VechileData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VechileDataRepository extends MongoRepository<VechileData, Long> {

}
