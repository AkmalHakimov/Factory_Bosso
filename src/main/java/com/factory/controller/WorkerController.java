package com.factory.controller;

import com.factory.payload.request.ReqTool;
import com.factory.payload.request.ReqWorker;
import com.factory.repository.WorkerRepo;
import com.factory.services.WorkerService.WorkerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/worker")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;
    private final WorkerRepo workerRepo;

    @GetMapping
    public HttpEntity<?> get(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset
    ){
        return workerService.get(search,page,offset);
    }

    @GetMapping("/all")
    public HttpEntity<?> getAll(){
        return ResponseEntity.ok(workerRepo.findAllBySackedIsTrue());
    }

    @PostMapping
    public HttpEntity<?> create(@RequestBody ReqWorker reqWorker){
        return workerService.create(reqWorker);
    }

    @DeleteMapping("/{workerId}")
    public HttpEntity<?> delete(@PathVariable Integer workerId){
        return workerService.delete(workerId);
    }

    @PutMapping("/{workerId}")
    public HttpEntity<?> editTool(@PathVariable Integer workerId, @RequestBody ReqWorker reqWorker){
        return workerService.edit(reqWorker,workerId);
    }

}
