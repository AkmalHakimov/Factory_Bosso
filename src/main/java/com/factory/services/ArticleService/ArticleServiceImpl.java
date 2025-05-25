package com.factory.services.ArticleService;

import com.factory.entity.Article;
import com.factory.entity.Worker;
import com.factory.payload.request.ReqArticle;
import com.factory.repository.ArticleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepo articleRepo;

    @Override
    public HttpEntity<?> get(String search, Integer page, Integer offset) {
        Pageable pageable = PageRequest.of(page - 1, offset);
        return ResponseEntity.ok(articleRepo.getArticles(search, pageable));
    }

    @Override
    public HttpEntity<?> create(ReqArticle reqArticle) {
            return ResponseEntity.ok(articleRepo.save(Article.builder()
                    .name(reqArticle.getName())
                    .width(reqArticle.getWidth())
                    .height(reqArticle.getHeight())
                    .sideNum(reqArticle.getSideNum())
                    .betPrice(reqArticle.getBetPrice())
                    .cuttingPrice(reqArticle.getCuttingPrice())
                    .packPrice(reqArticle.getPackPrice())
                    .yarnPrice(reqArticle.getYarnPrice())
                    .chipPrice(reqArticle.getChipPrice())
                    .cleaningPrice(reqArticle.getCleaningPrice())
                    .buttonOpenPrice(reqArticle.getButtonOpenPrice())
                    .yarnOpenPrice(reqArticle.getYarnOpenPrice())
                    .blueLabelPrice(reqArticle.getBlueLabelPrice())
                    .labelPrice(reqArticle.getLabelPrice())
                    .yellowChipPrice(reqArticle.getYellowChipPrice())
                    .labelPrice(reqArticle.getLabelPrice())
                    .blueLabelPrice(reqArticle.getBlueLabelPrice())
                    .plankPrice(reqArticle.getPlankPrice())
                    .makePackPrice(reqArticle.getMakePackPrice())
                    .build()));
    }

    @Override
    public HttpEntity<?> delete(Integer articleId) {
        articleRepo.deleteById(articleId);
        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> edit(ReqArticle reqArticle, Integer articleId) {
        return ResponseEntity.ok(articleRepo.save(Article.builder()
                .id(articleId)
                .name(reqArticle.getName())
                .width(reqArticle.getWidth())
                .height(reqArticle.getHeight())
                .sideNum(reqArticle.getSideNum())
                .betPrice(reqArticle.getBetPrice())
                .cuttingPrice(reqArticle.getCuttingPrice())
                .packPrice(reqArticle.getPackPrice())
                .yarnPrice(reqArticle.getYarnPrice())
                .chipPrice(reqArticle.getChipPrice())
                .cleaningPrice(reqArticle.getCleaningPrice())
                .buttonOpenPrice(reqArticle.getButtonOpenPrice())
                .yarnOpenPrice(reqArticle.getYarnOpenPrice())
                .blueLabelPrice(reqArticle.getBlueLabelPrice())
                .labelPrice(reqArticle.getLabelPrice())
                .yellowChipPrice(reqArticle.getYellowChipPrice())
                .labelPrice(reqArticle.getLabelPrice())
                .blueLabelPrice(reqArticle.getBlueLabelPrice())
                .plankPrice(reqArticle.getPlankPrice())
                .makePackPrice(reqArticle.getMakePackPrice())
                .build()));
    }
}
