package com.tvsgdp.placement.certificate;

import com.tvsgdp.placement.config.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/certificate")
public class CertificateController {

    @Autowired
    private final CertificateService certificateService;

    //creating a certificate
    @PostMapping("/add")
    public ResponseEntity<String> addCertificate(@RequestBody CertificateRequest certificateRequest){
        return new ResponseEntity<>(certificateService.addCertificate(certificateRequest), HttpStatus.OK);
    }

    /* getting all certificates for college admin
    @GetMapping("/all")
    public ResponseEntity<List<CertificateResponse>> allCertificate(){
        return new ResponseEntity<>(certificateService.allCertificate(), HttpStatus.OK);
    }
     */
    //retrieving certificates by using college id
    @GetMapping("/ByCollegeId/{id}")
    public ResponseEntity<Object> getCertificateByCollegeId(@PathVariable Long id){
        try{
            List<CertificateResponse> response =certificateService.getCertificateByCollegeId(id);
            return ResponseHandler.generateResponse("Success",HttpStatus.OK,response);
        }catch(Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,null);
        }

    }
    //retrieving certificates by using student id
    @GetMapping("/ByStudentId/{id}")
    public ResponseEntity<Object> getCertificateByStudentId(@PathVariable Long id){
        try{
            Optional<CertificateResponse> response =certificateService.getCertificateByStudentId(id);
            return ResponseHandler.generateResponse("Success",HttpStatus.OK,response);
        }catch(Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,null);
        }
    }

}
