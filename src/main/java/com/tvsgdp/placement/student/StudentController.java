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
    private ResponseEntity<Object> updateStudentByHallTicket(@RequestBody StudentRequest studentRequest, @PathVariable Long HallTicketNo){
        try{
            StudentResponse response = studentService.updateStudentByHallTicket(studentRequest, HallTicketNo);
            return ResponseHandler.generateResponse("Success",HttpStatus.CREATED,response);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,null);
        }
    }

    @GetMapping("allStudents")
    private ResponseEntity<Object> getAllStudents(){
        try{
            List<StudentResponse> response = studentService.getAllStudents();
            return ResponseHandler.generateResponse("Success",HttpStatus.OK,response);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,null);
        }
    }

    @GetMapping("id/{id}")
    private ResponseEntity<Object> getStudentById(@PathVariable Long id){
        try{
            StudentResponse response = studentService.getStudentById(id);
            return ResponseHandler.generateResponse("Success",HttpStatus.OK,response);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,null);
        }
    }
}
