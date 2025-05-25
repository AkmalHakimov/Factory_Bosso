package com.factory.repository;

import com.factory.entity.Box;
import com.factory.projections.BoxProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface BoxRepo extends JpaRepository<Box,Integer> {

    @Query(nativeQuery = true,value = "SELECT b.id,a.id as articleId,w.id as workerId, w.first_name,\n" +
            "       w.last_name,\n" +
            "       b.created_at,\n" +
            "       a.name,\n" +
            "       b.order_num,\n" +
            "       b.box_count,\n" +
            "       b.box_content_count,\n" +
            "       b.box_count * b.box_content_count                                     as packed_count,\n" +
            "       a.width * a.height                                                    as square,\n" +
            "       b.box_count * b.box_content_count * a.width * a.height                as total_square,\n" +
            "       a.pack_price                                                          as rascenka,\n" +
            "       b.box_count * b.box_content_count * a.width * a.height * a.pack_price as sewing_amount\n" +
            "FROM box b\n" +
            "         join article a on a.id = b.article_id\n" +
            "         join worker w on w.id = b.worker_id\n" +
            "WHERE LOWER(CONCAT(name, ' ', first_name, ' ', last_name)) LIKE LOWER(CONCAT('%', :search, '%'))\n" +
            "  AND EXTRACT(YEAR FROM b.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE)) \n" + // Explicit cast to DATE
            "  AND EXTRACT(MONTH FROM b.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE)) \n" + // Explicit cast to DATE
            "ORDER BY b.id DESC;")
    Page<BoxProjection> getBoxes(String search, Pageable pageable, LocalDate date);


}
