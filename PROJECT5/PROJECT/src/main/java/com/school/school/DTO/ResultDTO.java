package com.school.school.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;


public class ResultDTO {
    private Long id;
    private String studentFirstName;
    private String studentLastName;
    private String subject;
    private double mark;

    public ResultDTO(Long id, String firstName, String lastName, String subject, double mark) {
        this.id = id;
        this.studentFirstName = firstName;
        this.studentLastName = lastName;
        this.subject = subject;
        this.mark = mark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public ResultDTO() {
    }
}

