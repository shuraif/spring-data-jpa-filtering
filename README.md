# spring-data-jpa-filtering 

A REST API endpoint for demonstrating searching with flexible filtering capabilities and pagination support.

## Quick Links
- **API Base URL**: `https://spring-data-jpa-filtering.onrender.com`
- **Swagger UI**: [https://spring-data-jpa-filtering.onrender.com/swagger-ui/index.html](https://spring-data-jpa-filtering.onrender.com/swagger-ui/index.html)
- **API Documentation**: [https://spring-data-jpa-filtering.onrender.com/v3/api-docs](https://spring-data-jpa-filtering.onrender.com/v3/api-docs)

## Endpoint

```
POST /search
```
**Full URL**: `https://spring-data-jpa-filtering.onrender.com/search`

## Query Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `page` | Integer | 0 | Page number (0-based indexing) |
| `size` | Integer | 10 | Number of records per page |

## Request Body

The request body should contain a JSON object with a `filters` property:

```json
{
  "filters": {
    "key": "value"
  }
}
```

## Available Filters

### Text Filters

#### Name (Partial Match)
- **Key**: `name`
- **Type**: String
- **Behavior**: Case-insensitive partial match (LIKE '%value%')
- **Example**: 
  ```json
  {
    "filters": {
      "name": "john"
    }
  }
  ```
  *Matches: John Doe, Johnny Smith, Johnson, etc.*

### Multiple value Match Filters

#### Country
- **Key**: `country`
- **Type**: String
- **Behavior**: Multiple values supported (comma-separated) using IN operator
- **Single Value Example**:
  
  ```json
  {
    "filters": {
      "country": "USA"
    }
  }
  ```
- **Multiple Values Example**:
  ```json
  {
    "filters": {
      "country": "USA,Canada,UK"
    }
  }
  ```
  

### Exact Match Filters

#### Department
- **Key**: `department`
- **Type**: String
- **Behavior**: Exact match
- **Example**:
  ```json
  {
    "filters": {
      "department": "Computer Science"
    }
  }
  ```



#### City
- **Key**: `city`
- **Type**: String
- **Behavior**: Exact match
- **Example**:
  ```json
  {
    "filters": {
      "city": "New York"
    }
  }
  ```

#### Email
- **Key**: `email`
- **Type**: String
- **Behavior**: Exact match
- **Example**:
  ```json
  {
    "filters": {
      "email": "john.doe@example.com"
    }
  }
  ```

#### Phone
- **Key**: `phone`
- **Type**: String
- **Behavior**: Exact match
- **Example**:
  ```json
  {
    "filters": {
      "phone": "+1234567890"
    }
  }
  ```

### Date Filters

#### Date of Birth (Exact)
- **Key**: `dob`
- **Type**: String (Date)
- **Format**: `yyyy-MM-dd`
- **Behavior**: Exact date match
- **Example**:
  ```json
  {
    "filters": {
      "dob": "1995-05-15"
    }
  }
  ```

#### Date of Birth Range

##### Start Date Only
- **Key**: `dobStartDate`
- **Type**: String (Date)
- **Format**: `yyyy-MM-dd`
- **Behavior**: Greater than or equal to specified date
- **Example**:
  ```json
  {
    "filters": {
      "dobStartDate": "1990-01-01"
    }
  }
  ```

##### End Date Only
- **Key**: `dobEndDate`
- **Type**: String (Date)
- **Format**: `yyyy-MM-dd`
- **Behavior**: Less than or equal to specified date
- **Example**:
  ```json
  {
    "filters": {
      "dobEndDate": "2000-12-31"
    }
  }
  ```

##### Date Range
- **Keys**: `dobStartDate` and `dobEndDate`
- **Type**: String (Date)
- **Format**: `yyyy-MM-dd`
- **Behavior**: Between start and end dates (inclusive)
- **Example**:
  ```json
  {
    "filters": {
      "dobStartDate": "1993-01-01",
      "dobEndDate": "1993-12-31"
    }
  }
  ```

## Sample Requests

### Basic Search with Pagination
```bash
curl -X POST "http://localhost:8085/search?page=0&size=1" \
  -H "Content-Type: application/json" \
  -d '{
    "filters": {
      "dobStartDate": "1993-01-01",
      "dobEndDate": "1993-12-31"
    }
  }'
```

### Search by Name
```bash
curl -X POST "http://localhost:8085/search?page=0&size=10" \
  -H "Content-Type: application/json" \
  -d '{
    "filters": {
      "name": "smith"
    }
  }'
```

### Search by Department and Multiple Countries
```bash
curl -X POST "http://localhost:8085/search?page=0&size=20" \
  -H "Content-Type: application/json" \
  -d '{
    "filters": {
      "department": "Engineering",
      "country": "USA,Canada,UK"
    }
  }'
```

### Complex Search with Multiple Filters
```bash
curl -X POST "http://localhost:8085/search?page=0&size=15" \
  -H "Content-Type: application/json" \
  -d '{
    "filters": {
      "name": "john",
      "department": "Computer Science",
      "city": "San Francisco",
      "dobStartDate": "1990-01-01",
      "dobEndDate": "1995-12-31"
    }
  }'
```

### Search Students Born After Specific Date
```bash
curl -X POST "http://localhost:8085/search" \
  -H "Content-Type: application/json" \
  -d '{
    "filters": {
      "dobStartDate": "2000-01-01"
    }
  }'
```

### Search Students from Multiple Countries
```bash
curl -X POST "http://localhost:8085/search" \
  -H "Content-Type: application/json" \
  -d '{
    "filters": {
      "country": "USA,Germany,Japan,Australia"
    }
  }'
```

### Search by Exact Email
```bash
curl -X POST "http://localhost:8085/search" \
  -H "Content-Type: application/json" \
  -d '{
    "filters": {
      "email": "student@university.edu"
    }
  }'
```

## Response Format

The API returns a paginated response with the following structure:

```json
{
  "content": [
    {
      "id": "student-id",
      "name": "Student Name",
      "email": "student@example.com",
      "phone": "+1234567890",
      "department": "Computer Science",
      "city": "New York",
      "state": "NY",
      "country": "USA",
      "dob": "1995-05-15"
    }
  ],
  "pageable": {
    "sort": {
      "sorted": false,
      "unsorted": true
    },
    "pageNumber": 0,
    "pageSize": 10,
    "offset": 0,
    "unpaged": false,
    "paged": true
  },
  "totalElements": 100,
  "totalPages": 10,
  "last": false,
  "first": true,
  "numberOfElements": 10,
  "size": 10,
  "number": 0,
  "sort": {
    "sorted": false,
    "unsorted": true
  }
}
```

## Error Handling

### Invalid Date Format
If an invalid date format is provided, the API will return an error:

```json
{
  "error": "Invalid date format. Use yyyy-MM-dd"
}
```

### Valid Date Format
All date fields must follow the `yyyy-MM-dd` format:
- ✅ Valid: `2023-12-25`, `1995-01-01`, `2000-02-29`
- ❌ Invalid: `25-12-2023`, `2023/12/25`, `Dec 25, 2023`

## Notes

- All filters are combined using AND logic
- The `name` filter is case-insensitive and supports partial matching
- The `country` filter support multiple value as comma separated list
- All other text filters require exact matches
- Date filters support both exact matching and range queries
- Empty filter objects are allowed and will return all students
- Pagination is 0-based (first page is page 0)
