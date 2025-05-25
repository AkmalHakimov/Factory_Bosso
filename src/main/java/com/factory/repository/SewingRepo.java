package com.factory.repository;


import com.factory.entity.Sewing;
import com.factory.projections.ReportForOneWorker;
import com.factory.projections.ReportSewingWorkers;
import com.factory.projections.SewingProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SewingRepo extends JpaRepository<Sewing, Integer> {

    @Query(nativeQuery = true, value = "SELECT s.id,w.id as workerId, a.id as articleId,w.first_name,\n" +
            "       w.last_name,\n" +
            "       s.created_at,\n" +
            "       a.name,\n" +
            "       s.order_num,\n" +
            "       s.art_mat,\n" +
            "       s.count,\n" +
            "       (case\n" +
            "            when a.side_num = 2 then a.width*2\n" +
            "            when a.side_num = 4 then (a.height + a.width) * 2\n" +
            "            else a.height * a.width end) as art_perimeter,\n" +
            "       (case\n" +
            "            when a.side_num = 2 then a.width*2\n" +
            "            when a.side_num = 4 then (a.height + a.width) * 2\n" +
            "            else a.height * a.width end) * s.count as total_perimeter,\n" +
            "        a.bet_price,\n" +
            "        a.bet_price * (case\n" +
            "                           when a.side_num = 2 then a.width*2\n" +
            "                           when a.side_num = 4 then (a.height + a.width) * 2\n" +
            "                           else a.height * a.width end) * s.count as sewing_price,\n" +
            "        s.chip_count,\n" +
            "        a.chip_price*s.chip_count as chip_price,\n" +
            "        s.cleaning_count,\n" +
            "        s.cleaning_count*a.cleaning_price as cleaning_price,\n" +
            "        s.button_open_count,\n" +
            "        s.button_open_count * a.button_open_price as button_open_price,\n" +
            "        s.yarn_open_count,\n" +
            "        s.yarn_open_count*a.yarn_price as yarn_open_price,\n" +
            "        s.blue_label,\n" +
            "        s.blue_label*a.blue_label_price as blue_label_price,\n" +
            "        s.yellow_chip,\n" +
            "        s.yellow_chip*a.yellow_chip_price as yellow_chip_price,\n" +
            "        s.plank_drawing,\n" +
            "        s.plank_drawing*a.plank_price as plank_price,\n" +
            "        s.pack_bag,\n" +
            "        s.pack_bag*a.make_pack_price as make_pack_price\n" +
            "\n" +
            "FROM sewing s\n" +
            "         join article a on a.id = s.article_id\n" +
            "         join worker w on w.id = s.worker_id\n" +
            "WHERE LOWER(CONCAT(name, ' ', first_name, ' ', last_name)) LIKE LOWER(CONCAT('%', :search, '%'))\n" +
            "  AND EXTRACT(YEAR FROM s.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE)) \n" + // Explicit cast to DATE
            "  AND EXTRACT(MONTH FROM s.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE)) \n" + // Explicit cast to DATE
            "ORDER BY s.id DESC;")
    Page<SewingProjection> getAll(@Param("search") String search, Pageable pageable, LocalDate date);


    @Query(nativeQuery = true, value = """
    SELECT
        w.id AS workerId,
        w.first_name AS firstName, 
        w.last_name AS lastName,
        COALESCE(st.total_count, 0) AS count,
        COALESCE(st.total_chip_count, 0) AS chipCount,
        COALESCE(st.total_chip_price, 0) AS chipPrice,
        COALESCE(st.total_cleaning_count, 0) AS cleaningCount,
        COALESCE(st.total_cleaning_price, 0) AS cleaningPrice,
        COALESCE(st.total_button_open_count, 0) AS buttonOpenCount,
        COALESCE(st.total_button_open_price, 0) AS buttonOpenPrice,
        COALESCE(st.total_yarn_open_count, 0) AS yarnOpenCount,
        COALESCE(st.total_yarn_open_price, 0) AS yarnOpenPrice,
        COALESCE(st.total_blue_label, 0) AS blueLabel,
        COALESCE(st.total_blue_label_price, 0) AS blueLabelPrice,
        COALESCE(st.total_yellow_chip, 0) AS yellowChip,
        COALESCE(st.total_yellow_chip_price, 0) AS yellowChipPrice,
        COALESCE(st.total_perimeter, 0) AS totalPerimeter,
        COALESCE(st.total_sewing_price, 0) AS sewingPrice,
        COALESCE(cutting_totals.ready_prod_count, 0) AS readyProdCount,
        COALESCE(cutting_totals.total_perimeter_cutting, 0) AS totalPerimeterCutting,
        COALESCE(cutting_totals.cutting_price, 0) AS cuttingPrice,
        COALESCE(box_totals.box_count, 0) AS boxCount,
        COALESCE(box_totals.box_price, 0) AS boxPrice,
        COALESCE(box_totals.box_count_total, 0) AS boxCountTotal,
        COALESCE(st.plank_price, 0) AS plankPrice,
        COALESCE(st.plank_count, 0) AS plankPrice,
        COALESCE(st.pack_price, 0) AS makePackPrice,
        COALESCE(st.pack_count, 0) AS makePackPrice
    FROM worker w
    LEFT JOIN (
        SELECT 
            s.worker_id,
            SUM(s.count) AS total_count,
            SUM(s.chip_count) AS total_chip_count,
            SUM(s.chip_count * a.chip_price) AS total_chip_price,
            SUM(s.cleaning_count) AS total_cleaning_count,
            SUM(s.cleaning_count * a.cleaning_price) AS total_cleaning_price,
            SUM(s.button_open_count) AS total_button_open_count,
            SUM(s.button_open_count * a.button_open_price) AS total_button_open_price,
            SUM(s.yarn_open_count) AS total_yarn_open_count,
            SUM(s.yarn_open_count * a.yarn_price) AS total_yarn_open_price,
            SUM(s.blue_label) AS total_blue_label,
            SUM(s.blue_label * a.blue_label_price) AS total_blue_label_price,
            SUM(s.yellow_chip) AS total_yellow_chip,
            SUM(s.yellow_chip * a.yellow_chip_price) AS total_yellow_chip_price,
            SUM(CASE
                WHEN a.side_num = 2 THEN a.height + a.width
                WHEN a.side_num = 4 THEN (a.height + a.width) * 2
                ELSE a.height * a.width
            END * s.count) AS total_perimeter,
            SUM(a.bet_price * CASE
                WHEN a.side_num = 2 THEN a.height + a.width
                WHEN a.side_num = 4 THEN (a.height + a.width) * 2
                ELSE a.height * a.width
            END * s.count) AS total_sewing_price,
            SUM(s.plank_drawing) AS plank_count,
            SUM(s.plank_drawing * a.plank_price) AS plank_price,
            SUM(s.pack_bag) AS pack_count,
            SUM(s.pack_bag * a.make_pack_price) AS pack_price
        FROM sewing s
        LEFT JOIN article a ON s.article_id = a.id
        WHERE EXTRACT(YEAR FROM s.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))
          AND EXTRACT(MONTH FROM s.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))
        GROUP BY s.worker_id
    ) st ON w.id = st.worker_id
    LEFT JOIN (
        SELECT 
            c.worker_id,
            SUM(c.ready_prod_count) AS ready_prod_count,
            SUM(CASE
                WHEN c.side_option = 'HEIGHT' THEN a.height * c.ready_prod_count
                WHEN c.side_option = 'WIDTH' THEN a.width * c.ready_prod_count
                ELSE 0
            END) AS total_perimeter_cutting,
            COALESCE(SUM(CASE
                WHEN c.side_option = 'HEIGHT' THEN a.height * c.ready_prod_count * a.cutting_price
                WHEN c.side_option = 'WIDTH' THEN a.width * c.ready_prod_count * a.cutting_price
            END), 0) AS cutting_price
        FROM cutting c
        LEFT JOIN article a ON c.article_id = a.id
        WHERE EXTRACT(YEAR FROM c.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))
          AND EXTRACT(MONTH FROM c.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))
        GROUP BY c.worker_id
    ) cutting_totals ON w.id = cutting_totals.worker_id
    LEFT JOIN (
        SELECT 
            b.worker_id,
            SUM(b.box_count) AS box_count,
            SUM(b.box_count * b.box_content_count * a.width * a.height) AS box_count_total,
            SUM(b.box_count * b.box_content_count * a.width * a.height * a.pack_price) AS box_price
        FROM box b
        LEFT JOIN article a ON b.article_id = a.id
        LEFT JOIN worker w2 ON b.worker_id = w2.id
        WHERE LOWER(CONCAT(w2.first_name, ' ', w2.last_name)) LIKE LOWER(CONCAT('%', :search, '%'))
          AND EXTRACT(YEAR FROM b.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))
          AND EXTRACT(MONTH FROM b.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))
        GROUP BY b.worker_id
    ) box_totals ON w.id = box_totals.worker_id
    WHERE (:workerId = 0 OR w.id = :workerId)
    ORDER BY w.id
""")
    Page<ReportSewingWorkers> getReportSewingWorkers(@Param("search") String search, Pageable pageable, @Param("date") LocalDate date, Integer workerId);





    @Query(nativeQuery = true, value = """
SELECT
        w.id AS workerId, 
        w.first_name AS firstName, 
        w.last_name AS lastName,
        COALESCE(st.total_count, 0) AS count,
        COALESCE(st.total_chip_count, 0) AS chipCount,
        COALESCE(st.total_chip_price, 0) AS chipPrice,
        COALESCE(st.total_cleaning_count, 0) AS cleaningCount,
        COALESCE(st.total_cleaning_price, 0) AS cleaningPrice,
        COALESCE(st.total_button_open_count, 0) AS buttonOpenCount,
        COALESCE(st.total_button_open_price, 0) AS buttonOpenPrice,
        COALESCE(st.total_yarn_open_count, 0) AS yarnOpenCount,
        COALESCE(st.total_yarn_open_price, 0) AS yarnOpenPrice,
        COALESCE(st.total_blue_label, 0) AS blueLabel,
        COALESCE(st.total_blue_label_price, 0) AS blueLabelPrice,
        COALESCE(st.total_yellow_chip, 0) AS yellowChip,
        COALESCE(st.total_yellow_chip_price, 0) AS yellowChipPrice,
        COALESCE(st.total_perimeter, 0) AS totalPerimeter,
        COALESCE(st.total_sewing_price, 0) AS sewingPrice,
        COALESCE(cutting_totals.ready_prod_count, 0) AS readyProdCount,
        COALESCE(cutting_totals.total_perimeter_cutting, 0) AS totalPerimeterCutting,
        COALESCE(cutting_totals.cutting_price, 0) AS cuttingPrice,
        COALESCE(box_totals.box_count, 0) AS boxCount,
        COALESCE(box_totals.box_price, 0) AS boxPrice,
        COALESCE(box_totals.box_count_total, 0) AS boxCountTotal,
        COALESCE(st.plank_price, 0) AS plankCount,
        COALESCE(st.plank_count, 0) AS plankPrice,
        COALESCE(st.pack_price, 0) AS makePackCount,
        COALESCE(st.pack_count, 0) AS makePackPrice
    FROM worker w
    LEFT JOIN (
        SELECT 
            s.worker_id,
            SUM(s.count) AS total_count,
            SUM(s.chip_count) AS total_chip_count,
            SUM(s.chip_count * a.chip_price) AS total_chip_price,
            SUM(s.cleaning_count) AS total_cleaning_count,
            SUM(s.cleaning_count * a.cleaning_price) AS total_cleaning_price,
            SUM(s.button_open_count) AS total_button_open_count,
            SUM(s.button_open_count * a.button_open_price) AS total_button_open_price,
            SUM(s.yarn_open_count) AS total_yarn_open_count,
            SUM(s.yarn_open_count * a.yarn_price) AS total_yarn_open_price,
            SUM(s.blue_label) AS total_blue_label,
            SUM(s.blue_label * a.blue_label_price) AS total_blue_label_price,
            SUM(s.yellow_chip) AS total_yellow_chip,
            SUM(s.yellow_chip * a.yellow_chip_price) AS total_yellow_chip_price,
            SUM(CASE
                WHEN a.side_num = 2 THEN a.height + a.width
                WHEN a.side_num = 4 THEN (a.height + a.width) * 2
                ELSE a.height * a.width
            END * s.count) AS total_perimeter,
            SUM(a.bet_price * CASE
                WHEN a.side_num = 2 THEN a.height + a.width
                WHEN a.side_num = 4 THEN (a.height + a.width) * 2
                ELSE a.height * a.width
            END * s.count) AS total_sewing_price,
            SUM(s.plank_drawing) AS plank_count,
            SUM(s.plank_drawing * a.plank_price) AS plank_price,
            SUM(s.pack_bag) AS pack_count,
            SUM(s.pack_bag * a.make_pack_price) AS pack_price
        FROM sewing s
        LEFT JOIN article a ON s.article_id = a.id
        WHERE EXTRACT(YEAR FROM s.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))
          AND EXTRACT(MONTH FROM s.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))
        GROUP BY s.worker_id
    ) st ON w.id = st.worker_id
    LEFT JOIN (
        SELECT 
            c.worker_id,
            SUM(c.ready_prod_count) AS ready_prod_count,
            SUM(CASE
                WHEN c.side_option = 'HEIGHT' THEN a.height * c.ready_prod_count
                WHEN c.side_option = 'WIDTH' THEN a.width * c.ready_prod_count
                ELSE 0
            END) AS total_perimeter_cutting,
            COALESCE(SUM(CASE
                WHEN c.side_option = 'HEIGHT' THEN a.height * c.ready_prod_count * a.cutting_price
                WHEN c.side_option = 'WIDTH' THEN a.width * c.ready_prod_count * a.cutting_price
            END), 0) AS cutting_price
        FROM cutting c
        LEFT JOIN article a ON c.article_id = a.id
        WHERE EXTRACT(YEAR FROM c.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))
          AND EXTRACT(MONTH FROM c.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))
        GROUP BY c.worker_id
    ) cutting_totals ON w.id = cutting_totals.worker_id
    LEFT JOIN (
        SELECT 
            b.worker_id,
            SUM(b.box_count) AS box_count,
            SUM(b.box_count * b.box_content_count * a.width * a.height) AS box_count_total,
            SUM(b.box_count * b.box_content_count * a.width * a.height * a.pack_price) AS box_price
        FROM box b
        LEFT JOIN article a ON b.article_id = a.id
        LEFT JOIN worker w2 ON b.worker_id = w2.id
        WHERE LOWER(CONCAT(w2.first_name, ' ', w2.last_name)) LIKE LOWER(CONCAT('%', :search, '%'))
          AND EXTRACT(YEAR FROM b.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))
          AND EXTRACT(MONTH FROM b.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))
        GROUP BY b.worker_id
    ) box_totals ON w.id = box_totals.worker_id
    WHERE (:workerId = 0 OR w.id = :workerId)
    ORDER BY w.id
""")
    List<ReportSewingWorkers> getReportSewingWorkersForExcel(@Param("date") LocalDate date, @Param("workerId") Integer workerId, @Param("search") String search);

    @Query(nativeQuery = true, value = "SELECT SUM(s.count) AS total_count,\n" +
            "       a.name,\n" +
            "       SUM(\n" +
            "               (CASE\n" +
            "                    WHEN a.side_num = 2 THEN a.width*2\n" +
            "                    WHEN a.side_num = 4 THEN (a.height + a.width) * 2\n" +
            "                    ELSE a.height * a.width\n" +
            "                   END) * s.count\n" +
            "       )            AS total_perimeter\n" +
            "FROM article a\n" +
            "         JOIN sewing s ON a.id = s.article_id\n" +
            "WHERE s.worker_id = :workerId\n" +
            "  AND EXTRACT(YEAR FROM s.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE)) \n" + // Explicit cast to DATE
            "  AND EXTRACT(MONTH FROM s.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE)) \n" + // Explicit cast to DATE
            "GROUP BY a.id, a.name;")
        List<ReportForOneWorker> getReportForOneWorker(@Param("workerId") Integer workerId, @Param("date") LocalDate date);

}
