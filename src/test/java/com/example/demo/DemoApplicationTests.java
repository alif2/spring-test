package com.example.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

/**
 * It's important that we use the concrete implementation of all of our code and only mock on the boundary layer,
 * which is this case is the database wrapper. This method allows us to test the behavior, the foo in, bar out,
 * black box while also allowing the implementation to change as long as the behavior does not.
 */

@SpringBootTest
class DemoApplicationTests {

	private DemoController controller;
	private AutoCloseable closeable;

	@Mock
	private DemoDatabaseWrapper database;

	@BeforeEach
	void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
		controller = new DemoController(new DemoService(database));
	}

	@AfterEach
	void tearDown() throws Exception {
		closeable.close();
	}

	/**
	 * This test checks our validation. It's important to test valid and invalid requests.
	 */
	@Test
	void negativePersonIdShouldThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> controller.getPerson(-1));
	}

	/**
	 * What are we testing for here? Our goal with unit tests is to test that our code correctly responds to the input
	 * it's given, which could be on both sides. But, you may notice that this call stack is nothing but a pass-through
	 * to the database. This example is still a simplified version, but in a real application we would assume that there
	 * is more business logic that is not being represented here.
	 *
	 * If this is not the case and the real-world application really is just a pass-through then there is no code of
	 * ours that needs isolating at all. In that case, an integration test is the solution.
	 */
	@Test
	void validPersonIsReturned() {
		// Given
		Person p = new Person();
		p.setId(1);

		given(database.getPersonById(anyInt())).willReturn(p);

		// When
		var person = controller.getPerson(1);

		// Then
		assertEquals(1, person.getId());
	}
}
