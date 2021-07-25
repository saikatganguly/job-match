package org.swipejob.test.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.swipejob.test.models.Worker;

@Repository
public interface WorkerRepository extends MongoRepository<Worker, Integer> {
}

