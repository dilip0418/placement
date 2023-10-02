package com.tvsgdp.placement.drive;

import com.tvsgdp.placement.college.College;
import com.tvsgdp.placement.college.CollegeRepository;
import com.tvsgdp.placement.user.User;
import com.tvsgdp.placement.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DriveService {
    @Autowired
    private final CollegeRepository collegeRepository;
    private final UserRepository userRepository;
    @Autowired
    DriveRepository driveRepository;

    public List<DriveResponse> getAllDrives() throws Exception {

        List<Drive> drives = driveRepository.findAll();
        if (drives.isEmpty()) throw new Exception("No Drives Found, please create one....");
        return drives.stream().map(DriveResponse::getDriveResponse).toList();
    }


    public DriveResponse getDriveById(Long id) throws Exception {

        Optional<Drive> drive = driveRepository.findById(id);
        if (drive.isEmpty()) throw new Exception("Drive Not Found");

        return DriveResponse.getDriveResponse(drive.get());
    }

    public DriveResponse addDrive(DriveRequest driveRequest) throws Exception {
        Optional<User> user = userRepository.findById(driveRequest.getUserId());
        if (user.isEmpty()) throw new Exception("user not found");

        Optional<College> college = collegeRepository.findById(driveRequest.getCollegeId());
        if (college.isEmpty()) throw new Exception("college not found");

        Drive drive = Drive.builder()
                .name(driveRequest.getName())
                .date(driveRequest.getDate())
                .year(driveRequest.getYear())
                .qualification(driveRequest.getQualification())
                .user(user.get())
                .college(college.get())
                .build();

        driveRepository.save(drive);
        return DriveResponse.getDriveResponse(drive);

    }


    public void deleteById(Long id) throws Exception {
        Optional<Drive> drive = driveRepository.findById(id);
        if (drive.isEmpty()) throw new Exception("Drive Not Found");
        driveRepository.deleteById(id);
    }


    public List<DriveResponse> getDrivesByQualification(String qualification) throws Exception {
        List<Drive> drives = driveRepository.findByQualification(qualification);
        if (drives.isEmpty()) throw new Exception("No Drive Found, Please create a new drive");
        return drives.stream().map(DriveResponse::getDriveResponse).toList();
    }

    public List<DriveResponse> getDrivesByYear(Integer year) throws Exception {

        List<Drive> drives = driveRepository.findByYear(year);
        if (drives.isEmpty()) throw new Exception("No Drive Found, Please create a new drive");
        return drives.stream().map(DriveResponse::getDriveResponse).toList();
    }

//Building getMethods for specific user ...
    public List<DriveResponse> getAllDrivesByUserId(Long userId) throws Exception {
        List<Drive> drives = driveRepository.findAllByUserId(userId);
        if (drives.isEmpty())
            throw new Exception("No Drives Found, please create one....");
        return drives.stream().map(DriveResponse::getDriveResponse).toList();


    }
    public List<DriveResponse> getDrivesByYearWithUserId(Integer year,Long userId) throws Exception {

        List<Drive> drives = driveRepository.findByYearAndUserId(year,userId);
        if (drives.isEmpty()) throw new Exception("No Drive Found, Please create a new drive");
        return drives.stream().map(DriveResponse::getDriveResponse).toList();
    }

    public List<DriveResponse> getDrivesByQualificationWithUserId(String qualification, Long userId) throws Exception {
        List<Drive> drives = driveRepository.findByQualificationAndUserId(qualification,userId);
        if (drives.isEmpty()) throw new Exception("No Drive Found, Please create a new drive");
        return drives.stream().map(DriveResponse::getDriveResponse).toList();
    }
}
