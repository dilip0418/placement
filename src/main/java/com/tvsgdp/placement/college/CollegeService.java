package com.tvsgdp.placement.college;

import com.tvsgdp.placement.exception.NoCollegeFoundWithLocationException;
import com.tvsgdp.placement.exception.UserAlreadyHasACollegeException;
import com.tvsgdp.placement.user.User;
import com.tvsgdp.placement.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;


@Service
/*for constructor based dependency injection of required dependencies
this(@RequiredArgsConstructor) annotation will inject all the references declared as final
 */
@RequiredArgsConstructor
public class CollegeService {

    private final CollegeRepository collegeRepository;
    private final UserRepository userRepository;

    public Optional<CollegeResponse> getCollegeByCollegeAdminId(@PathVariable Long adminId) throws Exception {
        Optional<College> college = collegeRepository.findByCollegeAdminId(adminId);
        /*
        1. check if college is empty
        2. if college is empty throw an exception
         */
        if (college.isEmpty()) {
            throw new Exception("Could not find College");
        }

        /*return college as a CollegeResponse object
        [Using the lambda method reference feature to map the college object to the collegeResponse Object]
         */
        return college.map(CollegeResponse::getCollegeResponse);
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
                .map(CollegeResponse::getCollegeResponse)
                .toList();
    }

    public List<CollegeResponse> getAllCollege() throws Exception {
        //get all colleges
        List<College> colleges = collegeRepository.findAll();

        if (colleges.isEmpty()) {
            throw new Exception("No colleges found!");
        }
        /*return colleges as a list of CollegeResponse objects
         return college as a CollegeResponse object
        [Using the lambda method reference feature to map the college object to the collegeResponse Object]
         */
        return colleges.stream()
                .map(CollegeResponse::getCollegeResponse)
                .toList();
    }

    public Optional<CollegeResponse> getCollegeById(Long id) {

        //get college by id
        Optional<College> college = collegeRepository.findById(id);

        //check if college exists and throw an exception if no College is found with this id
        if (college.isEmpty()) {
            return Optional.empty();
        }

        /* return college as a CollegeResponse object
        [Using the lambda method reference feature to map the college object to the collegeResponse Object]
         */
        return college.map(CollegeResponse::getCollegeResponse);
    }

    public CollegeResponse createCollege(CollegeRequest collegeRequest) throws UserAlreadyHasACollegeException {

        // Check if the college admin user exists - [This is used to set the College admin attribute while creating]
        User collegeAdmin = userRepository.findById(collegeRequest.getCollegeAdminId())
                .orElseThrow(() -> new UserAlreadyHasACollegeException("User with id " +
                        collegeRequest.getCollegeAdminId() + " not found"));

        // Check if the college already exists
        Optional<College> existingCollege = collegeRepository.findByCollegeAdminId(collegeRequest.getCollegeAdminId());
        if (existingCollege.isPresent()) {
            throw new UserAlreadyHasACollegeException("user already has a College");
        }
        // Create a new college
        College college = College.builder()
                .collegeName(collegeRequest.getCollegeName())
                .collegeAdmin(collegeAdmin) // Used the above obtained user {collegeAdmin} to add this here.
                .location(collegeRequest.getLocation())
                .build();
        College savedCollege = collegeRepository.save(college);

        // Return the new college as a collegeResponse object
        return CollegeResponse.getCollegeResponse(savedCollege);
    }

    public CollegeResponse updateCollege(CollegeRequest collegeRequest) throws Exception {
        //get college by id & check if college exists and throw an exception if no College is found with this id
        Optional<College> college = Optional.ofNullable(collegeRepository.findByCollegeAdminId(
                        collegeRequest.getCollegeAdminId())
                .orElseThrow(() -> new Exception("Could not find")));
        System.out.println(college);

        // update college
        College collegeToUpdate = college.get();
        collegeToUpdate.setCollegeName(collegeRequest.getCollegeName());
        collegeToUpdate.setLocation(collegeRequest.getLocation());
        collegeToUpdate.setCollegeAdmin(college.get().getCollegeAdmin());
        collegeRepository.save(collegeToUpdate);

        // return updated college as a CollegeResponse object
        return CollegeResponse.getCollegeResponse(collegeToUpdate);
    }
}
