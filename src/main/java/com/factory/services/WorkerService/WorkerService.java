package com.factory.services.WorkerService;

import com.factory.payload.request.ReqWorker;
import org.springframework.http.HttpEntity;

public interface WorkerService {
    HttpEntity<?> get(String search, Integer page, Integer offset);

    HttpEntity<?> create(ReqWorker reqWorker);

    HttpEntity<?> delete(Integer workerId);

    HttpEntity<?> edit(ReqWorker reqWorker, Integer workerId);
}
