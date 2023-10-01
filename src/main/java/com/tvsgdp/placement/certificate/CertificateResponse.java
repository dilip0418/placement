package com.tvsgdp.placement.certificate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateResponse {

    private Long hallTicketNo;
    private String name;
    private String qualification;
    private String course;
    private Integer yop;
    private String collegeName;
    private String collegeLocation;
}
