package com.tvsgdp.placement.college;

import com.tvsgdp.placement.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollegeRepository extends JpaRepository<College, Long> {

    Optional<College> findByCollegeAdminId(Long id);

    Optional<College> findByLocation(String location);
}