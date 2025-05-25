package com.factory.repository;


import com.factory.entity.ToolType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ToolTypeRepo extends JpaRepository<ToolType,Integer> {

    @Query(nativeQuery = true,value = "select * from tool_type where lower(name)like lower(concat('%',:search,'%')) order by id desc;")
    Page<ToolType> getToolTypes(String search, Pageable pageable);
}
