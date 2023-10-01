package com.tvsgdp.placement.student;

import com.tvsgdp.placement.config.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("add")
    private ResponseEntity<Object> addStudent(@RequestBody StudentRequest studentRequest){
        try{
            StudentResponse response = studentService.addStudent(studentRequest);
            return ResponseHandler.generateResponse("success",HttpStatus.CREATED,response);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,null);
        }
    }

    @PutMapping("update/{HallTicketNo}")
    private ResponseEntity<String> updateStudentByHallTicket(@RequestBody StudentRequest studentRequest, @PathVariable Long HallTicketNo){
        return new ResponseEntity<>(studentService.updateStudentByHallTicket(studentRequest,HallTicketNo), HttpStatus.OK);
    }

    @GetMapping("allStudents")
    private ResponseEntity<List<StudentResponse>> getAllStudents(){
        return new ResponseEntity<>(studentService.getAllStudents(),HttpStatus.OK);
    }
}
