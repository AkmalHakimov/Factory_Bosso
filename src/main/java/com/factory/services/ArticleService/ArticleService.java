package com.factory.services.ArticleService;

import com.factory.payload.request.ReqArticle;
import org.springframework.http.HttpEntity;

public interface ArticleService {
    HttpEntity<?> get(String search, Integer page, Integer offset);

    HttpEntity<?> create(ReqArticle reqArticle);

    HttpEntity<?> delete(Integer articleId);

    HttpEntity<?> edit(ReqArticle reqArticle, Integer articleId);
}
