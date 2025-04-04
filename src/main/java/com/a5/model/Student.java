package com.a5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Entity class representing a student in the system.
 * Students can be enrolled in multiple courses.
 */
@Entity
@Table(name = "students")
public class Student {
    /**
     * Primary key for the student entity.
     * Auto-generated identity column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    /**
     * Student's full name.
     * Required field that cannot be blank.
     */
    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    /**
     * Student's email address.
     * Required field that must follow valid email format and must be unique.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Collection of courses in which the student is enrolled.
     * Uses a many-to-many relationship with the Course entity through the enrollments join table.
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "enrollments",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

    /**
     * Default constructor.
     * Initializes courses collection as an empty HashSet.
     */
    public Student() {
        this.courses = new HashSet<>();
    }

    /**
     * Gets the student's ID.
     * 
     * @return The student's ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the student's ID.
     * 
     * @param id The ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the student's name.
     * 
     * @return The student's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the student's name.
     * 
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the student's email.
     * 
     * @return The student's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the student's email.
     * 
     * @param email The email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the set of courses in which the student is enrolled.
     * 
     * @return A set of Course objects
     */
    public Set<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the courses in which the student is enrolled.
     * 
     * @param courses A set of Course objects
     */
    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    /**
     * Checks if this student is equal to another object.
     * Equality is based on ID, name, and email.
     * 
     * @param o The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) &&
               Objects.equals(name, student.name) &&
               Objects.equals(email, student.email);
    }

    /**
     * Generates a hash code for this student.
     * Based on ID, name, and email.
     * 
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

    /**
     * Returns a string representation of this student.
     * Includes ID, name, and email.
     * 
     * @return A string representation of the student
     */
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
} 