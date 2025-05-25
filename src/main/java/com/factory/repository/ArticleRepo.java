package com.factory.repository;


import com.factory.entity.Article;
import com.factory.projections.ArticleProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepo extends JpaRepository<Article,Integer> {


    @Query(nativeQuery = true,value = "SELECT id,\n" +
            "       bet_price,\n" +
            "       blue_label_price,\n" +
            "       button_open_price,\n" +
            "       chip_price,\n" +
            "       cleaning_price,\n" +
            "       cutting_price,\n" +
            "       height,\n" +
            "       label_price,\n" +
            "       make_pack_price,\n" +
            "       name,\n" +
            "       pack_price,\n" +
            "       plank_price,\n" +
            "       side_num,\n" +
            "       width,\n" +
            "       yarn_open_price,\n" +
            "       yarn_price,\n" +
            "       yellow_chip_price,\n" +
            "       (case\n" +
            "            when side_num = 2 then width*2\n" +
            "            when side_num = 4 then (width + height) * 2\n" +
            "            else width * height end) as sewing_perimeter,\n" +
            "       (case\n" +
            "            when side_num = 2 then width*2\n" +
            "            when side_num = 4 then (width + height) * 2\n" +
            "            else width * height end)/2 as cutting_perimeter,\n" +
            "       width*height as square\n" +
            "FROM article\n" +
            "WHERE LOWER(CONCAT(name, ' ', name, ' ', width,' ', height)) LIKE LOWER(CONCAT('%', :search, '%'))\n" +
            "ORDER BY id DESC;")
    Page<ArticleProjection> getArticles(String search, Pageable pageable);
}
