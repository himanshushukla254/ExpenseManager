package com.tech2hire.ExpenseManager.requests;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class UserExpenseAddRequest {

	@NotNull
	private Double expenseAmount;

	@NotNull
	private Date expenseDate;

	@NotNull
	private String expenseType;

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
	 * @return the expenseDate
	 */
	public Date getExpenseDate() {
		return expenseDate;
	}

	/**
	 * @param expenseDate
	 *            the expenseDate to set
	 */
	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}

	/**
	 * @return the expenseType
	 */
	public String getExpenseType() {
		return expenseType;
	}

	/**
	 * @param expenseType
	 *            the expenseType to set
	 */
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

}
