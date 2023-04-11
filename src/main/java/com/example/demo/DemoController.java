package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {
    /**
     * What are the responsibilities of a controller?
     * Controllers should handle input / output. They can do input validation and output formatting,
     * but should otherwise forward requests to the middle layer where the main business logic resides.
     */

    private final DemoService service;

    /**
     * Autowiring in Spring is best done on the constructor. This lets us instantiate it in our unit tests for easy use.
     */
    @Autowired
    public DemoController(DemoService service) {
        this.service = service;
    }

    @GetMapping(path = "person/{id}")
    public Person getPerson(@PathVariable int id) {
        /*
         * It's good practice to always validate input for any API endpoints, even if we also do so client-side.
         * But, we don't need to validate at all levels, since we should be rejecting bad user input here.
         * If we ensure we don't pass bad values, such as null, ourselves, we avoid having to check for them.
         */
         if(id < 1) {
            throw new IllegalArgumentException("IDs must be greater than 0.");
        }

        return service.getPersonById(id);
    }
}
