package com.tvsgdp.placement.college;

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
    public ResponseEntity<CollegeResponse> getCollegeById(@PathVariable Long id) {
        Optional<CollegeResponse> response = collegeService.getCollegeById(id);
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(response);
    }

    //get college by college adminId
    @GetMapping("/college-admin/{collegeAdminId}")
    public ResponseEntity<CollegeResponse> getCollegeByCollegeAdminId(@PathVariable Long collegeAdminId) {
        Optional<CollegeResponse> response = collegeService.getCollegeByCollegeAdminId(collegeAdminId);
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(response);
    }

    //get college by location
    @GetMapping("/location/{location}")
    @PreAuthorize("hasRole('ROLE_CORPORATE')")
    public ResponseEntity<List<CollegeResponse>> getAllCollegeLocation(@PathVariable String location) {
        List<CollegeResponse> response;
        try {
            response = collegeService.getAllCollegeLocation(location);
        } catch (NoCollegeFoundWithLocationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(response);
    }

    //get all college
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_CORPORATE')")
    public ResponseEntity<List<CollegeResponse>> getAllCollege() {
        List<CollegeResponse> response = collegeService.getAllCollege();
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(response);
    }

    // create a new college - [Note one uer can create only one college]
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_UNIVERSITY')")
    public ResponseEntity<CollegeResponse> createCollege(@RequestBody CollegeRequest collegeRequest) {

        CollegeResponse response = null;
        try {
            response = collegeService.createCollege(collegeRequest);
        } catch (UserAlreadyHasACollegeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        System.out.println(response.toString());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //update a college
    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_UNIVERSITY')")
    public ResponseEntity<CollegeResponse> updateCollege(@RequestBody CollegeRequest collegeRequest) {
        CollegeResponse response = null;
        try {
            response = collegeService.updateCollege(collegeRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        System.out.println(response.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
