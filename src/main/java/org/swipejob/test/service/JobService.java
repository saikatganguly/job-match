package org.swipejob.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.swipejob.test.models.Job;
import org.swipejob.test.models.Worker;
import org.swipejob.test.repository.JobRepository;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class JobService {

    private static Logger logger = LoggerFactory.getLogger(JobService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private JobRepository jobRepository;

    /**
     *
     * @param worker Worker object
     * @return List of jobs matching worker's location,skills,certificates
     */
    public List<Job> findJobs(Worker worker){
        logger.info("Serching jobs for workerId : {}",worker.getUserId());
        String certificates = String.join(" " , worker.getCertificates());
        Sort sort = new Sort(Sort.Direction.DESC, "score");

        //calculate max job distance. If unit is km then treat as kilometer else treat as mile. Get radian to work with geo within
        double radian= ("km".equals(worker.getJobSearchAddress().getUnit()) ? worker.getJobSearchAddress().getMaxJobDistance()*0.621371 :
               worker.getJobSearchAddress().getMaxJobDistance()) / 3963.2;

        Predicate<Job> licensePredicate = job -> (job.getDriverLicenseRequired().equals(true) ? worker.getHasDriversLicense().booleanValue():true);

        List<Job> jobs =  jobRepository.nearByJobs(worker.getJobSearchAddress().getLongitude() ,worker.getJobSearchAddress().getLatitude() , radian ,worker.getSkills().toArray(new String[0]) , certificates , sort);
        jobs = jobs.stream().filter(licensePredicate).limit(3).collect(Collectors.toList());
        return jobs;

    }
}
