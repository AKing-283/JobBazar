package com.aking.Job.portal.repository;

import com.aking.Job.portal.entity.JobPostActivity;
import com.aking.Job.portal.entity.JobSeekerApply;
import com.aking.Job.portal.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply, Integer> {

    List<JobSeekerApply> findByUserId(JobSeekerProfile userId);

    List<JobSeekerApply> findByJob(JobPostActivity job);
}