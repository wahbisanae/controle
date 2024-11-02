package com.school.school.controller;

import com.school.school.model.Student;
import com.school.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public String getStudents(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("newStudent", new Student());
        return "students";
    }

    @PostMapping("/students")
    public String addStudent(@ModelAttribute("newStudent") Student student) {
        studentRepository.save(student);
        return "redirect:/";
    }

    // Load edit page with the student's data
    @GetMapping("/students/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            return "update-student";
        } else {
            model.addAttribute("error", "Student not found with ID: " + id);
            return "redirect:/";
        }
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student updatedStudent,
                                @RequestParam(value = "_method", required = false) String method) {
        if ("put".equalsIgnoreCase(method)) {
            Optional<Student> existingStudent = studentRepository.findById(id);
            if (existingStudent.isPresent()) {
                Student student = existingStudent.get();
                student.setFirstName(updatedStudent.getFirstName());
                student.setLastName(updatedStudent.getLastName());
                student.setEmail(updatedStudent.getEmail());
                studentRepository.save(student);
            }
        } else {
            // This is for the delete operation.
            studentRepository.deleteById(id);
        }
        return "redirect:/";
    }

}
