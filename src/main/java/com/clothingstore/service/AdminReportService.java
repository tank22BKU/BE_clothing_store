package com.clothingstore.service;

import com.clothingstore.dto.response.ProductSalesReportDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminReportService {

    private final EntityManager entityManager;

    /**
     * Báo cáo doanh thu sản phẩm - Gọi stored procedure GetProductSalesReport
     */
    @Transactional
    public List<ProductSalesReportDto> getProductSalesReport(
            LocalDate startDate,
            LocalDate endDate,
            BigDecimal minRevenue) {

        log.info("Generating product sales report - startDate: {}, endDate: {}, minRevenue: {}",
                startDate, endDate, minRevenue);

        validateDateRange(startDate, endDate);

        try {
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("GetProductSalesReport")
                    .registerStoredProcedureParameter("p_startDate", LocalDate.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_endDate", LocalDate.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_minRevenue", BigDecimal.class, ParameterMode.IN);

            query.setParameter("p_startDate", startDate);
            query.setParameter("p_endDate", endDate);
            query.setParameter("p_minRevenue", minRevenue != null ? minRevenue : BigDecimal.ZERO);

            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();
            List<ProductSalesReportDto> reports = new ArrayList<>();

            for (Object[] row : results) {
                ProductSalesReportDto dto = ProductSalesReportDto.builder()
                        .productId(row[0] != null ? row[0].toString() : null)
                        .productName(row[1] != null ? row[1].toString() : null)
                        .totalQuantitySold(convertToInteger(row[2]))
                        .totalRevenue(row[3] != null ? (BigDecimal) row[3] : BigDecimal.ZERO)
                        .averagePrice(row[4] != null ? (BigDecimal) row[4] : BigDecimal.ZERO)
                        .build();
                reports.add(dto);
            }

            log.info("Generated report with {} products", reports.size());

            return reports;

        } catch (Exception e) {
            log.error("Error generating product sales report", e);
            throw new RuntimeException("Failed to generate sales report: " + e.getMessage(), e);
        }
    }

    // ===== HELPER METHODS =====

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (ObjectUtils.isEmpty(startDate) || ObjectUtils.isEmpty(endDate)) {
            throw new IllegalArgumentException("Start date and end date are required");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }

        if (startDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the future");
        }
    }

    private Integer convertToInteger(Object obj) {
        if (ObjectUtils.isEmpty(obj)) {
            return 0;
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        try {
            return Integer.parseInt(obj.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
