package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ApplicationDTO;
import com.example.demo.entity.Application;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.JobRepository;
import com.example.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class ApplicationController {

    @Autowired
    private ApplicationRepository repo;
    
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JobRepository jobRepo;

    // APPLY JOB (simple)
    @PostMapping("/apply")
    public String apply(@RequestParam int userId,
                        @RequestParam int jobId){

        // 🔥 CHECK DUPLICATE
        boolean exists = repo.existsByUserIdAndJobId(userId, jobId);

        if(exists){
            return "Already Applied ❌";
        }

        Application a = new Application();
        a.setUserId(userId);
        a.setJobId(jobId);

        repo.save(a);

        return "Applied Successfully ✔";
    }

    // ADMIN VIEW
    @GetMapping("/applications")
    public List<Application> getAll(){
        return repo.findAll();
    }
    
    @GetMapping("/applications/details")
    public List<ApplicationDTO> getDetails(){

        List<Application> list = repo.findAll();

        List<ApplicationDTO> result = new ArrayList<>();

        for(Application a : list){

            String name = userRepo.findById(a.getUserId()).get().getName();
            String job = jobRepo.findById(a.getJobId()).get().getTitle();

            result.add(new ApplicationDTO(name, job));
        }

        return result;
    }
}