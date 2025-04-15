package com.a5.service;

import com.a5.model.Course;
import com.a5.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAllWithStudents();
    }

    @Transactional(readOnly = true)
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Transactional
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public boolean existsByName(String name) {
        return courseRepository.existsByName(name);
    }

    @Transactional(readOnly = true)
    public Page<Course> getCoursesPage(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return courseRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Course> searchCoursesByName(String keyword) {
        return courseRepository.findByNameContainingIgnoreCase(keyword);
    }
}
