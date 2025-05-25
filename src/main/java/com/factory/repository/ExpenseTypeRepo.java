package com.factory.repository;

import com.factory.entity.ExpenseType;
import com.factory.projections.ExpenseTypeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseTypeRepo extends JpaRepository<ExpenseType,Integer> {

    @Query(nativeQuery = true,value = "SELECT\n" +
            "    id,\n" +
            "    name,\n" +
            "    amount,\n" +
            "    created_at\n" +
            "FROM\n" +
            "    expense_type e\n" +
            "WHERE\n" +
            " (LOWER(CONCAT(e.amount, ' ', name, ' ', created_at)) LIKE LOWER(CONCAT('%', :search, '%')))\n" +
            "ORDER BY\n" +
            "    id DESC;")
    Page<ExpenseTypeProjection> getExpenseTypes(String search, Pageable pageable);

    @Query(nativeQuery = true,value = "SELECT\n" +
            "    id,\n" +
            "    name,\n" +
            "    " +
            "amount,\n" +
            "    created_at\n" +
            "FROM\n" +
            "    expense_type e\n" +
            "ORDER BY\n" +
            "    id DESC;")
    List<ExpenseType> getExpenseTypesForExpenseTable();


}
