package com.tech2hire.ExpenseManager.enums;

public enum ExpenseType {
	shopping,food,dining,entertainment,misc;

	public static ExpenseType getValue(String expenseType) {
		switch(expenseType) {
			case "shopping":
				return shopping;
			case "food":
				return food;
			case "dining":
				return dining;
			case "entertainment":
				return entertainment;
			default:
				return misc;
		}
	}
}
