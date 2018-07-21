package com.example.demo.bean;

import org.springframework.data.domain.Page;

public interface StudentQueryService {
    Page<Student> findStudentNoCriteria(Integer page,Integer size);
    Page<Student> findStudentCriteria(Integer page,Integer size,StudentQuery studentQuery);
}
