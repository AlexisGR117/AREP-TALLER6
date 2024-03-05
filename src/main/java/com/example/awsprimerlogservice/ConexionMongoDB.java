package com.example.awsprimerlogservice;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionMongoDB {

    public static MongoDatabase conectar() {
        ConnectionString connectionString = new ConnectionString("mongodb://ec2-35-153-206-192.compute-1.amazonaws.com/:27017");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient.getDatabase("Taller6BD");
    }

}