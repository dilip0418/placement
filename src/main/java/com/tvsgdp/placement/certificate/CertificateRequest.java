package com.tvsgdp.placement.certificate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateRequest {

    //private Long id;
    private Integer yop;
    private Long college_id;
    private Long student_id;
}
