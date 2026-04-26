package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Job;
import com.example.demo.repository.JobRepository;

@RestController
@CrossOrigin
public class JobController {

    @Autowired
    private JobRepository repo;

    // ADD JOB
    @PostMapping("/jobs")
    public String addJob(@RequestBody Job job){
        repo.save(job);
        return "Job Added";
    }

    // GET JOBS (pagination safe)
    @GetMapping("/jobs")
    public Page<Job> getJobs(
        @RequestParam(defaultValue="0") int page,
        @RequestParam(defaultValue="10") int size){

        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable);
    }

    // DELETE
    @DeleteMapping("/jobs/{id}")
    public String deleteJob(@PathVariable int id){
        repo.deleteById(id);
        return "Deleted Successfully";
    }
}