package com.factory.repository;

import com.factory.entity.Cutting;
import com.factory.projections.CuttingProjection;
import com.factory.projections.CuttingReport;
import com.factory.projections.CuttingReportOneWorker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CuttingRepo extends JpaRepository<Cutting,Integer> {

    @Query(nativeQuery = true,value = "SELECT c.id, a.id as articleId, w.id as workerId,\n" +
            "       w.first_name,\n" +
            "       w.last_name,\n" +
            "       c.created_at,\n" +
            "       a.name,\n" +
            "       c.order_num,\n" +
            "       c.side_option,\n" +
            "       a.width,\n" +
            "       a.height,\n" +
            "       (case when side_option = 'HEIGHT' then a.height when side_option = 'WIDTH' then a.width end) as perimeter,\n" +
            "       c.ready_prod_count,\n" +
            "       (case when side_option = 'HEIGHT' then a.height*c.ready_prod_count when side_option = 'WIDTH' then a.width*c.ready_prod_count end) as total_perimeter,\n" +
            "       a.cutting_price as rascenka,\n" +
            "       (case when side_option = 'HEIGHT' then a.height*c.ready_prod_count when side_option = 'WIDTH' then a.width*c.ready_prod_count end) * a.cutting_price as cutting_price\n" +
            "FROM cutting c\n" +
            "         join article a on a.id = c.article_id\n" +
            "         join worker w on w.id = c.worker_id\n" +
            "WHERE LOWER(CONCAT(name, ' ', first_name, ' ', last_name)) LIKE LOWER(CONCAT('%', :search, '%'))\n" +
            "  AND EXTRACT(YEAR FROM c.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE)) \n" + // Explicit cast to DATE
            "  AND EXTRACT(MONTH FROM c.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE)) \n" + // Explicit cast to DATE
            "ORDER BY c.id DESC;")
    Page<CuttingProjection> getAll(@Param("search") String search, Pageable pageable, @Param("date") LocalDate date);

    @Query(nativeQuery = true, value = """
    SELECT w.id,
           w.first_name,
           w.last_name,
           COALESCE(SUM(c.ready_prod_count), 0) AS total_count,
           COALESCE(SUM(
                        CASE
                            WHEN side_option = 'HEIGHT' THEN a.height * c.ready_prod_count
                            WHEN side_option = 'WIDTH' THEN a.width * c.ready_prod_count
                        END), 0) AS total_meter,
           COALESCE(SUM(
                        CASE
                            WHEN side_option = 'HEIGHT' THEN a.height * c.ready_prod_count * a.cutting_price
                            WHEN side_option = 'WIDTH' THEN a.width * c.ready_prod_count * a.cutting_price
                        END), 0) AS total_price
    FROM worker w
             LEFT JOIN cutting c ON w.id = c.worker_id
             LEFT JOIN article a ON a.id = c.article_id
    WHERE EXTRACT(YEAR FROM c.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))
      AND EXTRACT(MONTH FROM c.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))
    GROUP BY w.id
""")
    Page<CuttingReport> getCuttingReports(Pageable pageable, @Param("date") LocalDate date);



    @Query(nativeQuery = true, value = """
    SELECT a.name,
           COALESCE(SUM(c.ready_prod_count), 0) AS total_count,
           COALESCE(SUM(
                        CASE
                            WHEN side_option = 'HEIGHT' THEN a.height * c.ready_prod_count
                            WHEN side_option = 'WIDTH' THEN a.width * c.ready_prod_count
                        END), 0) AS total_meter,
           COALESCE(SUM(
                        CASE
                            WHEN side_option = 'HEIGHT' THEN a.height * c.ready_prod_count * a.cutting_price
                            WHEN side_option = 'WIDTH' THEN a.width * c.ready_prod_count * a.cutting_price
                        END), 0) AS total_price
    FROM article a
             JOIN cutting c ON a.id = c.article_id
    WHERE c.worker_id = :workerId
      AND EXTRACT(YEAR FROM c.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))
      AND EXTRACT(MONTH FROM c.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))
    GROUP BY a.id, a.name
""")
    List<CuttingReportOneWorker> getCuttingReportsForOneWorker(@Param("workerId") Integer workerId, @Param("date") LocalDate date);

}
