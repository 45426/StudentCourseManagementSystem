package com.a5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Entity class representing a course in the system.
 * Courses can have multiple students enrolled through a many-to-many relationship.
 */
@Entity
@Table(name = "courses")
public class Course {
    /**
     * Primary key for the course entity.
     * Auto-generated identity column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    /**
     * Course name.
     * Required field that cannot be blank and must be unique.
     */
    @NotBlank(message = "Course name is required")
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Course description.
     * Optional field providing additional information about the course.
     */
    private String description;

    /**
     * Collection of students enrolled in this course.
     * Uses a many-to-many relationship with the Student entity, mapped by the courses field in Student.
     */
    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();

    /**
     * Default constructor.
     * Initializes students collection as an empty HashSet.
     */
    public Course() {
        this.students = new HashSet<>();
    }

    /**
     * Gets the course's ID.
     * 
     * @return The course's ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the course's ID.
     * 
     * @param id The ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the course's name.
     * 
     * @return The course's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the course's name.
     * 
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the course's description.
     * 
     * @return The course's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the course's description.
     * 
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the set of students enrolled in this course.
     * 
     * @return A set of Student objects
     */
    public Set<Student> getStudents() {
        return students;
    }

    /**
     * Sets the students enrolled in this course.
     * 
     * @param students A set of Student objects
     */
    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    /**
     * Checks if this course is equal to another object.
     * Equality is based on ID, name, and description.
     * 
     * @param o The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) &&
               Objects.equals(name, course.name) &&
               Objects.equals(description, course.description);
    }

    /**
     * Generates a hash code for this course.
     * Based on ID, name, and description.
     * 
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    /**
     * Returns a string representation of this course.
     * Includes ID, name, and description.
     * 
     * @return A string representation of the course
     */
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
} 