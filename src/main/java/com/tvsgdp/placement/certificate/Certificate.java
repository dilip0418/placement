package com.tvsgdp.placement.certificate;

import com.tvsgdp.placement.college.College;
import com.tvsgdp.placement.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer yop;

    //dependency of college
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = College.class)
    @JoinColumn(name = "college_id")
    private College college;

    //dependency of student
    @OneToOne(cascade = CascadeType.ALL, targetEntity = Student.class)
    @JoinColumn(name = "student_id")
    private Student student;
}
