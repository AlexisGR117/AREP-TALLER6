package com.example.awsprimerlogservice;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class LogService {

    public static void main(String... args){
        MongoDatabase database = ConexionMongoDB.conectar();
        MongoCollection<Document> collection = database.getCollection("registro");
        database.drop();
        Document document = new Document();
        document.put("Fecha", "A");
        document.put("Cadena", "Registro 1");
        collection.insertOne(document);
        Document document2 = new Document();
        document2.put("Fecha", "C");
        document2.put("Cadena", "Registro 2");
        collection.insertOne(document2);
        Document document3 = new Document();
        document3.put("Fecha", "B");
        document3.put("Cadena", "Registro 3");
        collection.insertOne(document3);
        port(5000);
        // get("/guardar", (req,res) -> webClient());
        get("/logservice", (req,res) -> {
            res.type("application/json");
            return "{\"logid1\":\"20-2-2024-Log Inicial\"}";
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

    public static String getLogs() {
        MongoDatabase database = ConexionMongoDB.conectar();
        MongoCollection<Document> collection = database.getCollection("registro");

        // Ordenar por fecha (descendente) y limitar a 10 registros:
        List<Document> documents = collection.find().sort(new Document("fecha", -1)).limit(10).into(new ArrayList<>());

        // Convertir la lista de documentos a un formato de cadena adecuado:
        StringBuilder logsStringBuilder = new StringBuilder();
        for (Document document : documents) {
            logsStringBuilder.append(document.toJson()); // O usar otro formato de serialización deseado
            logsStringBuilder.append("\n"); // Agregar un salto de línea entre documentos
        }

        // Devolver la cadena con los logs:
        return logsStringBuilder.toString();
    }
}
