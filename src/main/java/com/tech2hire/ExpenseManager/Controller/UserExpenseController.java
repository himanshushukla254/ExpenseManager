package com.tech2hire.ExpenseManager.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tech2hire.ExpenseManager.entities.UserExpense;
import com.tech2hire.ExpenseManager.enums.ExpenseType;
import com.tech2hire.ExpenseManager.repository.UserExpenseRepository;
import com.tech2hire.ExpenseManager.requests.UserExpenseAddRequest;

@Controller
public class UserExpenseController {

	@Autowired
	UserExpenseRepository userExpenseRepository;

	@PostMapping(value = "/v1/add_user_expenses/{userId}", consumes = "application/json", produces = "text/plain")
	public @ResponseBody String addUserExpenses(@PathVariable Long userId, @RequestBody UserExpenseAddRequest userExpenseAddRequest) {
		UserExpense userExpense = new UserExpense();
		userExpense.setExpenseAmount(userExpenseAddRequest.getExpenseAmount());
		userExpense.setExpenseType(ExpenseType.getValue(userExpenseAddRequest.getExpenseType()));
		userExpense.setExpenseDate(
				userExpenseAddRequest.getExpenseDate() != null ? userExpenseAddRequest.getExpenseDate() : new Date());
		userExpense.setUserId(userId);
		userExpenseRepository.save(userExpense);
		return "Expense Added Successfully";
	}

	@GetMapping(value = "/v1/fetch_expenses/{userId}", produces = "application/json")
	public @ResponseBody List<UserExpense> addUserExpenses(@PathVariable Long userId) {
		List<UserExpense> userExpenses = userExpenseRepository.findByUserId(userId);
		return userExpenses;
	}

	@GetMapping(value = "/")
	public String index() {
		return "index.html";
	}
}
