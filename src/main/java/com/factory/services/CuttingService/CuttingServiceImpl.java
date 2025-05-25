package com.factory.services.CuttingService;

import com.factory.entity.Cutting;
import com.factory.enums.SideOption;
import com.factory.payload.request.ReqCutting;
import com.factory.repository.ArticleRepo;
import com.factory.repository.CuttingRepo;
import com.factory.repository.WorkerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CuttingServiceImpl implements CuttingService {


    private final CuttingRepo cuttingRepo;
    private final ArticleRepo articleRepo;
    private final WorkerRepo workerRepo;

    @Override
    public HttpEntity<?> get(String search, Integer page, Integer offset, LocalDate date) {
        Pageable pageable = PageRequest.of(page - 1, offset);
        return ResponseEntity.ok(cuttingRepo.getAll(search, pageable, date ));
    }

//    @Override
//    public HttpEntity<?> get() {
//        Pageable pageable = PageRequest.of(page - 1, offset);
//        return ResponseEntity.ok(cuttingRepo.getAll(search, pageable));
//    }



    @Override
    public HttpEntity<?> getCuttingReportsForOneWorker(String search, Integer page, Integer offset, Integer workerId, LocalDate date) {
        Pageable pageable = PageRequest.of(page - 1, offset);
        return ResponseEntity.ok(cuttingRepo.getCuttingReportsForOneWorker(workerId, date));
    }

    @Override
    public HttpEntity<?> getCuttingReports(String search, Integer page, Integer offset, LocalDate date) {
        Pageable pageable = PageRequest.of(page - 1, offset);
        return ResponseEntity.ok(cuttingRepo.getCuttingReports(pageable, date));
    }

    @Override
    public HttpEntity<?> create(ReqCutting reqCutting) {
        return ResponseEntity.ok(cuttingRepo.save(Cutting.builder()
                .createdAt(reqCutting.getCreatedAt())
                .readyProdCount(reqCutting.getReadyProdCount())
                .orderNum(reqCutting.getOrderNum())
                .sideOption(SideOption.valueOf(reqCutting.getSideOption()))
                .article(articleRepo.findById(reqCutting.getArticleId()).orElseThrow())
                .worker(workerRepo.findById(reqCutting.getWorkerId()).orElseThrow())
                .build()));
    }

    @Override
    public HttpEntity<?> delete(Integer cuttingId) {
        cuttingRepo.deleteById(cuttingId);
        return ResponseEntity.ok("");
    }



    @Override
    public HttpEntity<?> edit(Integer cuttingId, ReqCutting reqCutting) {
        return ResponseEntity.ok(cuttingRepo.save(Cutting.builder()
                .id(cuttingId)
                .createdAt(reqCutting.getCreatedAt())
                .readyProdCount(reqCutting.getReadyProdCount())
                .orderNum(reqCutting.getOrderNum())
                .sideOption(SideOption.valueOf(reqCutting.getSideOption()))
                .article(articleRepo.findById(reqCutting.getArticleId()).orElseThrow())
                .worker(workerRepo.findById(reqCutting.getWorkerId()).orElseThrow())
                .build()));
    }
}
