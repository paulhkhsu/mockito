package com.javacodegeeks.mockito;

public class Customer {
	public void eat(Dish dish) throws WrongDishException {
		try {
			System.out.println("Taste the food");
			if (dish.getSpice() == null) {
				dish.eat();
			} else {
				dish.eat(dish.getSpice());
			}
			System.out.println("Ate the food");
		} catch (WrongDishException e) {
			System.out.println("Wrong dish!");
			throw e;
		} catch (NotSoTastyException e) {
			System.out.println("Not very tasty");
			throw e;
		}		
	}
}
