package com.aking.Job.portal.controller;


import com.aking.Job.portal.entity.JobPostActivity;
import com.aking.Job.portal.entity.JobSeekerProfile;
import com.aking.Job.portal.entity.JobSeekerSave;
import com.aking.Job.portal.entity.Users;
import com.aking.Job.portal.services.JobPostActivityService;
import com.aking.Job.portal.services.JobSeekerProfileService;
import com.aking.Job.portal.services.JobSeekerSaveService;
import com.aking.Job.portal.services.UsersService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class JobSeekerSaveController {
    private final UsersService usersService;
    private final JobSeekerProfileController jobSeekerProfileController;
    private final JobPostActivityService jobPostActivityService;
    private final JobSeekerSaveService jobSeekerSaveService;
    private final JobSeekerProfileService jobSeekerProfileService;

    public JobSeekerSaveController(UsersService usersService, JobSeekerProfileController jobSeekerProfileController, JobPostActivityService jobPostActivityService, JobSeekerSaveService jobSeekerSaveService, JobSeekerProfileService jobSeekerProfileService) {
        this.usersService = usersService;
        this.jobSeekerProfileController = jobSeekerProfileController;
        this.jobPostActivityService = jobPostActivityService;
        this.jobSeekerSaveService = jobSeekerSaveService;
        this.jobSeekerProfileService = jobSeekerProfileService;
    }

    @PostMapping("job-details/save/{id}")
    public String save(@PathVariable("id") int id, JobSeekerSave jobSeekerSave){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationFilter)){
            String currentUsername = authentication.getName();
            Users users = usersService.findByEmail(currentUsername);
            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(users.getUserId());
            JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
            if(seekerProfile.isPresent()&& jobPostActivity!=null){
                jobSeekerSave.setJob(jobPostActivity);
                jobSeekerSave.setUserId(seekerProfile.get());
            }else{
                throw new RuntimeException("User not found");
            }
            jobSeekerSaveService.addNew(jobSeekerSave);
        }
        return "redirect:/dashboard/";
    }

    @GetMapping("saved-jobs/")
    public String savedJobs(Model model){
        List<JobPostActivity> jobPost = new ArrayList<>();
        Object currentUserProfile = usersService.getCurrentUserProfile();
        List<JobSeekerSave>jobSeekerSaveList=jobSeekerSaveService.getCandidatesJob((JobSeekerProfile) currentUserProfile);
        for (JobSeekerSave jobSeekerSave:jobSeekerSaveList){
            jobPost.add(jobSeekerSave.getJob());
        }

        model.addAttribute("jobPost",jobPost);
        model.addAttribute("user",currentUserProfile);
        return "saved-jobs";
    }
}
