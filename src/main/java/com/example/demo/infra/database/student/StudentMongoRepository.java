package com.example.demo.infra.database.student;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMongoRepository extends MongoRepository<StudentMongo, String> {
}
