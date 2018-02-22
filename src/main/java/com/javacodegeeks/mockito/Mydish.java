package com.javacodegeeks.mockito;

class Mydish implements Dish {

	@Override
	public void eat() throws WrongDishException {
		// TODO Auto-generated method stub
		int i = 0;
		throw new WrongDishException();
	}

	@Override
	public void eat(String spicy) throws WrongDishException {
		// TODO Auto-generated method stub
		int i = 0;

	}

	@Override
	public String getSpice() {
		int i = 0;
		// TODO Auto-generated method stub
		return null;
	}

}