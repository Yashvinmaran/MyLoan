package com.loan.repository;

import com.loan.entities.DocumentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends MongoRepository<DocumentEntity,String> {
    Optional<DocumentEntity> findByUserId(String userId);
}
