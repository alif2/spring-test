package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * What should go in the service (middle) layer? Typically, everything that isn't handled by the controller or repository
 * layer, which is to say, business logic. Business logic is a confusing term, but it essentially means all code that
 * not related to passing data to the UI or handling data storage in the database. This code is what tests often
 * isolate, but it is not good practice to do so. This logic should be tested through the calling classes and not in
 * isolation. This is because we should be able to change the business logic without changing the tests, as long as the
 * resulting output is the same.
 */

@Service
public class DemoService {
    private final DemoDatabaseWrapper database;

    @Autowired
    public DemoService(@Qualifier("demoDatabase") DemoDatabaseWrapper database) {
        this.database = database;
    }

    public Person getPersonById(int id) {
        /*
         * The tests in this project assume there is some logic here, but what about cases where there is not?
         * If the entire stack is a pass through to the database and there is no logic, then a unit test that mocks
         * the database wrapper will be essentially just testing return statements. In this case, do not write a unit
         * test and instead, write an integration test. One is probably sufficient, just to verify the ability to return
         * data.
         */
        return database.getPersonById(id);
    }
}
