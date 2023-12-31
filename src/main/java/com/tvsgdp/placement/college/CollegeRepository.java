package com.tvsgdp.placement.college;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {

    Optional<College> findByCollegeAdminId(Long id);

    List<College> findByLocation(String location);

    Optional<College> findByCollegeName(String collegeName);
}