package com.example.Secbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    /*
     * TODO: Get the output file as the job paramerer
     */
    String outputFile = "output.json"; // hardcoding the output file

    public BatchConfiguration() {

    }

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Student> reader() {
        return new FlatFileItemReaderBuilder<Student>()
                .name("personItemReader")
                .resource(new ClassPathResource("test.csv"))
                .delimited()
                .names(new String[] { "id", "name", "dept", "dept_head", "college" })
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Student>() {
                    {
                        setTargetType(Student.class);
                    }
                })
                .build();
    }

    @Bean
    public ItemWriter<Student> writer() {

        return new JsonItemWriter<Student>("output.json");
    }

    /*
     * Used by csvToJsonJob converts all the students info in csv file to json
     */

    @Bean
    public Step step1(ItemReader<Student> reader, ItemWriter<Student> writer) {
        return stepBuilderFactory.get("step1")
                .<Student, Student>chunk(2)
                .reader(reader)
                .writer(writer)
                .build();
    }

    /*
     * Used by 'csvToJsonGetECEJob' to convert all student information in a CSV file
     * to JSON if the student's department value is 'ece'.
     */

    @Bean
    public Step step2(ItemReader<Student> reader, ItemWriter<Student> writer) {
        return stepBuilderFactory.get("step2")
                .<Student, Student>chunk(2)
                .reader(reader)
                .processor(new StudentProcessor("ece"))
                .writer(writer)
                .build();
    }

    /*
     * Used by 'csvToJsonGetECEJob' to convert all student information in a CSV file
     * to JSON if the student's department value is 'cse'.
     */
    @Bean
    public Step step3(ItemReader<Student> reader, ItemWriter<Student> writer) {
        return stepBuilderFactory.get("step3")
                .<Student, Student>chunk(2)
                .reader(reader)
                .processor(new StudentProcessor("cse"))
                .writer(writer)
                .build();
    }

    /*
     * Convert all student information in a CSV file to JSON
     */
    @Bean
    public Job csvToJsonJob(Step step1) {
        return jobBuilderFactory.get("csvToJsonJob")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }

    /*
     * Writes only students with dept value as 'ece'
     */
    @Bean
    public Job csvToJsonGetECEJob(Step step2) {
        return jobBuilderFactory.get("csvToJsonGetECEJob")
                .incrementer(new RunIdIncrementer())
                .start(step2)
                .build();
    }

    /*
     * Writes only students with dept value as 'cse'
     */
    @Bean
    public Job csvToJsonGetCSEJob(Step step3) {
        return jobBuilderFactory.get("csvToJsonGetCSEJob")
                .incrementer(new RunIdIncrementer())
                .start(step3)
                .build();
    }

}
