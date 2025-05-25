package com.factory.repository;

import com.factory.entity.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkerRepo extends JpaRepository<Worker,Integer> {

    @Query(nativeQuery = true,value = "SELECT *\n" +
            "FROM worker w\n" +
            "WHERE LOWER(CONCAT(w.first_name, ' ', w.last_name, ' ', w.role)) LIKE LOWER(CONCAT('%', :search, '%'))\n" +
            "ORDER BY w.id DESC;")
    Page<Worker> getWorkers(String search, Pageable pageable);

    List<Worker> findAllBySackedIsTrue();

}
