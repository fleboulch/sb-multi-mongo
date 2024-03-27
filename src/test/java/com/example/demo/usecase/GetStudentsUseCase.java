package com.example.demo.usecase;

import com.example.demo.TestDemoApplication;
import com.example.demo.infra.database.student.StudentMongo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@Import(TestDemoApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GetStudentsUseCase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection("student");
    }

    @Test
    void request_ok() throws Exception {
        // arrange
        mongoTemplate.insert(StudentMongo.builder().id(UUID.randomUUID()).name("florent").build());

        // act
        mockMvc.perform(MockMvcRequestBuilders.get("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(
                        //language=json
                        """
                        [
                            {
                                "name": "FLORENT"
                            }
                        ]
                        """
                ));

    }
}
