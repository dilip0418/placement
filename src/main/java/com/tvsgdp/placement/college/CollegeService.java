package com.tvsgdp.placement.college;

import com.tvsgdp.placement.exception.UserAlreadyHasACollegeException;
import com.tvsgdp.placement.user.User;
import com.tvsgdp.placement.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CollegeService {

    private final CollegeRepository collegeRepository;
    private final UserRepository userRepository;

    public Optional<CollegeResponse> getCollegeByCollegeAdminId(@PathVariable Long adminId) {
        Optional<College> college = collegeRepository.findByCollegeAdminId(adminId);
        if (college.isEmpty()) {
            return Optional.empty();
        }
        return college.map(value -> CollegeResponse.builder()
                .collegeName(value.getCollegeName())
                .build());
    }

    public CollegeResponse createCollege(CollegeRequest collegeRequest) throws UserAlreadyHasACollegeException {

        // Check if the college admin user exists
        User collegeAdmin = userRepository.findById(collegeRequest.getCollegeAdminId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + collegeRequest.getCollegeAdminId() + " not found"));

        // Check if the college already exists
        Optional<College> existingCollege = collegeRepository.findByCollegeAdminId(collegeAdmin.getId());
        if (existingCollege.isPresent()) {
            throw new UserAlreadyHasACollegeException("user with id " +
                    existingCollege.get().getCollegeAdmin().getId() + " already has a College");
        }
        // Create a new college
        College college = College.builder()
                .collegeName(collegeRequest.getCollegeName())
                .collegeAdmin(collegeAdmin)
                .location(collegeRequest.getLocation())
                .build();
        College savedCollege = collegeRepository.save(college);

        return CollegeResponse.builder()
                .collegeName(savedCollege.getCollegeName())
                .build();
    }


    public Optional<?> getAllCollegeLocation(String location) {
        return collegeRepository.findAllByLocation(location);
    }

    public Optional<?> getAllCollege() {
        return Optional.of(collegeRepository.findAll());
    }
}
