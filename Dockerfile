FROM openjdk:8-jdk-alpine

COPY target/EmployeeManagement-0.0.1-SNAPSHOT.jar  employeemanagement.jar

EXPOSE 8035

ENTRYPOINT ["java","-jar","/employeemanagement.jar"]
