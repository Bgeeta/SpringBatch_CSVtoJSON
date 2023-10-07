package com.example.Secbatch;

import org.springframework.batch.item.ItemProcessor;

public class StudentProcessor implements ItemProcessor<Student, Student> {
    String dept = null;

    StudentProcessor(String dept) {
        this.dept = dept;
    }

    StudentProcessor() {
    }

    @Override
    public Student process(Student item) throws Exception {
        if (this.dept == null || item.getDept().equals(this.dept))
            return item;
        return null;
    }
}
