package com.example.demo.bean;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
interface StudentRepository extends JpaRepository<Student,Long> ,JpaSpecificationExecutor<Student> {
    Student findStudentById(long id);

}
