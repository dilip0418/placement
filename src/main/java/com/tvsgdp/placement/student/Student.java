package com.tvsgdp.placement.student;

import com.tvsgdp.placement.certificate.Certificate;
import com.tvsgdp.placement.college.College;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    @SequenceGenerator(name = "student_seq", sequenceName = "student_sequence", allocationSize = 1, initialValue = 100)
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = College.class)
    @JoinColumn(name = "college_id")
    private College college;
    private String qualification;
    private String course;
    private Long yop;
    @OneToOne(cascade = CascadeType.ALL, targetEntity = Certificate.class)
    @JoinColumn(name = "certificate_code")
    private Certificate certificate;
    @NaturalId(mutable = false)
    private Long hallTicketNo;



}
