package com.ms.filter.dto.request;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Getter
public class StudentSearchRequest {

  private Map<String, String> filters;

}
