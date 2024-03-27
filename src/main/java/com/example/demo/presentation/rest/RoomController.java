package com.example.demo.presentation.rest;

import com.example.demo.domain.application.GetRooms;
import com.example.demo.infra.database.room.RoomMongo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final GetRooms handler;

    public RoomController(GetRooms handler) {
        this.handler = handler;
    }

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAll() {
        List<RoomResponse> response = handler.getAll()
                .stream()
                .map(this::toPresentation)
                .toList();

        return ResponseEntity.ok().body(response);
    }

    private RoomResponse toPresentation(RoomMongo infra) {
        return new RoomResponse(infra.getNumber());
    }
}
