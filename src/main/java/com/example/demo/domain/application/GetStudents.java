package com.example.demo.domain.application;

import com.example.demo.infra.database.student.StudentMongo;
import com.example.demo.infra.database.student.StudentMongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetStudents {

    private final StudentMongoRepository repository;

    public GetStudents(StudentMongoRepository repository) {
        this.repository = repository;
    }

    public List<StudentMongo> getAll() {
        return repository.findAll();
    }
}
