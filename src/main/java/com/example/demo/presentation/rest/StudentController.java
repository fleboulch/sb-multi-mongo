package com.example.demo.presentation.rest;

import com.example.demo.domain.application.GetStudents;
import com.example.demo.infra.database.student.StudentMongo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final GetStudents handler;

    public StudentController(GetStudents handler) {
        this.handler = handler;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAll() {
        List<StudentResponse> response = handler.getAll()
                .stream()
                .map(this::toPresentation)
                .toList();

        return ResponseEntity.ok().body(response);
    }

    private StudentResponse toPresentation(StudentMongo infra) {
        return new StudentResponse(infra.getName().toUpperCase());
    }
}
