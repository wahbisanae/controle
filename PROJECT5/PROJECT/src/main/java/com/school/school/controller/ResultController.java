package com.school.school.controller;

import com.school.school.DTO.ResultDTO;
import com.school.school.model.Result;
import com.school.school.model.Student;
import com.school.school.model.Exam;
import com.school.school.repository.ResultRepository;
import com.school.school.repository.StudentRepository;
import com.school.school.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/results")
public class ResultController {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private StudentRepository studentRepository;  // Add StudentRepository

    @Autowired
    private ExamRepository examRepository;        // Add ExamRepository

    @GetMapping
    public String getAllResults(Model model) {
        List<ResultDTO> results = resultRepository.findAll().stream().map(result ->
                new ResultDTO(
                        result.getId(),
                        result.getStudent().getFirstName(),
                        result.getStudent().getLastName(),
                        result.getExam().getSubject(),
                        result.getMark()
                )
        ).collect(Collectors.toList());

        model.addAttribute("results", results);
        model.addAttribute("newResult", new Result()); // Create a new Result object

        // Add lists of students and exams to the model
        List<Student> students = studentRepository.findAll();
        List<Exam> exams = examRepository.findAll();
        model.addAttribute("students", students);
        model.addAttribute("exams", exams);

        return "results"; // Returns "results.html" Thymeleaf template
    }


    @PostMapping
    public String addResult(@ModelAttribute("newResult") Result result) {
        resultRepository.save(result);
        return "redirect:/results"; // Redirect to refresh the results list
    }

    @PostMapping("/{id}")
    public String deleteResult(@PathVariable Long id) {
        resultRepository.deleteById(id);
        return "redirect:/results"; // Redirect to refresh the results list after deletion
    }

    @GetMapping("/edit/{id}")
    public String showEditResultForm(@PathVariable Long id, Model model) {
        Result result = resultRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid result ID:" + id));

        // Set up the edit model
        model.addAttribute("result", result);
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("exams", examRepository.findAll());
        return "update-result";  // A new Thymeleaf template for editing
    }

    @PostMapping("/update/{id}")
    public String updateResult(@PathVariable Long id, @ModelAttribute("result") Result updatedResult) {
        Result result = resultRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid result ID:" + id));

        result.setStudent(updatedResult.getStudent());
        result.setExam(updatedResult.getExam());
        result.setMark(updatedResult.getMark());

        resultRepository.save(result);
        return "redirect:/results"; // Redirect back to the results list
    }
}
