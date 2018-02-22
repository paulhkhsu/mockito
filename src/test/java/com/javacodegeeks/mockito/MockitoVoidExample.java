package com.javacodegeeks.mockito;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.myrest.mockito.CalculatorService;
//import org.testng.Assert;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;

@RunWith(MockitoJUnitRunner.class)
public class MockitoVoidExample {
	private Customer customer;
	private Dish dish;

	@Before
	public void setupMock() {
		customer = new Customer();
		//dish = mock(Mydish.class); // this always return no exception, must spy
		dish = spy(Mydish.class);
		when(dish.getSpice()).thenReturn(null);
	}

	//@Test
	public void testEatUsingStubVoid() throws WrongDishException {
		System.out
				.println("Train dish to not throw WrongDishException using stubVoid");
		// without the stubVoid
		//rain dish to not throw WrongDishException using stubVoid
		//Taste the food
		//Wrong dish!
		// test 2
		// with the stubVoid
		//Train dish to not throw WrongDishException using stubVoid
		//Taste the food
		//Ate the food
		///////Finished the dish, no exception thrown

		stubVoid(dish).toReturn().on().eat();
		customer.eat(dish);
		System.out.println("///////Finished the dish, no exception thrown");
	}

	//@Test
	public void testEatUsingDoNothing() throws WrongDishException {
		System.out
				.println("Train dish to not throw WrongDishException using doNothing");
		// this has same behave with stubVoid()
		doNothing().when(dish).eat();
		customer.eat(dish);
		System.out.println("Finished the dish, no exception thrown");
	}

	// @Test   (expected =NotSoTastyException.class)
	public void evaluateFood() throws WrongDishException {
		doThrow(NotSoTastyException.class).when(dish).eat();
		customer.eat(dish);
		System.out.println("Won't reach here");
	}

	// @Test(expected =RuntimeException.class)
	public void ifSpiceThrowException() throws WrongDishException {
		System.out
				.println("Train dish to not throw NotSuchATastyException when called first time and retun in subsquent calls");
		String spicy = "spicy";
		when(dish.getSpice()).thenReturn(spicy);
		doAnswer(new SpiceAnswer()).when(dish).eat(spicy);
		customer.eat(dish);

		spicy = "too spicy";
		when(dish.getSpice()).thenReturn(spicy);
		doAnswer(new SpiceAnswer()).when(dish).eat(spicy);
		customer.eat(dish);
	}

	private class SpiceAnswer implements Answer<String> {
		@Override
		public String answer(InvocationOnMock invocation) throws Throwable {
			String arg = (String) invocation.getArguments()[0];
			if ("too spicy".equals(arg)) {
				throw new RuntimeException("Spicy dish!");
			}
			return arg;
		}
	}

	// @Test
	public void eatMultipleDishes() throws WrongDishException {
		System.out
				.println("Train dish to not throw NotSuchATastyException when called first time and retun in subsquent calls");
		doThrow(NotSoTastyException.class).doNothing().when(dish).eat();
		try {
			customer.eat(dish);
			Assert.fail("allows eating, should have failed with NotSoTastyException");
		} catch (NotSoTastyException e) {
			System.out.println("Coudln't eat the dish, not very tasty");
		}
		customer.eat(dish);
		System.out.println("Finished the dish, no exception thrown");
		customer.eat(dish);
		System.out.println("Finished the dish, no exception thrown");
	}


}