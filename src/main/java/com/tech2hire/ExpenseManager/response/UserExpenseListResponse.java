package com.tech2hire.ExpenseManager.response;

import java.util.List;

public class UserExpenseListResponse {

	List<UserExpenseResponse> userExpenseResponses;
	
	String message;

	/**
	 * @return the userExpenseResponses
	 */
	public List<UserExpenseResponse> getUserExpenseResponses() {
		return userExpenseResponses;
	}

	/**
	 * @param userExpenseResponses the userExpenseResponses to set
	 */
	public void setUserExpenseResponses(List<UserExpenseResponse> userExpenseResponses) {
		this.userExpenseResponses = userExpenseResponses;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
