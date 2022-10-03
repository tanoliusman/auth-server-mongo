package com.oauth.authservermongo.repository;

import com.oauth.authservermongo.domain.MongoClient;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.Optional;

public class MongoClientRepository implements RegisteredClientRepository {


    private final MongoTemplate mongoTemplate;

    public MongoClientRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public void save(RegisteredClient registeredClient) {
        if(Optional.ofNullable(findById(registeredClient.getId())).isPresent()){
            return;
        }
        MongoClient mongoClient = MongoClient.convert(registeredClient);
        mongoTemplate.save(mongoClient);
    }



    @Override
    public RegisteredClient findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        MongoClient mongoClient = mongoTemplate.findOne(query, MongoClient.class);
        if(Optional.ofNullable(mongoClient).isPresent()) {
            return MongoClient.convertToRegisterClient(mongoClient);
        }
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("clientId").is(clientId));
        MongoClient mongoClient = mongoTemplate.findOne(query, MongoClient.class);
        if(Optional.ofNullable(mongoClient).isPresent()) {

            return MongoClient.convertToRegisterClient(mongoClient);
        }
        return null;
    }

}
