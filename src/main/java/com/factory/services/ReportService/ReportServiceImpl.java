package com.factory.services.ReportService;

import com.factory.entity.ReportPie;
import com.factory.payload.request.ReqPieReport;
import com.factory.projections.StoreProjection;
import com.factory.repository.ReportPieRepo;
import com.factory.repository.ReportRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepo reportRepo;
    private final ReportPieRepo reportPieRepo;

    @Override
    public HttpEntity<?> getSecondaryReports(String search, Integer page, Integer offset, LocalDate date) {
        Pageable pageable = PageRequest.of(page - 1, offset);
        return ResponseEntity.ok(reportRepo.getSecondaryReports(pageable, search, date ));
    }

    @Override
    public HttpEntity<?> getLeftAmountReport(String search, Integer page, Integer offset, LocalDate date) {
        Pageable pageable = PageRequest.of(page - 1, offset);

        Page<StoreProjection> leftAmount = reportRepo.getLeftAmount(pageable, search,date );
        return ResponseEntity.ok(leftAmount);
    }

    @Override
    public HttpEntity<?> post(ReqPieReport reqPieReport) {
        int monthValue = reqPieReport.getCreatedAt().getMonthValue();
        int year = reqPieReport.getCreatedAt().getYear();
        List<ReportPie> all = reportPieRepo.findAll();

        Integer x = null;
        for (ReportPie reportPie : all) {
            if (reportPie.getCreatedAt().getMonthValue() == monthValue && reportPie.getCreatedAt().getYear() == year) {
                x = reportPie.getId();
            }
        }
        if (x == null) {
            reportPieRepo.save(ReportPie.builder()
                    .createdAt(reqPieReport.getCreatedAt())
                    .dateNumChoy(reqPieReport.getDateNumChoy())
                    .choyAm(reqPieReport.getChoyAm())
                    .dateNumSal(reqPieReport.getDateNumSal())
                    .salAm(reqPieReport.getSalAm())
                    .dateNumSumka(reqPieReport.getDateNumSumka())
                    .sumkaAm(reqPieReport.getSumkaAm())
                    .monthDateNum(reqPieReport.getMonthDateNum())
                    .build());
        } else {
            reportPieRepo.save(ReportPie.builder()
                    .id(x)
                    .createdAt(reqPieReport.getCreatedAt())
                    .dateNumChoy(reqPieReport.getDateNumChoy())
                    .choyAm(reqPieReport.getChoyAm())
                    .dateNumSal(reqPieReport.getDateNumSal())
                    .salAm(reqPieReport.getSalAm())
                    .dateNumSumka(reqPieReport.getDateNumSumka())
                    .sumkaAm(reqPieReport.getSumkaAm())
                    .monthDateNum(reqPieReport.getMonthDateNum())
                    .build());
        }
        return ResponseEntity.ok("");
    }
}
