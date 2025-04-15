package com.a5.service;

import com.a5.model.Student;
import com.a5.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


import java.util.List;
import java.util.Optional;

/**
 * Service class for managing student data.
 * Provides methods for CRUD operations on student entities.
 */
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    /**
     * Retrieves all students with their associated courses.
     * 
     * @return List of all students with eagerly loaded course data
     */
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAllWithCourses();
    }

    /**
     * Retrieves a specific student by their ID.
     * 
     * @param id The ID of the student to retrieve
     * @return An Optional containing the student if found, or empty if not found
     */
    @Transactional(readOnly = true)
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findByIdWithCourses(id);
    }

    /**
     * Saves a student entity.
     * Can be used for both creating new students and updating existing ones.
     * 
     * @param student The student to save
     * @return The saved student with assigned ID
     */
    @Transactional
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    /**
     * Deletes a student by their ID.
     * 
     * @param id The ID of the student to delete
     */
    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    /**
     * Checks if a student with the specified email already exists.
     * 
     * @param email The email to check
     * @return true if a student with the email exists, false otherwise
     */
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }
    @Transactional(readOnly = true)
    public Page<Student> getStudentsPage(int page, int size, String sortBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
    return studentRepository.findAll(pageable);
}

} 