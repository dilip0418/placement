package com.tvsgdp.placement.student;

import com.tvsgdp.placement.college.College;
import jakarta.persistence.*;
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
}
