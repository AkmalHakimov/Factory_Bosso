package com.factory.repository;

import com.factory.entity.ExpenseCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExpenseCompanyRepo extends JpaRepository<ExpenseCompany,Integer> {
    @Query(nativeQuery = true,value = "select * from expense_company where lower(name)like lower(concat('%',:search,'%')) order by id desc;")
    Page<ExpenseCompany> getExpenseCompany(Pageable pageable, String search);
}
