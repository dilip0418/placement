package com.tvsgdp.placement.college;

import com.tvsgdp.placement.exception.NoCollegeFoundWithLocationException;
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
@RequiredArgsConstructor //for constructor injection of required dependencies this annotation will inject all the references declared as final
public class CollegeService {

    private final CollegeRepository collegeRepository;
    private final UserRepository userRepository;

    public Optional<CollegeResponse> getCollegeByCollegeAdminId(@PathVariable Long adminId) {
        Optional<College> college = collegeRepository.findByCollegeAdminId(adminId);
        if (college.isEmpty()) {
            return Optional.empty();
        }
        return college.map(value -> CollegeResponse.builder()
                .id(value.getId())
                .collegeName(value.getCollegeName())
                .collegeAdminName(value.getCollegeAdmin().getName())
                .collegeAdminEmail(value.getCollegeAdmin().getEmail())
                .location(value.getLocation())
                .build());
    }


    public List<CollegeResponse> getAllCollegeLocation(String location) throws NoCollegeFoundWithLocationException {

        //get all colleges
        List<College> colleges = collegeRepository.findByLocation(location);

        //check if colleges exist with this location and throw an exception if no College is found with this location
        if (colleges.isEmpty()) {
            throw new NoCollegeFoundWithLocationException("No college found with location " + location);
        }

        // return colleges with this location as a list of CollegeResponse objects
        return colleges.stream()
                .map(college ->
                        CollegeResponse.builder()
                                .id(college.getId())
                                .collegeName(college.getCollegeName())
                                .location(college.getLocation())
                                .collegeAdminName(college.getCollegeAdmin().getName())
                                .collegeAdminEmail(college.getCollegeAdmin().getEmail())
                                .build())
                .toList();
    }

    public List<CollegeResponse> getAllCollege() {
        //get all colleges
        List<College> colleges = collegeRepository.findAll();

        //return colleges as a list of CollegeResponse objects
        return colleges.stream()
                .map(college ->
                        CollegeResponse.builder()
                                .id(college.getId())
                                .collegeName(college.getCollegeName())
                                .location(college.getLocation())
                                .collegeAdminName(college.getCollegeAdmin().getName())
                                .collegeAdminEmail(college.getCollegeAdmin().getEmail())
                                .build())
                .toList();
    }

    public Optional<CollegeResponse> getCollegeById(Long id) {

        //get college by id
        Optional<College> college = collegeRepository.findById(id);

        //check if college exists and throw an exception if no College is found with this id
        if (college.isEmpty()) {
            return Optional.empty();
        }

        // return college as a CollegeResponse object
        return college.map(value -> CollegeResponse.builder()
                .id(value.getId())
                .collegeName(value.getCollegeName())
                .collegeAdminName(value.getCollegeAdmin().getName())
                .collegeAdminEmail(value.getCollegeAdmin().getEmail())
                .location(value.getLocation())
                .build());
    }

    public CollegeResponse createCollege(CollegeRequest collegeRequest) throws UserAlreadyHasACollegeException {

        // Check if the college admin user exists - [This is used to set the College admin attribute while creating]
        User collegeAdmin = userRepository.findById(collegeRequest.getCollegeAdminId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " +
                        collegeRequest.getCollegeAdminId() + " not found"));

        // Check if the college already exists
        Optional<College> existingCollege = collegeRepository.findByCollegeAdminId(collegeRequest.getCollegeAdminId());
        if (existingCollege.isPresent()) {
            throw new UserAlreadyHasACollegeException("user with id " +
                    existingCollege.get().getCollegeAdmin().getId() + " already has a College");
        }
        // Create a new college
        College college = College.builder()
                .collegeName(collegeRequest.getCollegeName())
                .collegeAdmin(collegeAdmin) // Used the above obtained user {collegeAdmin} to add this here.
                .location(collegeRequest.getLocation())
                .build();
        College savedCollege = collegeRepository.save(college);

        // Return the new college as a collegeResponse object
        return CollegeResponse.builder()
                .id(college.getId())
                .collegeName(savedCollege.getCollegeName())
                .location(savedCollege.getLocation())
                .collegeAdminName(collegeAdmin.getName())
                .collegeAdminEmail(collegeAdmin.getEmail())
                .build();
    }

    public CollegeResponse updateCollege(CollegeRequest collegeRequest) {
        //get college by id & check if college exists and throw an exception if no College is found with this id
        Optional<College> college = Optional.ofNullable(collegeRepository.findByCollegeAdminId(
                        collegeRequest.getCollegeAdminId())
                .orElseThrow(() -> new EntityNotFoundException("Could not find")));
        System.out.println(college);

        /*
        Check if the college admin user exists
        User collegeAdmin = userRepository.findById(collegeRequest.getCollegeAdminId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " +
                        collegeRequest.getCollegeAdminId() + " not found"));


        check if college exists and throw an exception if no College is found with this id
        if (college.isEmpty()) {
            throw new EntityNotFoundException("College not found");
        }*/

        // update college
        College collegeToUpdate = college.get();
        collegeToUpdate.setCollegeName(collegeRequest.getCollegeName());
        collegeToUpdate.setLocation(collegeRequest.getLocation());
        collegeToUpdate.setCollegeAdmin(college.get().getCollegeAdmin());
        collegeRepository.save(collegeToUpdate);

        // return updated college as a CollegeResponse object
        return CollegeResponse.builder()
               .id(collegeToUpdate.getId())
                .collegeName(collegeToUpdate.getCollegeName())
               .location(collegeToUpdate.getLocation())
                .collegeAdminName(collegeToUpdate.getCollegeAdmin().getName())
                .collegeAdminEmail(collegeToUpdate.getCollegeAdmin().getEmail())
                .build();
    }
}
