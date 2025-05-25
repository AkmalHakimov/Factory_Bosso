package com.factory.services.BoxService;

import com.factory.payload.request.ReqBox;
import org.springframework.http.HttpEntity;

import java.time.LocalDate;

public interface BoxService {
    HttpEntity<?> get(String search, Integer page, Integer offset, LocalDate date);

    HttpEntity<?> create(ReqBox reqBox);

    HttpEntity<?> delete(Integer boxId);

    HttpEntity<?> edit(ReqBox reqBox, Integer boxId);
}
