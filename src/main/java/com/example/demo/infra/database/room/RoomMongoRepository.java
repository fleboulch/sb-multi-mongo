package com.example.demo.infra.database.room;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomMongoRepository extends MongoRepository<RoomMongo, UUID> {
}
