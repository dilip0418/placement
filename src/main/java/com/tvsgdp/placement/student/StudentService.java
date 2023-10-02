package com.tvsgdp.placement.student;


import com.tvsgdp.placement.certificate.Certificate;
import com.tvsgdp.placement.certificate.CertificateService;
import com.tvsgdp.placement.college.College;
import com.tvsgdp.placement.college.CollegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final CollegeRepository collegeRepository;

    private final CertificateService certificateService;

    public StudentResponse addStudent(StudentRequest studentRequest) throws Exception{

        Optional<College> collegeOptional = collegeRepository.findById(studentRequest.getCollegeId());
        Certificate certificate = certificateService.createCertificate(collegeOptional);

        if(collegeOptional.isEmpty()) {
            throw new Exception("College Not Found");
        }

        College college = collegeOptional.get();
        Student student = studentRequest.buildStudentRequest(studentRequest,college,certificate);
        studentRepository.save(student);
        return StudentResponse.buildStudentResponse(student);

    }

    public StudentResponse updateStudentByHallTicket(StudentRequest studentRequest, Long HallTicketNo) throws Exception{

        Optional<Student> studentOptional = studentRepository.findByHallTicketNo(HallTicketNo);
        Optional<College> collegeOptional = collegeRepository.findById(studentRequest.getCollegeId());

        if(collegeOptional.isEmpty() && studentOptional.isEmpty()) {
            throw new Exception("College or Student Not Found");
        }

        College college = collegeOptional.get();
        Student existingStudent = studentOptional.get();

        // Update the existing student entity with the new values
        existingStudent.setCourse(studentRequest.getCourse());
        existingStudent.setName(studentRequest.getName());
        existingStudent.setHallTicketNo(studentRequest.getHallTicketNo());
        existingStudent.setQualification(studentRequest.getQualification());
        existingStudent.setYop(studentRequest.getYop());
        existingStudent.setCollege(college);

        studentRepository.save(existingStudent);
        return StudentResponse.buildStudentResponse(existingStudent);

    }

    public List<StudentResponse> getAllStudents() throws Exception{
        List<Student> students = studentRepository.findAll();
        if(students.isEmpty()){
            throw new Exception("Students Not Found");
        }

        return students.stream()
                .map(student ->
                        StudentResponse.buildStudentResponse(student))
                .toList();
    }

    public StudentResponse getStudentById(Long id) throws Exception{
        Optional<Student> student = studentRepository.findById(id);

        if(student.isEmpty()){
            throw new Exception("Student Not Found");
        }
        return StudentResponse.buildStudentResponse(student.get());
    }
}
