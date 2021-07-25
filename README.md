# Job-Match

Spring boot application to match Jobs against Worker

## How to run
run the application with mvn spring-boot:run

```bash
mvn spring-boot:run
```
#### MongoConfig Class
Connects to local (127.0.0.1) mongoDB and database swipejob.

#### DataLoader Class
Calls [Worker](http://test.swipejobs.com/api/workers) and [Job](http://test.swipejobs.com/api/jobs) endpoints to fetch data and stores it in respective collections to perform query.

## Create Index
Created textIndex using db.job.createIndex( { requiredCertificates: "text" } ).

## Rest URL to fetch jobs
http://localhost:8088/job/find/{workerId}