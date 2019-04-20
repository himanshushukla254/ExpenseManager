package com.tech2hire.ExpenseManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech2hire.ExpenseManager.entities.UserExpense;

@Repository
public interface UserExpenseRepository extends JpaRepository<UserExpense, Long> {

	List<UserExpense> findByUserId(Long userId);
	
}
