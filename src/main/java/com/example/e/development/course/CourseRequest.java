package com.example.e.development.course;

import lombok.Data;

@Data

public class CourseRequest {
    private String courseTitle;
    private double price;

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
