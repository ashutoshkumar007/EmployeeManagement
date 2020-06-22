# Employee Management System

Employee management System is used by user to manage employee records of a company. This employee management system interacts with another remote Payroll system and make sure that the two systems are always in sync.

## Installation

Pull the docker image using command

```bash
docker pull dockerashu007/employee:1.0
```

Run docker image using command

```bash
docker run -p 8035:8035 dockerashu007/employee:1.0
```

## Functionality

1. Add an employee. Add an employee locally and to the remote payroll system. If an employee with same name exists, add a number to the name.
   
 ##### Constraints: Employee name should be alphabetic, age should be more than 14 and salary should be positive

2. Search employee based on name or age: Search function makes sure employee is present in both systems before returning, else return error.

### Additional feature
1. #### Performance metrics
System metrics related to JVM, thread and http can be viewed using endpoint
```bash
localhost:8036/actuator/prometheus
```

2. #### System health check
System health check enpoint which ensures db connection also
```bash
localhost:8035/health
```

## Design
#### Add an employee

1. Employee will be saved locally first without the id. After that payroll service will be called and on successful response the id will be updated in local db and response will be sent.
2. On failure of payroll service , data will be deleted from local db for maintaining consistency.


#### Add employees in bulk

1. On receiving request for bulk add, the list of employees will be saved in a document in NoSql db with a job id and status created.
2. After saving document, an asynchronous method will be invoked with job id and  user will be responded with job id and api link to check the status of job id.
3. The asynchronous method will change the job status to in progress and then will fetch the document with job id.
4. After that it will add all employee using create method(the method used for adding single employee) one by one. If the employee is added successfully , then it will be added to success list otherwise it will be added to failed list in the same document.
5. Once all employee of a document is processed ,then the job status will be marked as completed.
6. User can get job status and failed employee list using the job id.

![alt text](https://github.com/ashutoshkumar007/EmployeeManagement/blob/master/AddBulkDesign.png)
