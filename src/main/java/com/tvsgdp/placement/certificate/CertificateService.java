package com.tvsgdp.placement.certificate;

import com.tvsgdp.placement.college.College;
import com.tvsgdp.placement.college.CollegeRepository;
import com.tvsgdp.placement.student.Student;
import com.tvsgdp.placement.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            return "success";
        }
        return "failure";

    }

    //Getting all certificates by using college id for CORPORATE
    public List<CertificateResponse> getCertificateByCollegeId(Long id) {
        List<Certificate> certificates = certificateRepository.findByCollegeId(id);

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
    public List<CertificateResponse> getCertificateByStudentId(Long id) {
        List<Certificate> certificates = certificateRepository.findByStudentId(id);

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
