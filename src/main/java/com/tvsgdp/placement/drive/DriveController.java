package com.tvsgdp.placement.drive;

import com.tvsgdp.placement.config.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/drive")
public class DriveController {


    private final DriveService driveService;


    @GetMapping("/allDrives")
    public ResponseEntity<Object> getAllDrives() {
        try {
            List<DriveResponse> responses = driveService.getAllDrives();
            return ResponseHandler.generateResponse("success", HttpStatus.OK, responses);

        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/allDrivesByUserId/{userId}")
    @PreAuthorize("hasRole('ROLE_CORPORATE')")
    public ResponseEntity<Object> getAllDrivesByUserId(@PathVariable Long userId) {
        try {
            List<DriveResponse> responses = driveService.getAllDrivesByUserId(userId);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, responses);

        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getDrivesById(@PathVariable Long id) {
        try {
            DriveResponse response = driveService.getDriveById(id);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("/qualification/{qualification}")
    public ResponseEntity<Object> getDrivesByQualification(@PathVariable String qualification) {
        try {
            List<DriveResponse> response = driveService.getDrivesByQualification(qualification);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("/yop/{yop}")
    public ResponseEntity<Object> getDrivesByYearOfPassing(@PathVariable Integer yop) {
        try {
            List<DriveResponse> response = driveService.getDrivesByYear(yop);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping("/create/add")
    @PreAuthorize("hasRole('ROLE_CORPORATE')")
    public ResponseEntity<Object> createDrive(@RequestBody DriveRequest driveRequest) {
        try {
            DriveResponse driveResponse = driveService.addDrive(driveRequest);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, driveResponse);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CORPORATE')")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        try {
            driveService.deleteById(id);
            return ResponseHandler.generateResponse("Drive deleted successfully", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }


    @GetMapping("/yop")
    @PreAuthorize("hasRole('ROLE_CORPORATE')")
    public ResponseEntity<Object> getDrivesByYearOfPassingWithUserId(@RequestParam Integer yop, @RequestParam Long userId) {
        try {
            List<DriveResponse> response = driveService.getDrivesByYearWithUserId(yop, userId);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }


    @GetMapping("/qualification")
    @PreAuthorize("hasRole('ROLE_CORPORATE')")
    public ResponseEntity<Object> getDrivesByQualificationWithUserId(@RequestParam String qualification, @RequestParam Long userId) {
        try {
            List<DriveResponse> response = driveService.getDrivesByQualificationWithUserId(qualification, userId);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("/collegeId/{collegeId}")
    @PreAuthorize("hasRole('ROLE_CORPORATE')")
    public ResponseEntity<Object> getDrivesByCollegeId(@PathVariable Long collegeId) {
        try {
            List<DriveResponse> response = driveService.getDrivesByCollegeId(collegeId);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
