package com.tvsgdp.placement.certificate;

import com.tvsgdp.placement.college.College;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Certificate {

    @Id
    @Column(name = "certificate_code")
    private Long code;
    private LocalDate issueDate;

//    private LocalDate issueDate = LocalDate.now();

    //dependency of college
    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = College.class)
    @JoinColumn(name = "college_id")
    private College college;

}
