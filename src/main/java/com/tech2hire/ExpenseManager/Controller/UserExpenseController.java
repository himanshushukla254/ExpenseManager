package com.tech2hire.ExpenseManager.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.tech2hire.ExpenseManager.response.UserExpenseListResponse;
import com.tech2hire.ExpenseManager.response.UserExpenseResponse;

@Controller
public class UserExpenseController {

	@Autowired
	UserExpenseRepository userExpenseRepository;

	@PostMapping(value = "/v1/add_user_expenses/{userId}", consumes = "application/json", produces = "text/plain")
	public @ResponseBody String addUserExpenses(@PathVariable Long userId,
			@RequestBody UserExpenseAddRequest userExpenseAddRequest) {
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

	@GetMapping(value = "/v1/fetch_expenses_for_chart/{userId}/{startDate}/{endDate}/{expendType}", produces = "application/json")
	public @ResponseBody List<UserExpense> addUserExpenses(@PathVariable Long userId, @PathVariable String startDate,
			@PathVariable String endDate, @PathVariable String expendType) throws ParseException {
		Date startAt = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
		Date endAt = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
		ExpenseType expenseType = ExpenseType.getValue(expendType);
		List<UserExpense> userExpenses = userExpenseRepository.fetchByFilters(userId, startAt, endAt, expenseType);
		return userExpenses;
	}

	@GetMapping(value = "/v1/fetch_trends/{userId}/{expendType}/{expendAmount}/{totalAmountSpent}", produces = "application/json")
	public @ResponseBody UserExpenseListResponse fetchTrendsForUsers(@PathVariable Long userId,
			@PathVariable String expendType, @PathVariable Double expendAmount, @PathVariable Double totalAmountSpent) {
		List<UserExpense> userExpenses = userExpenseRepository.findByUserId(userId);
		List<UserExpenseResponse> userExpenseResponses = new ArrayList<>();
		UserExpenseListResponse userExpenseListResponse = new UserExpenseListResponse();
		Map<ExpenseType, List<Double>> userExpenseMapPerType = new HashMap<>();
		Map<ExpenseType, Double> avgExpense = new HashMap<>();
		for (UserExpense userExpense : userExpenses) {
			if (userExpenseMapPerType.get(userExpense.getExpenseType()) != null) {
				List<Double> tempList = userExpenseMapPerType.get(userExpense.getExpenseType());
				tempList.add(userExpense.getExpenseAmount());
			} else {
				List<Double> tempList = new ArrayList<>();
				tempList.add(userExpense.getExpenseAmount());
				userExpenseMapPerType.put(userExpense.getExpenseType(), tempList);
			}
		}
		for (Map.Entry<ExpenseType, List<Double>> entry : userExpenseMapPerType.entrySet()) {
			avgExpense.put(entry.getKey(), getAverage(entry.getValue()));
		}
		for (Map.Entry<ExpenseType, Double> entry : avgExpense.entrySet()) {
			UserExpenseResponse uer = new UserExpenseResponse();
			uer.setExpenseAmount(entry.getValue());
			uer.setExpenseType(entry.getKey());
			userExpenseResponses.add(uer);
		}
		userExpenseListResponse.setUserExpenseResponses(userExpenseResponses);
		userExpenseListResponse.setMessage(getMessageForUser(avgExpense, expendType, expendAmount, totalAmountSpent));
		return userExpenseListResponse;
	}

	private String getMessageForUser(Map<ExpenseType, Double> avgExpense, String expendType, Double expendAmount,
			Double totalAmountSpent) {
		String message = "By Adding " + expendAmount + " to " + expendType + "\n" +"You can reduce your expenses from";
		for (Map.Entry<ExpenseType, Double> entry : avgExpense.entrySet()) {
			message = message
					.concat("*" + entry.getKey() + " by "
							+ String.format("%.2f", entry.getValue()) + " for next "
							+ String.valueOf((int) ((expendAmount - (9000 - totalAmountSpent)) / entry.getValue())))
					+ " times \n";
		}
		return message;
	}

	private Double getAverage(List<Double> value) {
		Double sum = 0.0;
		Double avg = 0.0;
		for (Double d : value)
			sum += d;
		avg = sum / value.size();
		return avg;
	}

	@GetMapping(value = "/")
	public String index() {
		return "index.html";
	}
}
