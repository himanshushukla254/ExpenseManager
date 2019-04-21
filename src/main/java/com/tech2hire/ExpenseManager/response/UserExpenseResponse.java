package com.tech2hire.ExpenseManager.response;

import com.tech2hire.ExpenseManager.enums.ExpenseType;

public class UserExpenseResponse {
	private Double expenseAmount;

	private ExpenseType expenseType;

	/**
	 * @return the expenseAmount
	 */
	public Double getExpenseAmount() {
		return expenseAmount;
	}

	/**
	 * @param expenseAmount
	 *            the expenseAmount to set
	 */
	public void setExpenseAmount(Double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	/**
	 * @return the expenseType
	 */
	public ExpenseType getExpenseType() {
		return expenseType;
	}

	/**
	 * @param expenseType
	 *            the expenseType to set
	 */
	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}
}
