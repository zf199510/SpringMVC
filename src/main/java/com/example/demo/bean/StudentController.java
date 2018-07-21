package com.example.demo.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "student")
public class StudentController implements StudentQueryService{
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/test")
    public String test() {
        return "aaa";
    }

    @RequestMapping("/add")
    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    @PostMapping("/del")
    public void delStudent(long id) {
        studentRepository.deleteById(id);
    }

    @PostMapping("/update")
    @Modifying
    @Transactional
    public void updateStudent(Student student) {
        System.out.print(student.toString());
        Student student1 = studentRepository.findStudentById(student.getId());
        student1.setName(student.getName());
        student1.setPhone(student.getPhone());
        student1.setSex(student.getSex());
        System.out.print(student1.toString());
    }

    @PostMapping("/search")
    public String searchStudent(long id) {
        Student s = studentRepository.findStudentById(id);
        System.out.println(s.toString());
        return s.toString();
    }
    


    @Override
    public Page<Student> findStudentNoCriteria(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page,size, Sort.Direction.ASC,"id");
        return studentRepository.findAll(pageable);
    }

    @Override
    public Page<Student> findStudentCriteria(Integer page, Integer size, StudentQuery studentQuery) {

        Pageable pageable = new PageRequest(page,size,Sort.Direction.ASC,"id");
        Page<Student> studentPage = studentRepository.findAll(new Specification<Student>(){
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(null!=studentQuery.getName()&&!"".equals(studentQuery.getName())){
                    list.add(criteriaBuilder.equal(root.get("name").as(String.class), studentQuery.getName()));
                }
                if(null!=studentQuery.getPhone()&&!"".equals(studentQuery.getPhone())){
                    list.add(criteriaBuilder.equal(root.get("isbn").as(String.class), studentQuery.getPhone()));
                }
                if(null!=studentQuery.getSex()&&!"".equals(studentQuery.getSex())){
                    list.add(criteriaBuilder.equal(root.get("author").as(String.class), studentQuery.getSex()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        return studentPage;
    }
}
