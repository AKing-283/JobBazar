package com.aking.Job.portal.repository;

import com.aking.Job.portal.entity.JobPostActivity;
import com.aking.Job.portal.entity.JobSeekerProfile;
import com.aking.Job.portal.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer> {

    public List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);

    List<JobSeekerSave> findByJob(JobPostActivity job);

}
