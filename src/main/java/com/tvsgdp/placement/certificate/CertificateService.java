package com.tvsgdp.placement.certificate;

import com.tvsgdp.placement.college.College;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
public class CertificateService {

    //Creating certificates
    public Certificate createCertificate(College college) {
        Certificate certificate =new Certificate();
        Random rand = new Random();
        Long randomCode = rand.nextLong((10000000 - 1000000)+1L) + 1000000; //custom logic for random code generation
        certificate.setCode(randomCode);
        certificate.setIssueDate(LocalDate.now());
        certificate.setCollege(college);
        return certificate;
    }
}
