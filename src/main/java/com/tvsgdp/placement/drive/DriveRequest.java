package com.tvsgdp.placement.drive;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class DriveRequest {
    private String name;

    private LocalDate date;

    private String qualification;

    private Integer year;

    private Long userId;

    private Long collegeId;
}
