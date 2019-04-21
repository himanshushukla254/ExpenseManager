package com.tech2hire.ExpenseManager.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tech2hire.ExpenseManager.entities.UserExpense;
import com.tech2hire.ExpenseManager.enums.ExpenseType;

@Repository
public interface UserExpenseRepository extends JpaRepository<UserExpense, Long> {

	List<UserExpense> findByUserId(Long userId);

	@Query(" select e from UserExpense e where e.userId = :userId and e.expenseDate >= :startDate and e.expenseDate <= :endDate and e.expenseType =:expendType ")
	List<UserExpense> fetchByFilters(@Param("userId") Long userId, @Param("startDate") Date startAt,
			@Param("endDate") Date endAt, @Param("expendType") ExpenseType expendType);

}
