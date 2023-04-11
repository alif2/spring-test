package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests use the real implementation of all the code and dependencies, like the database. We should not mock
 * any classes here. But, these tests will be slower because they must wait for the database and possibly network
 * latency. So, integration tests should be fewer than unit tests.
 */

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class DemoApplicationIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void validPersonIsReturnedIntegration() throws Exception {
        var id = 1;
        var result = mockMvc.perform(get("/demo/person/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

        var response = result.getResponse().getContentAsString();

        var objectMapper = new ObjectMapper();
        var person = objectMapper.readValue(response, Person.class);

        /*
        You may notice that all we can reasonably assert is that we get some kind of data back and that it fits our
        basic structure. This is typically the case with integration tests and is okay, because unit tests validate
        the concerns about our code handling the different kinds of possible responses, which leaves integration tests
        to handle more general cases to verify we can make the connection with the database and get data we expect.
        Unit and integration tests are not mutually exclusive and a good test environment uses both.
         */
        assertNotNull(person);
        assertTrue(person.getId() > 0);
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
    }
}
