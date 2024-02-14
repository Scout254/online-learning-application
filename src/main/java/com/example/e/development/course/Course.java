package com.example.e.development.course;

import com.example.e.development.enrollment.Enrollment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Entity
@Table(name = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseTitle; //title of the course
    private double price;//price for paid subscription
    private String courseDescription;
    private String courseCategory;
    private String courseReviews;
    private int courseDuration;
    private String userProgress; // Values: "Not Started", "In Progress", "Completed"
    private int completionPercentage; // Value range: 0 to 100
    @Enumerated(EnumType.STRING)
    private CourseType courseType;


    private int enrollmentCount;

    //one-to-many relationship with the modules
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Module> modules = new ArrayList<>();


    public List<Module> getModules() {
        return modules;
    }
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz> quizzes = new ArrayList<>();

    public List<Quiz> getQuizzes(){
        return quizzes;
    }
    // One-to-many relationship with enrollment
    //store enrollments associated with this course
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + courseTitle + '\'' +
                ", price=" + price +
                ", courseType=" + courseType +
                ", enrollmentCount=" + enrollmentCount +
                '}';
    }
    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public int getEnrollmentCount() {
        return enrollmentCount;
    }

    public void setEnrollmentCount(int enrollmentCount) {
        this.enrollmentCount = enrollmentCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


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
    // Update progress based on modules and quizzes completion
    public void updateProgress() {
        int totalModulesAndQuizzes = modules.size() + quizzes.size();
        int completedModulesAndQuizzes = (int) Stream.concat(modules.stream(), quizzes.stream())
                .filter(moduleOrQuiz -> isModuleOrQuizCompleted(moduleOrQuiz))
                .count();

        if (completedModulesAndQuizzes == totalModulesAndQuizzes) {
            userProgress = "Completed";
        } else if (completedModulesAndQuizzes > 0) {
            userProgress = "In Progress";
        } else {
            userProgress = "Not Started";
        }

        // Calculate completion percentage
        completionPercentage = (int) ((completedModulesAndQuizzes * 100.0) / totalModulesAndQuizzes);
    }

    private boolean isModuleOrQuizCompleted(Object moduleOrQuiz) {
        if (moduleOrQuiz instanceof Module) {
            return ((Module) moduleOrQuiz).isCompleted();
        } else if (moduleOrQuiz instanceof Quiz) {
            return ((Quiz) moduleOrQuiz).isCompleted();
        } else {
            // Handle other cases if necessary
            return false;
        }
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(String courseCategory) {
        this.courseCategory = courseCategory;
    }

    public String getCourseReviews() {
        return courseReviews;
    }

    public void setCourseReviews(String courseReviews) {
        this.courseReviews = courseReviews;
    }

    public int getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(int courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getUserProgress() {
        return userProgress;
    }

    public void setUserProgress(String userProgress) {
        this.userProgress = userProgress;
    }

    public int getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = completionPercentage;
    }
}
