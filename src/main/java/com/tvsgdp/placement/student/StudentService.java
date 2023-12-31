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

        College college = collegeRepository.findById(studentRequest.getCollegeId()).orElseThrow(()->new Exception("College not found"));
        Certificate certificate = certificateService.createCertificate(college);

        Student student = StudentRequest.buildStudentRequest(studentRequest, college,certificate);
        studentRepository.save(student);
        return StudentResponse.buildStudentResponse(student);

    }

    public StudentResponse updateStudentByHallTicket(StudentRequest studentRequest, Long HallTicketNo) throws Exception{

//        System.out.println(studentRequest.getCollegeId());
        Student existingStudent = studentRepository.findByHallTicketNo(HallTicketNo).orElseThrow(()->new Exception("College not found"));
        College college = collegeRepository.findById(studentRequest.getCollegeId()).orElseThrow(()->new Exception("College not found"));
        Certificate certificate = certificateService.createCertificate(college);

        // Update the existing student entity with the new values
        existingStudent.setCourse(studentRequest.getCourse());
        existingStudent.setName(studentRequest.getName());
        existingStudent.setHallTicketNo(studentRequest.getHallTicketNo());
        existingStudent.setQualification(studentRequest.getQualification());
        existingStudent.setYop(studentRequest.getYop());
        existingStudent.setCollege(college);
        existingStudent.setCertificate(certificate);


        studentRepository.save(existingStudent);
//        System.out.println("Student updated");
        return StudentResponse.buildStudentResponse(existingStudent);

    }

    public List<StudentResponse> getAllStudents() throws Exception{
        List<Student> students = studentRepository.findAll();
        if(students.isEmpty()){
            throw new Exception("Students Not Found");
        }

        return students.stream()
                .map(StudentResponse::buildStudentResponse)
                .toList();
    }

    public StudentResponse getStudentById(Long id) throws Exception{
        Student student = studentRepository.findById(id).orElseThrow(()->new Exception("Student not found"));


        return StudentResponse.buildStudentResponse(student);
    }

    public StudentResponse getStudentByHallTicketNo(Long hallTicketNo) throws Exception{
        Optional<Student> student = studentRepository.findByHallTicketNo(hallTicketNo);

        if(student.isEmpty()){
            throw new Exception("Student Not Found with Current HallTicketNo");
        }
        return StudentResponse.buildStudentResponse(student.get());
    }

    public List<StudentResponse> getStudentByCourse(String course) throws Exception {
        List<Student> students = studentRepository.findStudentByCourse(course);

        if(students.isEmpty()){
            throw new Exception("Students Not Found with current course");
        }

        return students.stream()
                .map(StudentResponse::buildStudentResponse)
                .toList();
    }

    public List<StudentResponse> getStudentByQualification(String qualification) throws Exception{
        List<Student> students = studentRepository.findStudentByQualification(qualification);

        if(students.isEmpty()){
            throw new Exception("Students Not Found with current qualification");
        }

        return students.stream()
                .map(StudentResponse::buildStudentResponse)
                .toList();
    }

    public List<StudentResponse> getStudentByYop(Long yop) throws Exception{
        List<Student> students = studentRepository.findStudentByYop(yop);

        if(students.isEmpty()){
            throw new Exception("Students Not Found with current passOut year");
        }

        return students.stream()
                .map(StudentResponse::buildStudentResponse)
                .toList();
    }

    public List<StudentResponse> getStudentByCollegeName(String collegeName) throws Exception{

        Optional<College> collegeOptional = collegeRepository.findByCollegeName(collegeName);
        if(collegeOptional.isEmpty()){
            throw new Exception("Student not found with current college");
        }

        College college = collegeOptional.get();
        List<Student> students = studentRepository.findStudentByCollegeId(college.getId());
        return students.stream()
                .map(StudentResponse::buildStudentResponse)
                .toList();
    }

    public List<StudentResponse> getStudentByName(String name) throws Exception{
        List<Student> students = studentRepository.findStudentByName(name);
        if(students.isEmpty()){
            throw new Exception("Student not found");
        }
        return students.stream()
                .map(StudentResponse::buildStudentResponse)
                .toList();
    }

    public String deleteStudentByHallTicketNo(Long hallTicketNo) throws Exception{
        Optional<Student> studentOptional = studentRepository.findByHallTicketNo(hallTicketNo);

        if(studentOptional.isEmpty()) {
            throw new Exception("Student Not Found");
        }

        Student student = studentOptional.get();
        studentRepository.delete(student);
        return student.getName()+" deleted Successfully";
    }

    public List<StudentResponse> getStudentByCollegeId(Long collegeId) throws Exception{

        List<Student> students = studentRepository.findStudentByCollegeId(collegeId);
        if(students.isEmpty()){
            throw new Exception("Students not found");
        }
        return students.stream()
                .map(StudentResponse::buildStudentResponse)
                .toList();
    }
}
