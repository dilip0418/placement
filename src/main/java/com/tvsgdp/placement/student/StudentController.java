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

    @GetMapping("allStudents")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getAllStudents() {
        try {
            List<StudentResponse> response = studentService.getAllStudents();
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Object> getStudentById(@PathVariable Long id) {
        try {
            StudentResponse response = studentService.getStudentById(id);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("collegeId/{collegeId}")
    public ResponseEntity<Object> getStudentByCollegeId(@PathVariable Long collegeId) {
        try {
            List<StudentResponse> response = studentService.getStudentByCollegeId(collegeId);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("hallTicketNo/{hallTicketNo}")
    public ResponseEntity<Object> getStudentByHallTicketNo(@PathVariable Long hallTicketNo) {
        try {
            StudentResponse response = studentService.getStudentByHallTicketNo(hallTicketNo);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }


    
    @GetMapping("course/{course}")
    public ResponseEntity<Object> getStudentByCourse(@PathVariable String course) {
        try {
            List<StudentResponse> response = studentService.getStudentByCourse(course);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("qualification/{qualification}")
    public ResponseEntity<Object> getStudentByQualification(@PathVariable String qualification) {
        try {
            List<StudentResponse> response = studentService.getStudentByQualification(qualification);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("yop/{yop}")
    public ResponseEntity<Object> getStudentByYop(@PathVariable Long yop) {
        try {
            List<StudentResponse> response = studentService.getStudentByYop(yop);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("collegeName/{collegeName}")
    public ResponseEntity<Object> getStudentByCollegeName(@PathVariable String collegeName) {
        try {
            List<StudentResponse> response = studentService.getStudentByCollegeName(collegeName);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("name/{name}")
    public ResponseEntity<Object> getStudentByName(@PathVariable String name) {
        try {
            List<StudentResponse> response = studentService.getStudentByName(name);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }


    /*
    AuthorizedRequest access required for using these methods
     */

    /*
    Here these methods below has an @PreAuthorize annotation having
    the authorization check allowing only the users with role ROLE_CORPORATE
     */
    @PostMapping("add")
    @PreAuthorize("hasRole('ROLE_UNIVERSITY')")
    public ResponseEntity<Object> addStudent(@RequestBody StudentRequest studentRequest) {
        try {
            StudentResponse response = studentService.addStudent(studentRequest);
            return ResponseHandler.generateResponse("Student Added", HttpStatus.CREATED, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping("update/{HallTicketNo}")
    @PreAuthorize("hasRole('ROLE_UNIVERSITY')")
    public ResponseEntity<Object> updateStudentByHallTicket(@RequestBody StudentRequest studentRequest, @PathVariable Long HallTicketNo) {
        try {
            StudentResponse response = studentService.updateStudentByHallTicket(studentRequest, HallTicketNo);
            return ResponseHandler.generateResponse("Student Updated", HttpStatus.CREATED, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping("delete/{hallTicketNo}")
    @PreAuthorize("hasRole('ROLE_UNIVERSITY')")
    public ResponseEntity<Object> deleteStudentByHallTicketNo(@PathVariable Long hallTicketNo) {
        try {
            String response = studentService.deleteStudentByHallTicketNo(hallTicketNo);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
