package com.factory.repository;

import com.factory.entity.Tool;
import com.factory.payload.response.ResTool;
import com.factory.projections.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ToolRepo extends JpaRepository<Tool,Integer> {


    @Query(nativeQuery = true,value = "SELECT\n" +
            "    t.id,\n" +
            "    t.code,\n" +
            "    t.color,\n" +
            "    t.dimension,\n" +
            "    t.marka,\n" +
            "    t.name,\n" +
            "    t.size,\n" +
            "    json_agg(\n" +
            "            json_build_object(\n" +
            "                    'label', tool_type.name,\n" +
            "                    'value', tool_type.id\n" +
            "            )\n" +
            "    ) AS tool_type_id\n" +
            "FROM\n" +
            "    tool t\n" +
            "        JOIN\n" +
            "    tool_tool_types ttt ON t.id = ttt.tool_id\n" +
            "        JOIN\n" +
            "    tool_type ON ttt.tool_types_id = tool_type.id\n" +
            "where(lower(concat(t.dimension, ' ', t.name, ' ', t.code, ' ', t.marka)) like lower(concat('%',:search,'%')))\n" +
            "GROUP BY\n" +
            "    t.id order by t.id desc;")
    Page<ToolProjection> getTools(Pageable pageable, String search);


    @Query(nativeQuery = true, value = "WITH income_summary AS (\n" +
            "    SELECT\n" +
            "        it.tool_id,  -- Include tool_id in the SELECT statement\n" +
            "        SUM(it.amount) AS total_income_amount\n" +
            "    FROM income_tool it\n" +
            "    WHERE EXTRACT(YEAR FROM it.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))  -- Year filter\n" +
            "      AND EXTRACT(MONTH FROM it.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))  -- Month filter\n" +
            "    GROUP BY it.tool_id\n" +
            "),\n" +
            "expense_summary AS (\n" +
            "    SELECT\n" +
            "        e.tool_id,\n" +
            "        SUM(e.amount) AS total_expense_amount,\n" +
            "        SUM(e.amount * e.price) AS total_expense_price\n" +
            "    FROM expense_tool e\n" +
            "    WHERE EXTRACT(YEAR FROM e.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))  -- Year filter\n" +
            "      AND EXTRACT(MONTH FROM e.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))  -- Month filter\n" +
            "      AND e.expense_type_id IS NOT NULL  -- Exclude rows with NULL expense_type_id\n" +
            "    GROUP BY e.tool_id\n" +
            ")\n" +
            "SELECT\n" +
            "    t.id,\n" +
            "    t.marka,\n" +
            "    t.name,\n" +
//            "    COALESCE(income_summary.total_income_amount, 0) AS total_income_amount,\n" +
            "    COALESCE(expense_summary.total_expense_amount, 0) AS total_expense_amount,\n" +
            "    COALESCE(expense_summary.total_expense_price, 0) AS total_expense_price\n" +
            "FROM\n" +
            "    tool t\n" +
//            "    LEFT JOIN income_summary ON t.id = income_summary.tool_id\n" +
            "    INNER JOIN expense_summary ON t.id = expense_summary.tool_id\n" +
            "WHERE\n" +
            "    LOWER(CONCAT(t.dimension, ' ', t.name, ' ', t.code, ' ', t.marka)) LIKE LOWER(CONCAT('%', :search, '%'))  -- Case-insensitive search\n" +
            "ORDER BY\n" +
            "    t.id DESC;\n")
    Page<ToolReportProjection> getReports(Pageable pageable, @Param("search") String search, @Param("date") LocalDate date);


@Query(nativeQuery = true,value = "SELECT \n" +
            "    e.id, \n" +
            "    e.name,\n" +
            "    et.amount,\n" +
            "    et.price AS expense_price,  -- Expense price from expense_tool\n" +
            "    SUM(et.amount * et.price) AS totalExpense,  -- Calculating total expense\n" +
//            "    SUM(et.amount * i.price) AS totalIncome,  -- Calculating total income based on income_tool price\n" +
            "    CASE\n" +
            "        WHEN e.amount != 0 THEN SUM(et.amount * et.price) / e.amount\n" +
            "        ELSE 0\n" +
            "    END AS ratio\n" +
            "FROM \n" +
            "    expense_tool et\n" +
            "    JOIN expense_type e ON et.expense_type_id = e.id\n" +
//            "    LEFT JOIN income_tool i ON et.tool_id = i.tool_id  -- Joining with income_tool to get income price\n" +
            "WHERE \n" +
            "    et.tool_id = :toolId\n" +
            "    AND EXTRACT(YEAR FROM et.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))  -- Year filter\n" +
            "    AND EXTRACT(MONTH FROM et.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))  -- Month filter\n" +
            "GROUP BY \n" +
            "    e.id, e.name, et.amount, et.price, e.amount  -- Grouping by all required fields\n")
    List<OneToolReportProjection> getOneToolReport(Integer toolId, LocalDate date);



    @Query(nativeQuery = true, value = "SELECT " +
            "t.id AS id, " +
            "t.name AS tool_name, " +
            "e.expense_type, " +
            "inc.total_income, " +
            "exp.total_expense, " +
            "exp.total_expense_price, " +
            "SUM(e.amount) AS total_expense_based_on_type " +
            "FROM " +
            "expense_tool e " +
            "JOIN tool t ON e.tool_id = t.id " +
            "LEFT JOIN (SELECT i.tool_id, SUM(i.amount) AS total_income FROM income_tool i GROUP BY i.tool_id) inc ON t.id = inc.tool_id " +
            "LEFT JOIN (SELECT e2.tool_id, SUM(e2.amount) AS total_expense, SUM(e2.amount * e2.price) AS total_expense_price FROM expense_tool e2 GROUP BY e2.tool_id) exp ON t.id = exp.tool_id " +
            "WHERE " +
            "e.expense_type IN ('SALFETKA', 'CHOYSHAB', 'SUMKA') " +
            "GROUP BY " +
            "t.id, t.name, e.expense_type, inc.total_income, exp.total_expense, exp.total_expense_price " +
            "ORDER BY " +
            "t.name, e.expense_type;")
    List<ToolReportProjection> getReportsExcel(String search);

    @Query(nativeQuery = true, value = "SELECT\n" +
            "    SUM(amount) AS total_income_amount,\n" +
            "    (SELECT SUM(amount * price) FROM expense_tool) AS total_expense_price\n" +
            "FROM\n" +
            "    income_tool;")
    CalculateBalanceProjection calculateTotalExpensePriceAndTotalIncome();









        @Query(nativeQuery = true,value = "SELECT\n" +
                "    -- Calculating `sumka_total`\n" +
                "    COALESCE(\n" +
                "        (\n" +
                "            SELECT\n" +
                "                SUM(e.amount * e.price) +\n" +
                "                (\n" +
                "                    :totalExpensePriceSum *\n" +
                "                    (SELECT rp.date_num_sumka\n" +
                "                     FROM report_pie rp\n" +
                "                     WHERE EXTRACT(MONTH FROM rp.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))\n" +
                "                     LIMIT 1)\n" +
                "                        /\n" +
                "                    (SELECT SUM(rp.month_date_num)\n" +
                "                     FROM report_pie rp\n" +
                "                     WHERE EXTRACT(MONTH FROM rp.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE)))\n" +
                "                )\n" +
                "            FROM\n" +
                "                expense_tool e\n" +
                "                JOIN expense_type et ON et.id = e.expense_type_id\n" +
                "            WHERE\n" +
                "                LOWER(et.name) = LOWER('SUMKA')\n" +
                "                AND EXTRACT(MONTH FROM e.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))\n" +
                "                AND EXTRACT(YEAR FROM e.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))\n" +
                "        ), 0\n" +
                "    ) AS sumka_total,\n" +
                "\n" +
                "    -- Calculating `salfetka_total`\n" +
                "    COALESCE(\n" +
                "        (\n" +
                "            SELECT\n" +
                "                SUM(e.amount * e.price) +\n" +
                "                (\n" +
                "                    :totalExpensePriceSum *\n" +
                "                    (SELECT rp.date_num_sal\n" +
                "                     FROM report_pie rp\n" +
                "                     WHERE EXTRACT(MONTH FROM rp.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))\n" +
                "                     LIMIT 1)\n" +
                "                        /\n" +
                "                    (SELECT SUM(rp.month_date_num)\n" +
                "                     FROM report_pie rp\n" +
                "                     WHERE EXTRACT(MONTH FROM rp.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE)))\n" +
                "                )\n" +
                "            FROM\n" +
                "                expense_tool e\n" +
                "                JOIN expense_type et ON et.id = e.expense_type_id\n" +
                "            WHERE\n" +
                "                LOWER(et.name) = LOWER('SALFETKA')\n" +
                "                AND EXTRACT(MONTH FROM e.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))\n" +
                "                AND EXTRACT(YEAR FROM e.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))\n" +
                "        ), 0\n" +
                "    ) AS salfetka_total,\n" +
                "\n" +
                "    -- Calculating `choyshab_total`\n" +
                "    COALESCE(\n" +
                "        (\n" +
                "            SELECT\n" +
                "                SUM(e.amount * e.price) +\n" +
                "                (\n" +
                "                    :totalExpensePriceSum *\n" +
                "                    (SELECT rp.date_num_choy\n" +
                "                     FROM report_pie rp\n" +
                "                     WHERE EXTRACT(MONTH FROM rp.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))\n" +
                "                     LIMIT 1)\n" +
                "                        /\n" +
                "                    (SELECT SUM(rp.month_date_num)\n" +
                "                     FROM report_pie rp\n" +
                "                     WHERE EXTRACT(MONTH FROM rp.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE)))\n" +
                "                )\n" +
                "            FROM\n" +
                "                expense_tool e\n" +
                "                JOIN expense_type et ON et.id = e.expense_type_id\n" +
                "            WHERE\n" +
                "                LOWER(et.name) = LOWER('CHOYSHAB')\n" +
                "                AND EXTRACT(MONTH FROM e.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))\n" +
                "                AND EXTRACT(YEAR FROM e.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))\n" +
                "        ), 0\n" +
                "    ) AS choyshab_total,\n" +
                "\n" +
                "    -- Calculating `salfetka_total_am`\n" +
                "    COALESCE(\n" +
                "        (\n" +
                "            SELECT\n" +
                "                (SUM(e.amount * e.price) +\n" +
                "                 (\n" +
                "                     :totalExpensePriceSum *\n" +
                "                     (SELECT rp.date_num_sal\n" +
                "                      FROM report_pie rp\n" +
                "                      WHERE EXTRACT(MONTH FROM rp.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))\n" +
                "                      LIMIT 1)\n" +
                "                         /\n" +
                "                     (SELECT SUM(rp.month_date_num)\n" +
                "                      FROM report_pie rp\n" +
                "                      WHERE EXTRACT(MONTH FROM rp.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE)))\n" +
                "                 )\n" +
                "                ) / MAX(et.amount)\n" +
                "            FROM\n" +
                "                expense_tool e\n" +
                "                JOIN expense_type et ON et.id = e.expense_type_id\n" +
                "            WHERE\n" +
                "                LOWER(et.name) = LOWER('SALFETKA')\n" +
                "                AND EXTRACT(MONTH FROM e.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))\n" +
                "                AND EXTRACT(YEAR FROM e.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))\n" +
                "        ), 0\n" +
                "    ) AS salfetka_total_am,\n" +
                "\n" +
                "    -- Calculating `sumka_total_am`\n" +
                "    COALESCE(\n" +
                "        (\n" +
                "            SELECT\n" +
                "                (SUM(e.amount * e.price) +\n" +
                "                 (\n" +
                "                     :totalExpensePriceSum *\n" +
                "                     (SELECT rp.date_num_sumka\n" +
                "                      FROM report_pie rp\n" +
                "                      WHERE EXTRACT(MONTH FROM rp.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))\n" +
                "                      LIMIT 1)\n" +
                "                         /\n" +
                "                     (SELECT SUM(rp.month_date_num)\n" +
                "                      FROM report_pie rp\n" +
                "                      WHERE EXTRACT(MONTH FROM rp.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE)))\n" +
                "                 )\n" +
                "                ) / MAX(et.amount)\n" +
                "            FROM\n" +
                "                expense_tool e\n" +
                "                JOIN expense_type et ON et.id = e.expense_type_id\n" +
                "            WHERE\n" +
                "                LOWER(et.name) = LOWER('SUMKA')\n" +
                "                AND EXTRACT(MONTH FROM e.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))\n" +
                "                AND EXTRACT(YEAR FROM e.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))\n" +
                "        ), 0\n" +
                "    ) AS sumka_total_am,\n" +
                "\n" +
                "    -- Calculating `choyshab_total_am`\n" +
                "    COALESCE(\n" +
                "        (\n" +
                "            SELECT\n" +
                "                (SUM(e.amount * e.price) +\n" +
                "                 (\n" +
                "                     :totalExpensePriceSum *\n" +
                "                     (SELECT rp.date_num_choy\n" +
                "                      FROM report_pie rp\n" +
                "                      WHERE EXTRACT(MONTH FROM rp.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))\n" +
                "                      LIMIT 1)\n" +
                "                         /\n" +
                "                     (SELECT SUM(rp.month_date_num)\n" +
                "                         FROM report_pie rp\n" +
                "                      WHERE EXTRACT(MONTH FROM rp.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE)))\n" +
                "                 )\n" +
                "                ) / MAX(et.amount)\n" +
                "            FROM\n" +
                "                expense_tool e\n" +
                "                JOIN expense_type et ON et.id = e.expense_type_id\n" +
                "            WHERE\n" +
                "                LOWER(et.name) = LOWER('CHOYSHAB')\n" +
                "                AND EXTRACT(MONTH FROM e.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))\n" +
                "                AND EXTRACT(YEAR FROM e.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))\n" +
                "        ), 0\n" +
                "    ) AS choyshab_total_am\n" +
                "\n" +
                "FROM\n" +
                "    report_pie rp\n" +
                "WHERE\n" +
                "    EXTRACT(MONTH FROM rp.created_at) = EXTRACT(MONTH FROM CAST(:date AS DATE))\n")
    PieReportProjection toolPieReport(LocalDate date, BigDecimal totalExpensePriceSum);
//    @Query(nativeQuery = true,value = "select t.id,\n" +
//            "       t.name,\n" +
//            "       t.dimension,\n" +
//            "       et.amount             as expense_amount,\n" +
//            "       it.amount             as income_amount," +
//            "       ts.amount             as saldo_amount,\n" +
//            "       (it.amount + COALESCE(ts.amount, 0)) - et.amount AS lastAmount\n" +
//            "from tool t\n" +
//            "         join expense_tool et on t.id = et.tool_id\n" +
//            "         join income_tool it on t.id = it.tool_id\n" +
//            "         left join tool_saldo ts on t.id = ts.tool_id\n" +
//            "order by id desc\n")
//    List<ToolReportProjection> getReportExcel(String search);
//
//    @Query(nativeQuery = true,value = "select t.id, t.name,t.color,t.size,t.dimension,ts.amount as saldo_amount,et.amount as expense_amount,it.amount as income_amount, (it.amount + COALESCE(ts.amount, 0)) - et.amount AS lastAmount\n " +
//            "from tool t\n" +
//            "         join expense_tool et on t.id = et.tool_id\n" +
//            "         join income_tool it on t.id = it.tool_id left join tool_saldo ts on t.id = ts.tool_id " +
//            "where (lower(concat(t.dimension, ' ', t.name)) like lower(concat('%',:search,'%'))) order by id desc")
//    Page<ToolReportProjection> getReports(Pageable pageable, String search);


}
