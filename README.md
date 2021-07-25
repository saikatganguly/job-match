# Job-Match

Spring boot application to match Jobs against Worker

## How to run
run the application with mvn spring-boot:run

```bash
mvn spring-boot:run
```
#### MongoConfig.java Class
Connects to local (127.0.0.1) mongoDB and database swipejob.

#### DataLoader.java Class
Calls [Worker](http://test.swipejobs.com/api/workers) and [Job](http://test.swipejobs.com/api/jobs) endpoints to fetch data and stores it in respective collections to perform query.

It also implements WriterConverter and ReaderConverter. WriterConverter is needed to modify location data as well as multilingual document. ReaderConvert converts document to Job model class.

## Create Index
Created textIndex using 
```$xslt
db.job.createIndex( { requiredCertificates: "text" } )
```

## Rest URL to fetch jobs
http://localhost:8088/matches/{workerId}

## Brief of the implementation
Get worker based on the ID. Searched in the mongodb : 

-> Text based search based on worker certificates 

-> Job title filtering based on Worker skills

-> geolocation search based on Worker jobSearchAddress