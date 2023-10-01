package com.tvsgdp.placement.certificate;

import com.tvsgdp.placement.college.College;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CertificateService {


    private final CertificateRepository certificateRepository;

    //Creating certificates
    public Certificate createCertificate(Optional<College> collegeOptional) {
        Certificate certificate =new Certificate();
        Random rand = new Random();
        Long randomCode = rand.nextLong((10000000 - 1000000)+1L) + 1000000;
        certificate.setCode(randomCode);
        certificate.setIssueDate(LocalDate.now());
        certificate.setCollege(collegeOptional.get());
        return certificate;
    }
}
