package com.tvsgdp.placement.drive;

import com.tvsgdp.placement.college.College;

import com.tvsgdp.placement.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Drive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate date;
    @Column(name = "qualification")
    private String qualification;
    @Column(name = "year")
    private Integer year;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(targetEntity = College.class)
    @JoinColumn(name = "college_id")
    private College college;





}
