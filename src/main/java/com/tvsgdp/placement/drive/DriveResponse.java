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

public class DriveResponse {
    private Long id;
    private String name;
    private LocalDate date;
    private String qualification;
    private Integer year;
    private String collegeName;
    private String collegeLocation;

    public static DriveResponse getDriveResponse(Drive drive){
       return DriveResponse.builder()
                .id(drive.getId())
                .name(drive.getName())
                .date(drive.getDate())
                .qualification(drive.getQualification())
                .year(drive.getYear())
                .collegeName(drive.getCollege().getCollegeName())
                .collegeLocation(drive.getCollege().getLocation())
                .build();

    }
}
