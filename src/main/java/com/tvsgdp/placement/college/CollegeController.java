package com.tvsgdp.placement.college;

import com.tvsgdp.placement.exception.UserAlreadyHasACollegeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/college")
public class CollegeController {

    private final CollegeService collegeService;

    @GetMapping("/{collegeAdminId}")
    public ResponseEntity<?> getCollegeByCollegeAdminId(@PathVariable Long collegeAdminId) {
        Optional<?> response = collegeService.getCollegeByCollegeAdminId(collegeAdminId);
        if (response.isEmpty()) {
            return new ResponseEntity<>("No college found with this admin id", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<?> getAllCollegeLocation(@PathVariable String location) {
        Optional<?> response = collegeService.getAllCollegeLocation(location);
        if (!response.isPresent()) {
            return new ResponseEntity<>("No college found with this location", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllCollege() {
        Optional<?> response = collegeService.getAllCollege();
        if (response.isEmpty()) {
            return new ResponseEntity<>("No college found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCollege(@RequestBody CollegeRequest collegeRequest) {
        // Handle the response of the POST request by using the wild card ? parameter as the return type
        CollegeResponse response = null;
        try {
            response = collegeService.createCollege(collegeRequest);
        } catch (UserAlreadyHasACollegeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        System.out.println(response.toString());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
