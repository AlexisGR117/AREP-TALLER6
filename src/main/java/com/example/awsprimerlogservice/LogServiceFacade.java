package com.example.awsprimerlogservice;

import static spark.Spark.*;

/**

 * Esta clase proporciona una fachada para el servicio de registro, que distribuye las solicitudes de registro entre varios servicios de registro remotos.
 * La clase utiliza la lista de URLs de los servicios de registro proporcionados en el entorno y crea un objeto `RemoteLogServiceInvoker` para invocar los servicios de registro.
 *
 * @author Jefer Alexis Gonzalez Romero
 * @version 1.0 (10/03/2023)
 */
public class LogServiceFacade {

    /**
     * Punto de entrada de la aplicación.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        String[] logServices = getLogServicesURLS(System.getenv("LOG_SERVICES").split(";"));
        RemoteLogServiceInvoker invoker = new RemoteLogServiceInvoker(logServices);
        staticFiles.location("/public");
        port(getPort());
        get("/logservicefacade", (req, res) -> {
            res.type("application/json");
            System.out.println(req.queryParams("msg"));
            return invoker.invoke(req.queryParams("msg").replace(" ", "%20"));
        });
    }

    /**
     * Obtiene el puerto en el que se ejecutará el servidor web local.
     *
     * @return El puerto en el que se ejecutará el servidor web local.
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) return Integer.parseInt(System.getenv("PORT"));
        return 4567;
    }

    /**
     * Obtiene las URLs de los servicios de registro remotos.
     *
     * @param logServices Lista de nombres de los servicios de registro remotos.
     * @return Las URLs de los servicios de registro remotos.
     */
    public static String[] getLogServicesURLS(String[] logServices) {
        String[] logServicesURLS = new String[logServices.length];
        for (int i = 0; i < logServicesURLS.length; i++) {
            logServicesURLS[i] = "http://" + logServices[i] + ":35000/logservice?msg=";
        }
        return logServicesURLS;
    }
}
