package com.example.awsprimerlogservice;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * Esta clase proporciona métodos estáticos para interactuar con una base de datos de MongoDB.
 * La clase crea una conexión a la base de datos utilizando la cadena de conexión y el nombre de la base de datos especificados.
 *
 * @author Jefer Alexis Gonzalez Romero
 * @version 1.0 (10/03/2023)
 */
public class MongoUtil {
    private static final String CONNECTION_STRING = "mongodb://MongoDB:27017";
    private static final String DATABASE_NAME = "Taller6DB";

    /**
     * Obtiene una conexión a la base de datos de MongoDB.
     *
     * @return Una conexión a la base de datos de MongoDB.
     */
    public static MongoDatabase getDB() {
        MongoClient client = MongoClients.create(CONNECTION_STRING);
        return client.getDatabase(DATABASE_NAME);
    }
}
