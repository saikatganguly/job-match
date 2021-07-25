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

import java.util.Comparator;
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
        Predicate<Job> positiveScorePredicate = j -> j.getScore()>0;

        List<Job> jobs =  jobRepository.nearByJobs(worker.getJobSearchAddress().getLongitude() ,worker.getJobSearchAddress().getLatitude() ,
                                                    radian ,worker.getSkills().toArray(new String[0]) , certificates , sort);

        if(jobs.size()==0){
            jobs = jobRepository.findJobsBySkillsAndCertificate(worker.getSkills().toArray(new String[0]) , certificates , sort);
            jobs.sort(getComparator(worker));
        }
        jobs = jobs.stream().filter(licensePredicate).filter(positiveScorePredicate).limit(3).collect(Collectors.toList());
        return jobs;

    }

    private Comparator getComparator(Worker worker){
        Comparator<Job> jobsComparator
                = Comparator.comparing(
                Job::getLocation, (l1, l2) -> {
                    Double job2Distance = distance(worker.getJobSearchAddress().getLatitude() , worker.getJobSearchAddress().getLongitude() , l2.getLatitude() , l2.getLongitude());
                    Double job1Distance = distance(worker.getJobSearchAddress().getLatitude() , worker.getJobSearchAddress().getLongitude() , l1.getLatitude() , l1.getLongitude());
                    return job1Distance.compareTo(job2Distance);
                })
                .thenComparing(Job::getScore , Comparator.reverseOrder());
        return jobsComparator;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            return dist;
        }
    }
}
