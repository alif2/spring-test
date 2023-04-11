package com.example.demo;

import org.springframework.stereotype.Repository;

/**
 * @see DemoDatabase
 */
@Repository
public interface DemoDatabaseWrapper {
    Person getPersonById(int id);
}
