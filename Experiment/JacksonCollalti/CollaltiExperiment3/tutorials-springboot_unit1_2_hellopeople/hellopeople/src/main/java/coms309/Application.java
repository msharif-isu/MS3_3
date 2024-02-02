package coms309;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Sample Spring Boot Application.
 * 
 * @author Vivek Bengre
 */

@SpringBootApplication
public class Application {

    /*
    Json Test text
    {
    "firstName": "Jackson",
    "lastName": "Collalti",
    "address": "Lost",
    "telephone": "888",
    "bucketList": "Seattle",
    "budget":5000.25,
    "transportationType":"Plane",
    "groupSize":4
    }
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
