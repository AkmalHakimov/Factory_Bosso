package com.factory.repository;

import com.factory.entity.IncomeTool;
import com.factory.projections.IncomeToolProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IncomeToolRepo extends JpaRepository<IncomeTool,Integer> {

    @Query(nativeQuery = true, value = "SELECT i.id, \n" +
            "       amount, \n" +
            "       created_at, \n" +
            "       i.payment_type, \n" +
            "       price, \n" +
            "       t.id as tool_id, \n" +
            "       price * amount as sum_income, \n" +
            "       t.name as tool_name, \n" +
            "       tt.name as tool_type_name, \n" +
            "       tt.id as tool_type_id \n" +
            "FROM income_tool i \n" +
            "JOIN tool t ON t.id = i.tool_id \n" +
            "JOIN tool_type tt ON i.tool_type_id = tt.id \n" +
            "WHERE ((:toolId = 0) OR (i.tool_id = :toolId)) \n" +
            "  AND (LOWER(CONCAT(i.amount, ' ', i.payment_type, ' ', i.price, ' ', i.created_at, ' ', tt.name, ' ', t.name)) LIKE LOWER(CONCAT('%', :search, '%'))) \n" +
            "  AND EXTRACT(YEAR FROM i.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE)) \n" + // Explicit cast to DATE
            "  AND EXTRACT(MONTH FROM i.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE)) \n" + // Explicit cast to DATE
            "ORDER BY i.id DESC")
    Page<IncomeToolProjection> getIncomeTools(@Param("search") String search,
                                              @Param("toolId") Integer toolId,
                                              @Param("date") LocalDate date,
                                              Pageable pageable);



    @Query(nativeQuery = true,value = "select COALESCE(SUM(amount), 0) as total_amount\n" +
            "from income_tool i where tool_id = :toolId and tool_type_id = :toolTypeId")
    BigDecimal totalOfSpecifiedIncome(Integer toolId, Integer toolTypeId);
}
