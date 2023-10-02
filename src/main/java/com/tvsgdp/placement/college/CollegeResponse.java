package com.tvsgdp.placement.college;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollegeResponse {
    private Long id;
    private String collegeName;
    private String location;
    private String collegeAdminName;
    private String collegeAdminEmail;


    public static CollegeResponse getCollegeResponse(College college){
        return CollegeResponse.builder()
                .id(college.getId())
                .collegeName(college.getCollegeName())
                .location(college.getLocation())
                .collegeAdminName(college.getCollegeAdmin().getName())
                .collegeAdminEmail(college.getCollegeAdmin().getEmail())
                .build();
    }
}
