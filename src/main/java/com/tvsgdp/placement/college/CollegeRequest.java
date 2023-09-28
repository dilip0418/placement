package com.tvsgdp.placement.college;

import com.tvsgdp.placement.user.User;
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
