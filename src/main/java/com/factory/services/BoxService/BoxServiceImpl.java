package com.factory.services.BoxService;

import com.factory.entity.Box;
import com.factory.payload.request.ReqBox;
import com.factory.repository.ArticleRepo;
import com.factory.repository.BoxRepo;
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
public class BoxServiceImpl implements BoxService {

    private final BoxRepo boxRepo;
    private final ArticleRepo articleRepo;
    private final WorkerRepo workerRepo;

    @Override
    public HttpEntity<?> get(String search, Integer page, Integer offset, LocalDate date) {
        Pageable pageable = PageRequest.of(page - 1, offset);
        return ResponseEntity.ok(boxRepo.getBoxes(search, pageable,date));
    }

    @Override
    public HttpEntity<?> create(ReqBox reqBox) {
        return ResponseEntity.ok(boxRepo.save(Box.builder()
                .createdAt(reqBox.getCreatedAt())
                .boxContentCount(reqBox.getBoxContentCount())
                .boxCount(reqBox.getBoxCount())
                .orderNum(reqBox.getOrderNum())
                .article(articleRepo.findById(reqBox.getArticleId()).orElseThrow())
                .worker(workerRepo.findById(reqBox.getWorkerId()).orElseThrow())
                .build()));
    }

    @Override
    public HttpEntity<?> delete(Integer boxId) {
        boxRepo.deleteById(boxId);
        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> edit(ReqBox reqBox, Integer boxId) {
        return ResponseEntity.ok(boxRepo.save(Box.builder()
                .id(boxId)
                .createdAt(reqBox.getCreatedAt())
                .boxContentCount(reqBox.getBoxContentCount())
                .boxCount(reqBox.getBoxCount())
                .orderNum(reqBox.getOrderNum())
                .article(articleRepo.findById(reqBox.getArticleId()).orElseThrow())
                .worker(workerRepo.findById(reqBox.getWorkerId()).orElseThrow())
                .build()));
    }
}
