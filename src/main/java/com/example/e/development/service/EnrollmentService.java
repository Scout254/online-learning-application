package com.example.e.development.service;

import com.example.e.development.models.*;
import com.example.e.development.dto.EnrollmentRequest;
import com.example.e.development.repository.CourseRepository;
import com.example.e.development.repository.EnrollmentRepository;
import com.example.e.development.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
public class EnrollmentService {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    EnrollmentRepository enrollmentRepository;

    public Enrollment enrollUser(EnrollmentRequest enrollmentRequest) {
        try {
            Long userId = enrollmentRequest.getUserId();
            Long courseId = enrollmentRequest.getCourseId();

            // Check if the user has already enrolled in the specified course
            if (enrollmentRepository.existsByUser_IdAndCourse_Id(userId, courseId)) {
                throw new IllegalArgumentException("User has already enrolled in this course");
            }
            // Retrieve user and course entities
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new EntityNotFoundException("Course not found"));


            // Check if the user is premium or the course is free or paid
            if (user.getUserType() == UserType.PREMIUM || course.getCourseType() == CourseType.FREE) {
                // Premium users can enroll in any course, and all users can enroll in free courses

                // Create enrollment
                Enrollment enrollment = new Enrollment();
                enrollment.setUser(user);
                enrollment.setCourse(course);
                enrollment.setEnrollmentDate(LocalDateTime.now()); // Set the enrollment date to the current date

                course.setEnrollmentCount(course.getEnrollmentCount() + 1);
                enrollmentRepository.save(enrollment);

                return enrollment;
            } else if (course.getCourseType() == CourseType.PAID) {
                // Handle the case where the course is paid

                // Implement your payment logic here (e.g., redirect to payment page)
                // After successful payment, update user type and allow enrollment
                // ...

                // For simplicity, we'll throw an exception as a placeholder
                throw new IllegalArgumentException("Payment required for course enrollment");
            } else {
                // Handle any other cases
                throw new IllegalArgumentException("User not allowed to enroll in this course");
            }
        } catch (Exception e) {

            // Rethrow the exception or handle it based on your requirements
            throw new RuntimeException("Error while enrolling user", e);
        }

    }

}
