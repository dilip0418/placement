package com.tvsgdp.placement.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private Long id;
    private Long hallTicketNo;
    private String name;
    private String qualification;
    private String course;
    private Long yop;
    private String collegeName;
    private String collegeLocation;

}
