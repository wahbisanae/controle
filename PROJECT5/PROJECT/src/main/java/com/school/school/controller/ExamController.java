package com.school.school.controller;

import com.school.school.model.Exam;
import com.school.school.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/exams")
public class ExamController {

    @Autowired
    private ExamRepository examRepository;

    @GetMapping
    public String getAllExams(Model model) {
        model.addAttribute("exams", examRepository.findAll());
        model.addAttribute("newExam", new Exam());
        return "exams";
    }

    @PostMapping
    public String addExam(@ModelAttribute("newExam") Exam exam) {
        examRepository.save(exam);
        return "redirect:/exams";
    }

    @PostMapping("/{id}")
    public String deleteOrUpdateExam(@PathVariable Long id,
                                     @ModelAttribute("exam") Exam updatedExam,
                                     @RequestParam(value = "_method", required = false) String method) {
        if ("put".equalsIgnoreCase(method)) {  // Check if the request is for updating
            Optional<Exam> existingExam = examRepository.findById(id);
            if (existingExam.isPresent()) {
                Exam exam = existingExam.get();
                exam.setName(updatedExam.getName());
                exam.setDate(updatedExam.getDate());
                exam.setSubject(updatedExam.getSubject());
                examRepository.save(exam);
            }
        } else {  // If `_method` is not "put", delete the exam
            examRepository.deleteById(id);
        }
        return "redirect:/exams";
    }

    @GetMapping("/edit/{id}")
    public String editExam(@PathVariable Long id, Model model) {
        Optional<Exam> exam = examRepository.findById(id);
        if (exam.isPresent()) {
            model.addAttribute("exam", exam.get());
            return "update-exam"; // Thymeleaf template for updating an exam
        } else {
            model.addAttribute("error", "Exam not found with ID: " + id);
            return "redirect:/exams";
        }
    }
}
