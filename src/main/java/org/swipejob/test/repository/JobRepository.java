package org.swipejob.test.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.swipejob.test.models.Job;

import java.util.List;

@Repository
public interface JobRepository extends MongoRepository<Job, Integer> {

    @Query(value= "{" +
            "    \"$and\": [" +
            "    {\"$text\": { \"$search\": ?4 }}," +
            "    {\"jobTitle\": {\"$in\": ?3}}," +
            "    {\"loc\": {" +
            "        \"$geoWithin\": {" +
            "            \"$centerSphere\": [[" +
            "               ?0," +
            "               ?1" +
            "            ], ?2 ]" +
            "        }" +
            "    }}" +
            "]}," +
            "{\"score\": { \"$meta\": \"textScore\" }}" +
            ").sort( { score: { $meta: \"textScore\" } } )")

    List<Job> nearByJobs(double longitude, double latitude , double distance , String[] skills , String certificates , Sort sort);

    @Query(value= "{" +
            "    \"$and\": [" +
            "    {\"$text\": { \"$search\": ?1 }}," +
            "    {\"jobTitle\": {\"$in\": ?0}}" +
            "]}," +
            "{\"score\": { \"$meta\": \"textScore\" }}" +
            ").sort( { score: { $meta: \"textScore\" } } )")
    List<Job> findJobsBySkillsAndCertificate(String[] skills, String certificates, Sort sort);
}
