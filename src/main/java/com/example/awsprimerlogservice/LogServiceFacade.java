package com.example.awsprimerlogservice;

import static spark.Spark.staticFiles;
import static spark.Spark.get;
public class LogServiceFacade {

    private static final String LOG_SERVICE_URL = "http://localhost:5000/logservice";

    public static void main(String[] args) {
        RemoteLogServiceInvoker invoker = new RemoteLogServiceInvoker(LOG_SERVICE_URL);
        staticFiles.location("/public");
        get("/logservicefacade", (req, res) -> {
            res.type("application/json");
            return invoker.invoke(args);
        });
    }
}
