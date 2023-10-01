package com.tvsgdp.placement.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<CertificateResponse>> getCertificateByCollegeId(@PathVariable Long id){
        return new ResponseEntity<>(certificateService.getCertificateByCollegeId(id), HttpStatus.OK);
    }
    //retrieving certificates by using student id
    @GetMapping("/ByStudentId/{id}")
    public ResponseEntity<List<CertificateResponse>> getCertificateByStudentId(@PathVariable Long id){
        return new ResponseEntity<>(certificateService.getCertificateByStudentId(id), HttpStatus.OK);
    }

}
