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
/*
@Builder is an annotation commonly used in Java to simplify the process of creating and initializing objects
with numerous fields, especially in situations where there are many optional parameters.
It is often used in conjunction with the Builder design pattern to improve the readability and maintainability of code.
 */
@Builder
/*
@Builder is an annotation commonly used in Java to simplify the process of creating and initializing objects
with numerous fields, especially in situations where there are many optional parameters.
It is often used in conjunction with the Builder design pattern to improve the readability and maintainability of code.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    @SequenceGenerator(name = "student_seq", sequenceName = "student_sequence", allocationSize = 1, initialValue = 100)
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = College.class)
    @JoinColumn(name = "college_id")
    private College college;
    private String qualification;
    private String course;
    private Long yop;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = Certificate.class, orphanRemoval = true)
    @JoinColumn(name = "certificate_code")
    private Certificate certificate;

    @NaturalId
    private Long hallTicketNo;

}
