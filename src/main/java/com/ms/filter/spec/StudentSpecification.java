package com.ms.filter.spec;

import com.ms.filter.entity.Student;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class StudentSpecification {
  public static Specification<Student> filter(Map<String, String> filters) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      filters.forEach((key, value) -> {
        switch (key) {
          // this one work similar to SQL where clause with like operator
          // WERE name LIKE '%value%'
          case "name" -> predicates.add(cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%"));

          // In operator for country fields, allowing multiple values separated by commas
          // This is similar to SQL where clause with IN operator
          // WHERE country IN ('US', 'UK', ...)
          case "country" -> {
            String[] values = value.split(",");
            predicates.add(root.get("country").in(Arrays.asList(values)));
          }

          // Exact match for department field. This is similar to SQL where clause with equal operator
          // WHERE department = value
          case "department" -> predicates.add(cb.equal(root.get("department"), value));
          case "city" -> predicates.add(cb.equal(root.get("city"), value));
          case "email" -> predicates.add(cb.equal(root.get("email"), value));
          case "phone" -> predicates.add(cb.equal(root.get("phone"), value));

          // Exact match for date fields. If exact date is provided, it will match the date exactly.
          // WHERE dob = value
          case "dob" -> predicates.add(cb.equal(root.get("dob"), LocalDate.parse(value)));

        }
      });

      // Handle date range filtering separately in order to parse the date correctly
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      try {
        if (filters.containsKey("dobStartDate") && filters.containsKey("dobEndDate")) {
          LocalDate start = LocalDate.parse(filters.get("dobStartDate"), formatter);
          LocalDate end = LocalDate.parse(filters.get("dobEndDate"), formatter);
          predicates.add(cb.between(root.get("dob"), start, end));
        } else if (filters.containsKey("dobStartDate")) {
          LocalDate start = LocalDate.parse(filters.get("dobStartDate"), formatter);
          predicates.add(cb.greaterThanOrEqualTo(root.get("dob"), start));
        } else if (filters.containsKey("dobEndDate")) {
          LocalDate end = LocalDate.parse(filters.get("dobEndDate"), formatter);
          predicates.add(cb.lessThanOrEqualTo(root.get("dob"), end));
        }
      } catch (DateTimeParseException e) {
        // Handle invalid date format
        throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
      }

      // Add more date-related filters if needed
      // For example, if you want to filter by created date or modified date, you can add similar logic here.
//      if (filters.containsKey("startCreatedDate") && filters.containsKey("endCreatedDate")) {
//        LocalDate startCreated = LocalDate.parse(filters.get("startCreatedDate"), formatter);
//        LocalDate endCreated = LocalDate.parse(filters.get("endCreatedDate"), formatter);
//        predicates.add(cb.between(root.get("createdDate"), startCreated, endCreated));
//      } else if (filters.containsKey("startCreatedDate")) {
//        LocalDate startCreated = LocalDate.parse(filters.get("startCreatedDate"), formatter);
//        predicates.add(cb.greaterThanOrEqualTo(root.get("createdDate"), startCreated));
//      } else if (filters.containsKey("endCreatedDate")) {
//        LocalDate endCreated = LocalDate.parse(filters.get("endCreatedDate"), formatter);
//        predicates.add(cb.lessThanOrEqualTo(root.get("createdDate"), endCreated));
//      }

      // Add all predicates to the query
      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
