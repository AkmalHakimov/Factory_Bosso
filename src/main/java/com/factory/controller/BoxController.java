package com.factory.controller;

import com.factory.payload.request.ReqArticle;
import com.factory.payload.request.ReqBox;
import com.factory.repository.BoxRepo;
import com.factory.services.BoxService.BoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/box")
@RequiredArgsConstructor
public class BoxController {

    private final BoxService boxService;
    private final BoxRepo boxRepo;

    @GetMapping
    public HttpEntity<?> get(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset,
            @RequestParam LocalDate date
    ){
        return boxService.get(search,page,offset,date);
    }

    @GetMapping("/all")
    public HttpEntity<?> getAll(){
        return ResponseEntity.ok(boxRepo.findAll());
    }

    @PostMapping
    public HttpEntity<?> create(@RequestBody ReqBox reqBox){
        return boxService.create(reqBox);
    }



    @DeleteMapping("/{boxId}")
    public HttpEntity<?> delete(@PathVariable Integer boxId){
        return boxService.delete(boxId);
    }

    @PutMapping("/{boxId}")
    public HttpEntity<?> edit(@PathVariable Integer boxId, @RequestBody ReqBox reqBox){
        return boxService.edit(reqBox,boxId);
    }
}
