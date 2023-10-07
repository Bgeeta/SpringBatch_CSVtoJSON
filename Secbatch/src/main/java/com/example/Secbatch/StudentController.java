package com.example.Secbatch;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;


@RestController
public class StudentController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job csvToJsonJob;

    @Autowired
    private Job csvToJsonGetECEJob, csvToJsonGetCSEJob;
    

    StudentController(){
    }

    @GetMapping("/convert")
    public ResponseEntity<String> convertCsvToJson() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("outputFile", "output.json")
            .toJobParameters();

        jobLauncher.run(csvToJsonJob, jobParameters);

        return ResponseEntity.ok("CSV to JSON conversion job started.");
    }

    @GetMapping("/convert/{dept}")
    public ResponseEntity<String> convertCsvToJsonCSE(@PathVariable String dept) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("outputFile", "output.json")
            .addLong("timestamp", System.currentTimeMillis())
            .toJobParameters();

        if(dept.equals("cse"))
        jobLauncher.run(csvToJsonGetCSEJob, jobParameters);
        else if(dept.equals("ece"))
        jobLauncher.run(csvToJsonGetECEJob, jobParameters);
        else
        return ResponseEntity.notFound().build();
        //jobLauncher.run(csvToJsonJob, jobParameters);"No such department"
        return ResponseEntity.ok("CSV to JSON conversion job started.And an output file is generated which contains all the "+dept+" records");
    }
}

