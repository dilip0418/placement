package com.tvsgdp.placement.college;

import com.tvsgdp.placement.config.ResponseHandler;
import com.tvsgdp.placement.exception.NoCollegeFoundWithLocationException;
import com.tvsgdp.placement.exception.UserAlreadyHasACollegeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/college")
public class CollegeController {

    private final CollegeService collegeService;

    //get college by collegeId
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CORPORATE')")
    public ResponseEntity<Object> getCollegeById(@PathVariable Long id) {
        Optional<CollegeResponse> response = collegeService.getCollegeById(id);
        if (response.isEmpty()) {
            return ResponseHandler.generateResponse("College not found",HttpStatus.NOT_FOUND,null);
        }
        return ResponseHandler.generateResponse("Success",HttpStatus.OK,response);
    }

    //get college by college adminId
    @GetMapping("/college-admin/{collegeAdminId}")
    public ResponseEntity<Object> getCollegeByCollegeAdminId(@PathVariable Long collegeAdminId) {
        try {
        Optional<CollegeResponse> response = collegeService.getCollegeByCollegeAdminId(collegeAdminId);
            return ResponseHandler.generateResponse("Success",HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,null);
        }
    }

    //get college by location
    @GetMapping("/location/{location}")
    @PreAuthorize("hasRole('ROLE_CORPORATE')")
    public ResponseEntity<Object> getAllCollegeByLocation(@PathVariable String location) {

        try {
            List<CollegeResponse> response = collegeService.getAllCollegeLocation(location);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (NoCollegeFoundWithLocationException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    //get all college
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_CORPORATE')")
    public ResponseEntity<Object> getAllCollege() {
        try {
            List<CollegeResponse> response = collegeService.getAllCollege();
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    // create a new college - [Note one uer can create only one college]
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_UNIVERSITY')")
    public ResponseEntity<Object> createCollege(@RequestBody CollegeRequest collegeRequest) {
        try {
            CollegeResponse response = collegeService.createCollege(collegeRequest);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (UserAlreadyHasACollegeException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    //update a college
    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_UNIVERSITY')")
    public ResponseEntity<Object> updateCollege(@RequestBody CollegeRequest collegeRequest) {

        try {
            CollegeResponse response = collegeService.updateCollege(collegeRequest);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
}
