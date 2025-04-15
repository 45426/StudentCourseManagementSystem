package com.a5.controller;

import com.a5.model.Course;
import com.a5.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public String listCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            Model model) {

        var coursesPage = courseService.getCoursesPage(page, size, sortBy);
        model.addAttribute("coursesPage", coursesPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursesPage.getTotalPages());

        return "courses/list";
    }

    // ✅ NEW: Search functionality
    @GetMapping("/search")
    public String searchCourses(@RequestParam("keyword") String keyword, Model model) {
        List<Course> results = courseService.searchCoursesByName(keyword);
        model.addAttribute("coursesPage", new PageImpl<>(results));
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        return "courses/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        return "courses/form";
    }

    @PostMapping
    public String createCourse(@Valid @ModelAttribute("course") Course course, BindingResult result) {
        if (result.hasErrors()) {
            return "courses/form";
        }
        if (courseService.existsByName(course.getName())) {
            result.rejectValue("name", "error.course", "Course name already exists");
            return "courses/form";
        }
        courseService.saveCourse(course);
        return "redirect:/courses";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));
        model.addAttribute("course", course);
        return "courses/form";
    }

    @PostMapping("/{id}")
    public String updateCourse(@PathVariable Long id, @Valid @ModelAttribute("course") Course course,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "courses/form";
        }
        course.setId(id);
        courseService.saveCourse(course);
        return "redirect:/courses";
    }

    @PostMapping("/{id}/delete")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }
}
