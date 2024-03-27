package com.example.demo.infra.database.room;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.UUID;

@Document(collection = "room")
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
public class RoomMongo implements Serializable {

    @Id
    private UUID id;

    private int number;

}
