package org.swipejob.test.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.swipejob.test.models.Job;
import org.swipejob.test.models.Worker;
import org.swipejob.test.repository.WorkerRepository;
import org.swipejob.test.service.JobService;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;


@RestController
@RequestMapping("/job")
public class JobController {
    private static Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private WorkerRepository repository;

    @Autowired
    private JobService service;

    @GetMapping(value = "/find/{workerId}")
    public ResponseEntity<List<Job>> findJobsByWorkerId(@PathVariable int workerId){
        logger.info("Serching worker by id : {}", workerId);
        Worker worker = repository.findOne(workerId);
        if(worker==null)
            return ResponseEntity.badRequest().body(Collections.emptyList());
        logger.debug("Worker found : {}", worker);
        Function<Worker , List> func = service::findJobs;
        return new ResponseEntity(func.apply(worker) , HttpStatus.OK);
    }
}
