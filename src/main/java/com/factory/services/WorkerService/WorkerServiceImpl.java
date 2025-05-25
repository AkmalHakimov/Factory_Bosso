package com.factory.services.WorkerService;

import com.factory.entity.IncomeTool;
import com.factory.entity.Worker;
import com.factory.payload.request.ReqWorker;
import com.factory.repository.WorkerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepo workerRepo;

    @Override
    public HttpEntity<?> get(String search, Integer page, Integer offset) {
        Pageable pageable = PageRequest.of(page - 1, offset);
        return ResponseEntity.ok(workerRepo.getWorkers(search, pageable));
    }

    @Override
    public HttpEntity<?> create(ReqWorker reqWorker) {
        return ResponseEntity.ok(workerRepo.save(Worker.builder()
                .role(reqWorker.getRole())
                .firstName(reqWorker.getFirstName())
                .lastName(reqWorker.getLastName())
                .sacked(reqWorker.getSacked())
                .build()));
    }

    @Override
    public HttpEntity<?> delete(Integer workerId) {
        workerRepo.deleteById(workerId);
        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> edit(ReqWorker reqWorker, Integer workerId) {
        return ResponseEntity.ok(workerRepo.save(Worker.builder()
                .id(workerId)
                .role(reqWorker.getRole())
                .firstName(reqWorker.getFirstName())
                .sacked(reqWorker.getSacked())
                .lastName(reqWorker.getLastName())
                .build()));
    }
}
