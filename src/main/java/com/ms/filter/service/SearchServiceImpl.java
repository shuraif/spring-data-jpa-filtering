package com.ms.filter.service;

import com.ms.filter.dto.request.StudentSearchRequest;
import com.ms.filter.dto.response.StudentSearchResponse;
import com.ms.filter.entity.Student;

import com.ms.filter.repo.StudentRepo;
import com.ms.filter.spec.StudentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
public class SearchServiceImpl implements SearchService {

  @Autowired
  StudentRepo studentRepo;
  public Page<StudentSearchResponse> searchStudent(StudentSearchRequest searchRequest, Pageable pageable) {

    Specification<Student> spec = StudentSpecification.filter(searchRequest.getFilters());
    return studentRepo.findAll(spec, pageable).map(
        student -> new StudentSearchResponse(
            student.getId(),
            student.getName(),
            student.getEmail(),
            student.getPhone(),
            student.getDepartment(),
            student.getCity(),
            student.getState(),
            student.getCountry(),
            student.getDob(),
            student.getReview()
        )
    );

  }
}
