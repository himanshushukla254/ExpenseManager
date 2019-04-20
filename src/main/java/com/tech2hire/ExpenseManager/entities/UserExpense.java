package com.tech2hire.ExpenseManager.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tech2hire.ExpenseManager.enums.ExpenseType;

@Table(name = "USER_EXPENSE")
@Entity
public class UserExpense {
	@Id
	@GeneratedValue
	@Column(name = "USER_EXPENSE_ID")
	private Long expenseId;

	@Enumerated(EnumType.STRING)
	@Column(name = "USER_EXPENSE_TYPE", columnDefinition = "enum('shopping','food','dining','entertainment','misc')")
	private ExpenseType expenseType;

	@Column(name = "EXPENSE_DATE")
	private Date expenseDate;

	@Column(name = "EXPENSE_AMT")
	private Double expenseAmount;

	@Column(name = "USER_ID")
	private Long userId;
	
	
	/**
	 * @return the expenseId
	 */
	public Long getExpenseId() {
		return expenseId;
	}

	/**
	 * @param expenseId the expenseId to set
	 */
	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}

	/**
	 * @return the expenseType
	 */
	public ExpenseType getExpenseType() {
		return expenseType;
	}

	/**
	 * @param expenseType the expenseType to set
	 */
	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}

	/**
	 * @return the expenseDate
	 */
	public Date getExpenseDate() {
		return expenseDate;
	}

	/**
	 * @param expenseDate the expenseDate to set
	 */
	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}

	/**
	 * @return the expenseAmount
	 */
	public Double getExpenseAmount() {
		return expenseAmount;
	}

	/**
	 * @param expenseAmount the expenseAmount to set
	 */
	public void setExpenseAmount(Double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
}
