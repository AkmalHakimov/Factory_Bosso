package com.factory.repository;

import com.factory.entity.ExpenseTool;
import com.factory.projections.ExpenseToolProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseToolRepo extends JpaRepository<ExpenseTool,Integer> {

    @Query(nativeQuery = true, value = "SELECT\n" +
            "    e.id,\n" +
            "    e.amount,\n" +
            "    e.created_at,\n" +
            "    e.description,\n" +
            "    e.price AS expense_price,\n" +
            "    t.id AS toolId,\n" +
            "    (e.price * e.amount) AS sum_expense,\n" +
            "    t.name AS tool_name,\n" +
            "    tt.name AS tool_type_name,\n" +
            "    tt.id AS tool_type_id,\n" +
            "    et.name AS expense_type_name,\n" +
            "    et.id AS expense_type_id\n" +
            "FROM\n" +
            "    expense_tool e\n" +
            "        LEFT JOIN tool t ON t.id = e.tool_id\n" +
            "        LEFT JOIN tool_type tt ON e.tool_type_id = tt.id\n" +
            "        LEFT JOIN expense_type et ON et.id = e.expense_type_id\n" +
            "WHERE\n" +
            "    (:toolId = 0 OR e.tool_id = :toolId)\n" +
            "  AND LOWER(CONCAT(e.amount, ' ', e.price, ' ', e.created_at, ' ', et.name, ' ', e.description, ' ', tt.name, ' ', t.name)) LIKE LOWER(CONCAT('%', :search, '%'))\n" +
            "  AND EXTRACT(YEAR FROM e.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))\n" +
            "  AND EXTRACT(MONTH FROM e.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))\n" +
            "ORDER BY\n" +
            "    e.id DESC")
    Page<ExpenseToolProjection> getExpenseTools(@Param("search") String search, @Param("toolId") Integer toolId,
                                                @Param("date") LocalDate date,
                                                Pageable pageable);



    @Query(nativeQuery = true,value = "select COALESCE(SUM(amount), 0) as total_amount\n" +
            "from expense_tool i where tool_id = :toolId and tool_type_id = :toolTypeId")
    BigDecimal totalOfSpecifiedExpense(Integer toolId, Integer toolTypeId);
}
