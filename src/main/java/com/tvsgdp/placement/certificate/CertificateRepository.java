package com.tvsgdp.placement.certificate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CertificateRepository extends JpaRepository<Certificate,Long> {

}
