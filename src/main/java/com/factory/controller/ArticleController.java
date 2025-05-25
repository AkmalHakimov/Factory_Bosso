package com.factory.controller;


import com.factory.payload.request.ReqArticle;
import com.factory.payload.request.ReqWorker;
import com.factory.repository.ArticleRepo;
import com.factory.services.ArticleService.ArticleService;
import com.factory.services.WorkerService.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleRepo articleRepo;

    @GetMapping
    public HttpEntity<?> get(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset
    ){
        return articleService.get(search,page,offset);
    }

    @GetMapping("/all")
    public HttpEntity<?> getAll(){
        return ResponseEntity.ok(articleRepo.findAll());
    }

    @PostMapping
    public HttpEntity<?> create(@RequestBody ReqArticle reqArticle){
        return articleService.create(reqArticle);
    }

    @DeleteMapping("/{articleId}")
    public HttpEntity<?> delete(@PathVariable Integer articleId){
        return articleService.delete(articleId);
    }

    @PutMapping("/{articleId}")
    public HttpEntity<?> editTool(@PathVariable Integer articleId, @RequestBody ReqArticle reqArticle){
        return articleService.edit(reqArticle,articleId);
    }
}
