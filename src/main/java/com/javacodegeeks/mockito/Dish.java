package com.javacodegeeks.mockito;

public interface Dish {
	void eat() throws WrongDishException;
	void eat(String spicy) throws WrongDishException;
	String getSpice();
}
