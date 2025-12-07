package com.clothingstore.controller;

import com.clothingstore.dto.response.ProductSalesReportDto;
import com.clothingstore.service.AdminReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/reports")
@RequiredArgsConstructor
@Slf4j
public class AdminReportController {

    private final AdminReportService reportService;

    /**
     * Báo cáo doanh thu sản phẩm
     * GET /api/admin/reports/revenue?startDate=2024-01-01&endDate=2024-02-01&minRevenue=1000000
     */
    @GetMapping("/revenue")
    public ResponseEntity<List<ProductSalesReportDto>> getProductSalesReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "0") BigDecimal minRevenue) {

        log.info("GET /api/admin/reports/revenue - startDate: {}, endDate: {}, minRevenue: {}",
                startDate, endDate, minRevenue);

        try {
            List<ProductSalesReportDto> report = reportService.getProductSalesReport(
                    startDate, endDate, minRevenue);

            return ResponseEntity.ok(report);

        } catch (IllegalArgumentException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ArrayList<>());

        } catch (Exception e) {
            log.error("Error generating report", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ArrayList<>());
        }
    }
}