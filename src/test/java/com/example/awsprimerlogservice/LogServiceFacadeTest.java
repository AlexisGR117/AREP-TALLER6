package com.example.awsprimerlogservice;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.bson.Document;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LogServiceFacadeTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public LogServiceFacadeTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(LogServiceFacadeTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue(true);
    }

    public void testGetLogServicesURLS() throws Exception {
        String[] logServicesNames = {"LogServiceUno", "LogServiceDos", "LogServiceTres"};
        String[] logServicesURLS = LogServiceFacade.getLogServicesURLS(logServicesNames);
        String[] expectedURLS = {"http://LogServiceUno:35000/logservice?msg=", "http://LogServiceDos:35000/logservice?msg=","http://LogServiceTres:35000/logservice?msg="};
        assertTrue(Arrays.toString(expectedURLS).equals(Arrays.toString(logServicesURLS)));
    }

    public void testLogsToJSON() {
        FindIterable<Document> logs = Mockito.mock(FindIterable.class);
        MongoCursor<Document> mongoCursor = Mockito.mock(MongoCursor.class);
        Mockito.when(mongoCursor.hasNext()).thenReturn(true, true, false);
        Mockito.when(mongoCursor.next()).thenReturn(
                new Document("_id", 1).append("message", "log 1"),
                new Document("_id", 2).append("message", "log 2")
        );
        Mockito.when(logs.iterator()).thenReturn(mongoCursor);
        String jsonResult = LogDAO.logsToJSON(logs);
        System.out.println(jsonResult);
        String expectedJson = "{\"logs\": [{\"_id\": 1, \"message\": \"log 1\"},\n{\"_id\": 2, \"message\": \"log 2\"}]}";
        System.out.println(expectedJson);
        assertEquals(expectedJson, jsonResult);
    }

}
