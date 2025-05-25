package com.factory.repository;

import com.factory.entity.Tool;
import com.factory.projections.StoreProjection;
import com.factory.projections.ToolReportProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ReportRepo extends JpaRepository<Tool,Integer> {

    @Query(nativeQuery = true, value = "SELECT\n" +
            "    t.id,\n" +
            "    t.marka,\n" +
            "    t.name,\n" +
            "    COALESCE(SUM(it.amount), 0) AS total_income_amount,\n" +
            "    COALESCE(SUM(e.amount), 0) AS total_expense_amount,\n" +
            "    COALESCE(SUM(e.amount * e.price), 0) AS total_expense_price\n" +
            "FROM\n" +
            "    tool t\n" +
            "    LEFT JOIN income_tool it ON t.id = it.tool_id\n" +
            "        AND EXTRACT(YEAR FROM it.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))\n" +
            "        AND EXTRACT(MONTH FROM it.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))\n" +
            "    LEFT JOIN expense_tool e ON t.id = e.tool_id\n" +
            "        AND EXTRACT(YEAR FROM e.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))\n" +
            "        AND EXTRACT(MONTH FROM e.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))\n" +
            "        AND e.expense_type_id IS NULL  -- Exclude rows with NULL expense_type_id\n" +
            "WHERE\n" +
            "    LOWER(CONCAT(t.dimension, ' ', t.name, ' ', t.code, ' ', t.marka)) LIKE LOWER(CONCAT('%', :search, '%'))  -- Case-insensitive search\n" +
            "GROUP BY\n" +
            "    t.id, t.marka, t.name\n" +
            "ORDER BY\n" +
            "    t.id DESC;\n")
    Page<ToolReportProjection> getSecondaryReports(Pageable pageable, @Param("search") String search,@Param("date") LocalDate date);


    @Query(nativeQuery = true, value = "SELECT\n" +
            "    tt.id AS tool_type_id,\n" +
            "    tt.name AS tool_type_name,\n" +
            "    COALESCE(income.total_income_amount, 0) AS total_income_amount,\n" +
            "    COALESCE(expense.total_expense_amount, 0) AS total_expense_amount,\n" +
            "    COALESCE(income.total_income_amount, 0) - COALESCE(expense.total_expense_amount, 0) AS left_amount\n" +
            "FROM\n" +
            "    tool_type tt\n" +
            "        LEFT JOIN (\n" +
            "        SELECT\n" +
            "            i.tool_type_id,\n" +
            "            SUM(i.amount) AS total_income_amount\n" +
            "        FROM\n" +
            "            income_tool i\n" +
            "        WHERE\n" +
            "            i.created_at <= :date  -- Filter by provided date\n" +
            "        GROUP BY\n" +
            "            i.tool_type_id\n" +
            "    ) AS income ON tt.id = income.tool_type_id\n" +
            "        LEFT JOIN (\n" +
            "        SELECT\n" +
            "            e.tool_type_id,\n" +
            "            SUM(e.amount) AS total_expense_amount\n" +
            "        FROM\n" +
            "            expense_tool e\n" +
            "        WHERE\n" +
            "            e.created_at <= :date  -- Filter by provided date\n" +
            "        GROUP BY\n" +
            "            e.tool_type_id\n" +
            "    ) AS expense ON tt.id = expense.tool_type_id\n" +
            "WHERE\n" +
            "    LOWER(tt.name) LIKE LOWER(CONCAT('%', :search, '%'))  -- Filter by tool_type_name\n" +
            "ORDER BY\n" +
            "    tt.id")
    Page<StoreProjection> getLeftAmount(Pageable pageable, String search,LocalDate date);




}
