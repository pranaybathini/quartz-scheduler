# Email Scheduler in Springboot.
 
Email Scheduling application with spring boot and mysql as a database. It uses quartz scheduler internally for scheduling the custom jobs. 

This application involves CRUD operations involving the scheduling operations. 

# Running the application

```
 mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=<env> --spring.mail.password=<YOUR_SMTP_PASSWORD>"                         
```

# Swagger Link

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

# Postman Collection

Import from here - [Post man Collection](https://github.com/pranaybathini/quartz-scheduler/blob/main/Quartz-Scheduler.postman_collection.json)
