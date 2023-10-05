package com.tvsgdp.placement.college;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollegeRequest {
    private String collegeName;
    private Long collegeAdminId;
    private String location;
}
