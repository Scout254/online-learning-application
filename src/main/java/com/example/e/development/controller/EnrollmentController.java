package com.example.e.development.controller;

import com.example.e.development.enrollment.EnrollmentRequest;
import com.example.e.development.enrollment.Enrollment;
import com.example.e.development.enrollment.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")

public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollUser(@RequestBody EnrollmentRequest enrollmentRequest) {
        Enrollment enrollments =  enrollmentService.enrollUser(enrollmentRequest);
        return ResponseEntity.ok("Enrollment successful");
    }
}
