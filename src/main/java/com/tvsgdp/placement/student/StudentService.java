package com.tvsgdp.placement.student;


import com.tvsgdp.placement.college.College;
import com.tvsgdp.placement.college.CollegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StudentService {
    @Autowired
    private final StudentRepository studentRepository;
    @Autowired
    private final CollegeRepository collegeRepository;
    public String addStudent(StudentRequest studentRequest) {
        Optional<College> collegeOptional = collegeRepository.findById(studentRequest.getCollegeId());

        if(collegeOptional.isPresent()){
            College college = collegeOptional.get();

            Student student = Student.builder()
                    .course(studentRequest.getCourse())
                    .name(studentRequest.getName())
                    .hallTicketNo(studentRequest.getHallTicketNo())
                    .qualification(studentRequest.getQualification())
                    .yop(studentRequest.getYop())
                    .college(college).build();

            studentRepository.save(student);
            return "success";
        }
        return "failure";
    }

    public String updateStudentByHallTicket(StudentRequest studentRequest, Long HallTicketNo) {
        Optional<Student> studentOptional = studentRepository.findByHallTicketNo(HallTicketNo);
        Optional<College> collegeOptional = collegeRepository.findById(studentRequest.getCollegeId());

        if(collegeOptional.isPresent() && studentOptional.isPresent()){
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
            return "success";
        }
        return "failure";

    }

    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepository.findAll();

        return students.stream()
                .map(student ->
                        StudentResponse.builder()
                                .id(student.getId())
                                .hallTicketNo(student.getHallTicketNo())
                                .name(student.getName())
                                .qualification(student.getQualification())
                                .course(student.getCourse())
                                .yop(student.getYop())
                                .collegeName(student.getCollege().getCollegeName())
                                .collegeLocation(student.getCollege().getLocation())
                                .build()).toList();
    }
}
