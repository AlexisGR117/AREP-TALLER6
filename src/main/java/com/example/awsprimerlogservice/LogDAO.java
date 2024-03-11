package com.example.awsprimerlogservice;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Esta clase proporciona métodos para agregar y listar registros en la base de datos de MongoDB.
 * La clase utiliza la colección "logs" de la base de datos para almacenar los registros.
 *
 * @author Jefer Alexis Gonzalez Romero
 * @version 1.0 (10/03/2023)
 */
public class LogDAO {

    private final MongoCollection<Document> logsCollection;

    /**
     * Crea una nueva instancia de la clase `LogDAO` con la colección de registros especificada.
     *
     * @param database La base de datos de MongoDB que contiene la colección de registros.
     */
    public LogDAO(MongoDatabase database) {
        this.logsCollection = database.getCollection("logs");
    }

    /**
     * Agrega un nuevo registro a la base de datos de MongoDB.
     *
     * @param date La fecha y hora del registro.
     * @param description La descripción del registro.
     */
    public void addLog(String date, String description) {
        Document newLog = new Document("date", date)
                .append("description", description);
        logsCollection.insertOne(newLog);
    }

    /**
     * Lista los últimos 10 registros de la base de datos de MongoDB en formato JSON.
     *
     * @return Los últimos 10 registros en formato JSON.
     */
    public String listLogs() {
        FindIterable<Document> logs = logsCollection.find()
                .sort(new BasicDBObject("date", -1))
                .limit(10);
        return logsToJSON(logs);
    }

    /**
     * Convierte una lista de documentos de MongoDB en una cadena JSON.
     *
     * @param logs La lista de documentos de MongoDB.
     * @return La cadena JSON que representa la lista de documentos.
     */
    public static String logsToJSON(FindIterable<Document> logs) {
        StringBuilder logsStringBuilder = new StringBuilder();
        for (Document document : logs) {
            logsStringBuilder.append(document.toJson());
            logsStringBuilder.append(",\n");
        }
        return "{\"logs\": [" + logsStringBuilder.substring(0, logsStringBuilder.length() - 2) + "]}";
    }
}
