package com.factory.services.SewingService;

import com.factory.entity.Sewing;
import com.factory.payload.request.ReqSewing;
import com.factory.repository.ArticleRepo;
import com.factory.repository.SewingRepo;
import com.factory.repository.WorkerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SewingServiceImpl implements SewingService {


    private final SewingRepo sewingRepo;
    private final ArticleRepo articleRepo;
    private final WorkerRepo workerRepo;

    @Override
    public HttpEntity<?> getReportForOneWorker(String search, Integer page, Integer offset, Integer workerId, LocalDate date) {
        Pageable pageable = PageRequest.of(page - 1, offset);
        return ResponseEntity.ok(sewingRepo.getReportForOneWorker(workerId, date));
    }
    @Override
    public HttpEntity<?> get(String search, Integer page, Integer offset, LocalDate date) {
        Pageable pageable = PageRequest.of(page - 1, offset);
        return ResponseEntity.ok(sewingRepo.getAll(search, pageable,date));
    }

    @Override
    public HttpEntity<?> getWorkerReports(String search, Integer page, Integer offset, LocalDate date, Integer workerId) {
        Pageable pageable = PageRequest.of(page - 1, offset);
        return ResponseEntity.ok(sewingRepo.getReportSewingWorkers(search, pageable, date,workerId));
    }

    @Override
    public HttpEntity<?> create(ReqSewing reqSewing) {
        return ResponseEntity.ok(sewingRepo.save(Sewing.builder()
                .createdAt(reqSewing.getCreatedAt())
                .orderNum(reqSewing.getOrderNum())
                .count(reqSewing.getCount())
                .chipCount(reqSewing.getChipCount())
                .cleaningCount(reqSewing.getCleaningCount())
                .buttonOpenCount(reqSewing.getButtonOpenCount())
                .yarnOpenCount(reqSewing.getYarnOpenCount())
                .blueLabel(reqSewing.getBlueLabel())
                .yellowChip(reqSewing.getYellowChip())
                .plankDrawing(reqSewing.getPlankDrawing())
                .packBag(reqSewing.getPackBag())
                .artMat(reqSewing.getArtMat())
                .article(articleRepo.findById(reqSewing.getArticleId()).orElseThrow())
                .worker(workerRepo.findById(reqSewing.getWorkerId()).orElseThrow())
                .build()));
    }

    @Override
    public HttpEntity<?> delete(Integer sewingId) {
        sewingRepo.deleteById(sewingId);
        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> edit(ReqSewing reqSewing, Integer sewingId) {
        return ResponseEntity.ok(sewingRepo.save(Sewing.builder()
                .id(sewingId)
                .artMat(reqSewing.getArtMat())
                .createdAt(reqSewing.getCreatedAt())
                .orderNum(reqSewing.getOrderNum())
                .count(reqSewing.getCount())
                .chipCount(reqSewing.getChipCount())
                .cleaningCount(reqSewing.getCleaningCount())
                .buttonOpenCount(reqSewing.getButtonOpenCount())
                .yarnOpenCount(reqSewing.getYarnOpenCount())
                .blueLabel(reqSewing.getBlueLabel())
                .yellowChip(reqSewing.getYellowChip())
                .plankDrawing(reqSewing.getPlankDrawing())
                .packBag(reqSewing.getPackBag())
                .article(articleRepo.findById(reqSewing.getArticleId()).orElseThrow())
                .worker(workerRepo.findById(reqSewing.getWorkerId()).orElseThrow())
                .build()));
    }
}
