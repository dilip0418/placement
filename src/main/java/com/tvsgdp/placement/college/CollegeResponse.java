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
}
