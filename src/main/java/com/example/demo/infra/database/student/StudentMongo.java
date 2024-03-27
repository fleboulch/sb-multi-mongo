package com.example.demo.infra.database.student;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.UUID;

@Document(collection = "student")
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
public class StudentMongo implements Serializable {

    @Id
    private UUID id;

    private String name;

}
