package org.swipejob.test.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.swipejob.test.models.Job;
import org.swipejob.test.models.Worker;
import org.swipejob.test.repository.JobRepository;
import org.swipejob.test.repository.WorkerRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Component
public class DataLoader {

    private static Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @PostConstruct
    public void loadData() throws IOException {
        loadJobs();
        loadWorkers();
    }

    private void loadJobs() throws IOException {
        logger.info("Loading job collection");
        ResponseEntity<List<Job>> responseEntity =
                restTemplate.exchange(
                        "https://test.swipejobs.com/api/jobs",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Job>>() {}
                );
        jobRepository.save(responseEntity.getBody());
    }

    private void loadWorkers() throws IOException {
        logger.info("Loading worker collection");
        ResponseEntity<List<Worker>> responseEntity =
                restTemplate.exchange(
                        "https://test.swipejobs.com/api/workers",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Worker>>() {}
                );

        workerRepository.save(responseEntity.getBody());
    }



}
