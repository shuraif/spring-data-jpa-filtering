package com.ms.filter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
public class StudentSearchResponse {

  private Integer id;
  private String name;
  private String email;
  private String phone;
  private String department;
  private String city;
  private String state;
  private String country;
  private LocalDate dob;

}
