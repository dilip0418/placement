package com.tvsgdp.placement.student;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student")
public class StudentController {
    @Autowired
    private final StudentService studentService;

    @PostMapping("add")
    private ResponseEntity<String> addStudent(@RequestBody StudentRequest studentRequest){
        return new ResponseEntity<>(studentService.addStudent(studentRequest), HttpStatus.OK);
    }

    @PutMapping("update/{HallTicketNo}")
    private ResponseEntity<String> updateStudentByHallTicket(@RequestBody StudentRequest studentRequest, @PathVariable Long HallTicketNo){
        return new ResponseEntity<>(studentService.updateStudentByHallTicket(studentRequest,HallTicketNo), HttpStatus.OK);
    }

    @GetMapping("allStudents")
    private ResponseEntity<List<Student>> getAllStudents(){
        return new ResponseEntity<>(studentService.getAllStudents(),HttpStatus.OK);
    }
}
