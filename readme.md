# Email Scheduler in Springboot.
 
Email Scheduling application with spring boot and mysql as a database. It uses quartz scheduler internally for scheduling the custom jobs. 

This application involves CRUD operations involving the scheduling operations. 

# Blog with detailed steps to create the email scheduler application in spring boot with MySQL

Link - [Quartz Scheduler Email Scheduling application in Spring boot with MySQL](https://www.pranaybathini.com/quartz-scheduler-email-scheduling)

# Blog on adding Postgres as database to existing application(Pluggable Dao layer)

Link - [Email Scheduler Application with Quartz scheduler and PostgreSQL](https://www.pranaybathini.com/email-scheduler-application-with-quartz)

# Running the application

```
 mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=<env> --spring.mail.password=<YOUR_SMTP_PASSWORD>"                         
```

# Swagger Link

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

# Postman Collection

Import from here - [Post man Collection](https://github.com/pranaybathini/quartz-scheduler/blob/main/Quartz-Scheduler.postman_collection.json)

# Run application with MySQL as database

```
 mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=local --spring.mail.password=<YOUR_SMTP_PASSWORD>"                         
```

# Run application with PostgreSQL as database

```
 mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=local-postgres --spring.mail.password=<YOUR_SMTP_PASSWORD>" 
 
 
```

# Dockerizing the application

Link - [Dockerizing a spring boot application](https://www.pranaybathini.com/dockerizing-spring-boot-application)

# HashiCorp Vault Configuration

Most of the secrets are written in plain text and available in code. This is prone to attacks. Configure a secret manager to store secrets and our application 
should access them from the secret manager securely.

Link - [Fetching secrets from Hashicorp vault in spring boot.](https://www.pranaybathini.com/hashicorp-vault-config-in-spring-bootFetching secrets from Hashicorp vault in spring boot.)
