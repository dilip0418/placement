package com.tvsgdp.placement.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Optional<Student> findByHallTicketNo(Long HallTicketNo);

    List<Student> findStudentByCourse(String course);

    List<Student> findStudentByQualification(String qualification);

    List<Student> findStudentByYop(Long yop);

    List<Student> findStudentByCollegeId(Long id);

    List<Student> findStudentByName(String name);

}
