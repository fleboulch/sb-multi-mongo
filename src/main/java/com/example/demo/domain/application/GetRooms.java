package com.example.demo.domain.application;

import com.example.demo.infra.database.room.RoomMongo;
import com.example.demo.infra.database.room.RoomMongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetRooms {

    private final RoomMongoRepository repository;

    public GetRooms(RoomMongoRepository repository) {
        this.repository = repository;
    }

    public List<RoomMongo> getAll() {
        return repository.findAll();
    }
}
