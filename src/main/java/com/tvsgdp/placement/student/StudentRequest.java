package com.tvsgdp.placement.student;

import com.tvsgdp.placement.certificate.Certificate;
import com.tvsgdp.placement.college.College;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {

    private String name;
    private Long collegeId;
    private String qualification;
    private String course;
    private Long yop;
    private Long hallTicketNo;

    public static Student buildStudentRequest(StudentRequest studentRequest, College college, Certificate certificate){
        return Student.builder()
                .course(studentRequest.getCourse())
                .name(studentRequest.getName())
                .hallTicketNo(studentRequest.getHallTicketNo())
                .qualification(studentRequest.getQualification())
                .yop(studentRequest.getYop())
                .college(college)
                .certificate(certificate)
                .build();

    }
}
