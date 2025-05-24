package com.ms.filter.service;


import com.ms.filter.dto.request.StudentSearchRequest;
import com.ms.filter.dto.response.StudentSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchService {

  public Page<StudentSearchResponse> searchStudent(StudentSearchRequest searchRequest, Pageable pageable);
}
