package com.tvsgdp.placement.drive;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriveRepository extends JpaRepository<Drive,Long> {

    List<Drive> findByQualification(String qualification);

    List<Drive> findByYear(Integer year);

    List<Drive> findAllByUserId(Long userId);

    List<Drive> findByQualificationAndUserId(String qualification, Long userId);

    List<Drive> findByYearAndUserId(Integer year, Long userId);

    List<Drive> findByCollegeId(Long collegeId);


    //    List<Drive> findById();
}
