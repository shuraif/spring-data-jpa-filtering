package com.ms.filter.controller;


import com.ms.filter.dto.request.StudentSearchRequest;
import com.ms.filter.dto.response.StudentSearchResponse;
import com.ms.filter.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

  @Autowired
  private SearchService searchService;

  @PostMapping("/search")
  public @ResponseBody ResponseEntity<Page<StudentSearchResponse>> searchStudent(
      @RequestBody StudentSearchRequest searchRequest,
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size
      ) {

     Pageable pageable = PageRequest.of(page, size);
     Page<StudentSearchResponse> response = searchService.searchStudent(searchRequest, pageable);
     return ResponseEntity.ok(response);
  }
}
