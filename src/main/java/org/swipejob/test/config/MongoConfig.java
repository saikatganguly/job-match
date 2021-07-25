package org.swipejob.test.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.swipejob.test.models.Job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class MongoConfig {

    private static Logger logger = LoggerFactory.getLogger(MongoConfig.class);

    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient("127.0.0.1:27018");
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(mongo(), "swipejob");
    }

    @Bean
    public MappingMongoConverter mongoConverter() throws Exception {
        logger.debug("Mapping Converter bean created");
        MongoMappingContext mappingContext = new MongoMappingContext();
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
        MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mappingContext);
        mongoConverter.setCustomConversions(customConversions());
        return mongoConverter;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory(), mongoConverter());
    }

    @Bean
    public CustomConversions customConversions(){
        logger.debug("Loading Converters");
        List<Converter<?,?>> converters = new ArrayList<>();
        converters.add(JobWriterConverter.INSTANCE);
        converters.add(JobReaderConverter.INSTANCE);
        return new CustomConversions(converters);
    }


    @WritingConverter
    enum JobWriterConverter implements Converter<Job, DBObject> {

        INSTANCE;

        @Override
        public DBObject convert(Job job) {
            logger.debug("Converting Job object : {}", job);
            ObjectMapper oMapper = new ObjectMapper();
            DBObject dbObject = new BasicDBObject();
            dbObject.putAll(oMapper.convertValue(job, Map.class));
            dbObject.put("_id" , job.getJobId());
            dbObject.put("startDate" , job.getStartDate());
            dbObject.put("loc" ,  Document.parse("{ type: \"Point\" , coordinates: ["+job.getLocation().getLongitude()+","+job.getLocation().getLatitude()+"] }"));
            Map<String , String> map = new HashMap(){{
                put("language" , "latin");
                put("about" , job.getAbout());
            }};
            dbObject.put("translation" , map);
            dbObject.removeField("_class");
            dbObject.removeField("location");
            dbObject.removeField("score");
            dbObject.removeField("about");
            return dbObject;
        }
    }

    @ReadingConverter
    enum JobReaderConverter implements Converter<DBObject ,Job > {

        INSTANCE;

        @Override
        public Job convert(DBObject dbObject) {
            logger.debug("Converting dbObject object : {}", dbObject.toMap());
            Job job;
            ObjectMapper oMapper = new ObjectMapper();
            job = oMapper.convertValue(dbObject , Job.class);
            logger.debug("Converting DBObject to Job");
            Map locationMap = oMapper.convertValue(dbObject.get("loc") , Map.class);
            Job.Location location = job.new Location();
            BasicDBList coordinates = ((BasicDBList) locationMap.get("coordinates"));
            location.setLongitude((Double)coordinates.get(0));
            location.setLatitude((Double)coordinates.get(1));
            job.setLocation(location);
            job.setAbout(((BasicDBObject)dbObject.get("translation")).get("about").toString());
            return job;
        }
    }

}
