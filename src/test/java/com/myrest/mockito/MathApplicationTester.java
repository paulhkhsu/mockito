package com.myrest.mockito;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

// @RunWith attaches a runner with the test class to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class MathApplicationTester {

	// @InjectMocks annotation is used to create and inject the mock object
	@InjectMocks
	MathApplication mathApplication ;

	// @Mock annotation is used to create the mock object to be injected
	@Mock
	CalculatorService calcService;

	@Before
	public void initHere() {
		// global rule here
		// add the behavior of calc service to add two numbers
		//when(calcService.add(10.0, 20.0)).thenReturn(30.00);
		// add the behavior of calc service to subtract two numbers
		//when(calcService.subtract(20.0, 10.0)).thenReturn(10.00);
	}

	@Test
	public void testAdd() {
		 when(calcService.add(10.0, 20.0)).thenReturn(30.00);
System.out.println(mathApplication.add(10.0, 20.0));
		// test the add functionality
		Assert.assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);
	}

	@Test
	public void testVerifyingCall() {
		 when(calcService.add(10.0, 20.0)).thenReturn(30.00);

		// test the add functionality
		Assert.assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);
		// verify the behavior
		verify(calcService).add(10.0, 20.0);
		// verify(calcService).add(10.1, 20.0); // bad
	}

	@Test
	public void testExpectingCalls() {
		 when(calcService.add(10.0, 20.0)).thenReturn(30.00);

		Assert.assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);
		// limit the method call to 1, no less and no more calls are allowed
		verify(calcService, times(1)).add(10.0, 20.0);
	}

	@Test
	public void testVaryingCalls() {
		 when(calcService.add(10.0, 20.0)).thenReturn(30.00);
			when(calcService.subtract(20.0, 10.0)).thenReturn(10.00);

		// test the add functionality
		Assert.assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);
		Assert.assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);
		Assert.assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);

		// test the subtract functionality
		Assert.assertEquals(mathApplication.subtract(20.0, 10.0), 10.0, 0.0);

		// check a minimum 1 call count
		verify(calcService, atLeastOnce()).subtract(20.0, 10.0);

		// check if add function is called minimum 2 times
		verify(calcService, atLeast(2)).add(10.0, 20.0);

		// check if add function is called maximum 3 times
		verify(calcService, atMost(3)).add(10.0, 20.0);
	}

	@Test(expected = RuntimeException.class)
	public void testException() {
		// add the behavior to throw exception
		doThrow(new RuntimeException("Add operation not implemented")).when(
				calcService).add(40.0, 20.0);

		// test the add functionality and return RuntimeException,class same
		// @Test
		Assert.assertEquals(mathApplication.add(40.0, 20.0), 60.0, 0);
		// try this not return RuntimeException,class
		// Assert.assertEquals(mathApplication.add(20.0, 20.0), 60.0, 0);
	}

	@Test
	public void testAddAndSubtract() {

		// add the behavior to add numbers
		when(calcService.add(20.0, 10.0)).thenReturn(30.0);

		// subtract the behavior to subtract numbers
		when(calcService.subtract(20.0, 10.0)).thenReturn(10.0);

		// test the add functionality
		Assert.assertEquals(mathApplication.add(20.0, 10.0), 30.0, 0);

		// test the subtract functionality
		Assert.assertEquals(mathApplication.subtract(20.0, 10.0), 10.0, 0);

		// create an inOrder verifier for a single mock
		InOrder inOrder = inOrder(calcService);

		// following will make sure that add is first called then subtract is
		// called.
		// inOrder.verify(calcService).subtract(20.0, 10.0);
		inOrder.verify(calcService).add(20.0, 10.0);
		inOrder.verify(calcService).subtract(20.0, 10.0);
	}

	@Test
	public void testReset() {

		// add the behavior to add numbers
		when(calcService.add(20.0, 10.0)).thenReturn(30.0);
		System.out.println("testReset before " + mathApplication.add(20.0, 10.0));

		// test the add functionality
		Assert.assertEquals(mathApplication.add(20.0, 10.0), 30.0, 0);

		// reset the mock
		reset(calcService);
		System.out.println("testReset after reset " + mathApplication.add(20.0, 10.0));

		// test the add functionality after resetting the mock
		//Assert.assertEquals(mathApplication.add(20.0, 10.0), 30.0, 0);
		Assert.assertEquals(mathApplication.add(20.0, 10.0), 0, 0);
	}

	@Test
	public void testAddAndSubtractTimeout() {

		// add the behavior to add numbers
		when(calcService.add(20.0, 10.0)).thenReturn(30.0);

		// subtract the behavior to subtract numbers
		when(calcService.subtract(20.0, 10.0)).thenReturn(10.0);

		// test the subtract functionality
		Assert.assertEquals(mathApplication.subtract(20.0, 10.0), 10.0, 0);

		// test the add functionality
		Assert.assertEquals(mathApplication.add(20.0, 10.0), 30.0, 0);
		System.out.println("testAddAndSubtractTimeout " + mathApplication.add(20.0, 10.0));

		// verify call to add method to be completed within 100 ms
		verify(calcService, timeout(100)).add(20.0, 10.0);

		// invocation count can be added to ensure multiplication invocations
		// can be checked within given timeframe
		verify(calcService, timeout(100).times(1)).subtract(20.0, 10.0);
	}

	@Test
	public void iterator_will_return_hello_world() {
		// arrange
		Iterator i = (Iterator) mock(Iterator.class);
		when(i.next()).thenReturn("Hello").thenReturn("World");
		// act
		String result = i.next() + " " + i.next();
		System.out.println("iterator_will_return_hello_world " + result);
		// assert
		assertEquals("Hello World", result);
	}

}