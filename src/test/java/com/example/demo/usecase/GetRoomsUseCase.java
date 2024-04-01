package com.example.demo.usecase;

import com.example.demo.common.IntegrationTest;
import com.example.demo.infra.database.room.RoomMongo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@IntegrationTest
public class GetRoomsUseCase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @Qualifier("secondaryMongoTemplate")
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection("room");
    }

    @Test
    void request_ok() throws Exception {
        // arrange
        mongoTemplate.insert(RoomMongo.builder().id(UUID.randomUUID()).number(11).build());

        // act
        mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(
                        //language=json
                        """
                        [
                            {
                                "number": 11
                            }
                        ]
                        """
                ));

    }
}
