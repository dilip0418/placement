package com.tvsgdp.placement.certificate;

import com.tvsgdp.placement.college.College;
import com.tvsgdp.placement.college.CollegeRepository;
import com.tvsgdp.placement.student.Student;
import com.tvsgdp.placement.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CertificateService {

    @Autowired
    private final CertificateRepository certificateRepository;

    @Autowired
    private final CollegeRepository collegeRepository;

    @Autowired
    private final StudentRepository studentRepository;

    //adding certificates
    public String addCertificate(CertificateRequest certificateRequest) {
        Optional<College> collegeOptional = collegeRepository.findById(certificateRequest.getCollege_id());
        Optional<Student> studentOptional = studentRepository.findById(certificateRequest.getStudent_id());
        if(collegeOptional.isPresent()){
            College college = collegeOptional.get();
            Student student = studentOptional.get();
            Certificate certificate =Certificate.builder()
                    .yop(certificateRequest.getYop())
                    .college(college)
                    .student(student)
                    .build();

            certificateRepository.save(certificate);
            return "Success";
        }
        return "Failure";

    }

    //Getting all certificates by using college id for CORPORATE
    public List<CertificateResponse> getCertificateByCollegeId(@PathVariable Long id) throws Exception {

        //get all certificates
        List<Certificate> certificates = certificateRepository.findByCollegeId(id);

        //check if certificates exist with this College_id and throw an exception if no certificates is found with this College_id
        if(certificates.isEmpty()){
            throw new Exception("Could not find certificates");
        }
        return certificates.stream()
                .map(certificate ->
                        CertificateResponse.builder()
                                .hallTicketNo(certificate.getStudent().getHallTicketNo())
                                .name(certificate.getStudent().getName())
                                .qualification(certificate.getStudent().getQualification())
                                .course(certificate.getStudent().getCourse())
                                .yop(certificate.getYop())
                                .collegeName(certificate.getCollege().getCollegeName())
                                .collegeLocation(certificate.getCollege().getLocation())
                                .build()).toList();
    }

    //Getting all certificates by using Student id for UNIVERSITY
    public Optional<CertificateResponse> getCertificateByStudentId(Long id) throws Exception {
        Optional<Certificate> certificates = certificateRepository.findByStudentId(id);

        if (certificates.isEmpty()){
            throw new Exception("Could not find Certificate");
        }
        return certificates
                .map(certificate ->
                        CertificateResponse.builder()
                                .hallTicketNo(certificate.getStudent().getHallTicketNo())
                                .name(certificate.getStudent().getName())
                                .qualification(certificate.getStudent().getQualification())
                                .course(certificate.getStudent().getCourse())
                                .yop(certificate.getYop())
                                .collegeName(certificate.getCollege().getCollegeName())
                                .collegeLocation(certificate.getCollege().getLocation())
                                .build());
    }

    /* Getting all certificates for college admin
    public List<CertificateResponse> allCertificate() {
        
        List<Certificate> certificates = certificateRepository.findAll();

        return certificates.stream()
                .map(certificate ->
                        CertificateResponse.builder()
                                .hallTicketNo(certificate.getStudent().getHallTicketNo())
                                .name(certificate.getStudent().getName())
                                .qualification(certificate.getStudent().getQualification())
                                .course(certificate.getStudent().getCourse())
                                .yop(certificate.getYop())
                                .collegeName(certificate.getCollege().getCollegeName())
                                .collegeLocation(certificate.getCollege().getLocation())
                                .build()).toList();

    }
 */
}
