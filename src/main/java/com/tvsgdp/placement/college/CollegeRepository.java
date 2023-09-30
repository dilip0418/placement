package com.tvsgdp.placement.college;

import com.tvsgdp.placement.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {

    Optional<College> findByCollegeAdminId(Long id);

<<<<<<< HEAD
    Optional<College> findByLocation(String location);

    Optional<List<?>> findAllByLocation(String location);
=======
    List<College> findByLocation(String location);
>>>>>>> 9403f82656d7aa20a9d8b2b0721393c3b48f74ba
}