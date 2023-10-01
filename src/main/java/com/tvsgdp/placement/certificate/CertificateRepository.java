package com.tvsgdp.placement.certificate;

import com.tvsgdp.placement.college.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate,Long> {

    List<Certificate> findByCollegeId(Long id);

    Optional<Certificate> findByStudentId(Long id);
}
