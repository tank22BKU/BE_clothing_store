package com.clothingstore.service;

import com.clothingstore.dto.response.OrderListResponse;
import com.clothingstore.dto.response.OrderResponse;
import com.clothingstore.dto.response.OrderSummaryDto;
import com.clothingstore.entity.Order;
import com.clothingstore.entity.OrderDetails;
import com.clothingstore.repository.OrderDetailsRepository;
import com.clothingstore.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminOrderService {

    private final EntityManager entityManager;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    /**
     * Lấy danh sách đơn hàng - Gọi stored procedure GetOrdersByCustomer
     */

    @Transactional
    public OrderListResponse getOrders(String customerId, String status, String sortBy) {
        log.info("Fetching orders - customerId: {}, status: {}, sortBy: {}", customerId, status, sortBy);

        try {
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("GetOrdersByCustomer")
                    .registerStoredProcedureParameter("p_customerId", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_status", String.class, ParameterMode.IN);

            query.setParameter("p_customerId", customerId);
            query.setParameter("p_status", status);
            //query.setParameter("p_sortBy", sortBy != null ? sortBy : "date_desc");

            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();
            List<OrderSummaryDto> orders = new ArrayList<>();

            for (Object[] row : results) {
                String orderId = String.valueOf(row[0]);

                int itemCount = countNumberOfItemsBelongToOrder(orderId);

                OrderSummaryDto dto = OrderSummaryDto.builder()
                        .orderId(row[0] != null ? row[0].toString() : null)
                        .customerName(row[5] != null ? row[5].toString() : null)
                        .orderDate(convertToLocalDateTime(row[1]))
                        .status(row[2] != null ? row[2].toString() : null)
                        .totalAmount(row[3] != null ? (BigDecimal) row[3] : BigDecimal.ZERO)
                        .itemsCount(itemCount)
                        .build();
                orders.add(dto);
            }

            log.info("Found {} orders", orders.size());

            return OrderListResponse.builder()
                    .totalOrders(orders.size())
                    .orders(orders)
                    .build();

        } catch (Exception e) {
            log.error("Error fetching orders", e);
            throw new RuntimeException("Failed to fetch orders: " + e.getMessage(), e);
        }
    }

    /**
     * Cập nhật trạng thái đơn hàng - Gọi stored procedure UpdateOrderStatus
     */
    @Transactional
    public void updateOrderStatus(String orderId, String newStatus) {
        log.info("Updating order status - orderId: {}, newStatus: {}", orderId, newStatus);

        validateOrderExists(orderId);
        validateOrderStatus(newStatus);

        try {
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("UpdateOrderStatus")
                    .registerStoredProcedureParameter("p_orderId", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_newStatus", String.class, ParameterMode.IN);

            query.setParameter("p_orderId", orderId);
            query.setParameter("p_newStatus", newStatus);

            query.execute();
            log.info("Order status updated successfully - orderId: {}", orderId);

        } catch (Exception e) {
            log.error("Error updating order status - orderId: {}", orderId, e);

            // Bắt lỗi business logic từ stored procedure
            String errorMessage = e.getMessage();
            if (errorMessage != null && errorMessage.contains("Cannot cancel an order that has already been delivered")) {
                throw new IllegalArgumentException("Cannot cancel an order that has already been delivered.");
            }
            throw new RuntimeException("Failed to update order status: " + errorMessage, e);
        }
    }

    /**
     * Xóa đơn hàng - Gọi stored procedure DeleteOrder
     */
    @Transactional
    public void deleteOrder(String orderId) {
        log.info("Deleting order: {}", orderId);

        validateOrderExists(orderId);

        try {
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("DeleteOrder")
                    .registerStoredProcedureParameter("p_orderId", String.class, ParameterMode.IN);

            query.setParameter("p_orderId", orderId);

            query.execute();
            log.info("Order deleted successfully: {}", orderId);

        } catch (Exception e) {
            log.error("Error deleting order: {}", orderId, e);
            throw new RuntimeException("Failed to delete order: " + e.getMessage(), e);
        }
    }

    // ===== HELPER METHODS =====

    private void validateOrderExists(String orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
    }

    private void validateOrderStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Order status cannot be empty");
        }

        // Validate status values
        List<String> validStatuses = List.of("pending", "processing", "delivered", "cancelled", "returned");
        if (!validStatuses.contains(status.toLowerCase())) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }
    }

    private LocalDateTime convertToLocalDateTime(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Timestamp) {
            return ((Timestamp) obj).toLocalDateTime();
        } else if (obj instanceof LocalDateTime) {
            return (LocalDateTime) obj;
        } else if (obj instanceof java.sql.Date) {
            return ((java.sql.Date) obj).toLocalDate().atStartOfDay();
        }
        return null;
    }

    private Integer convertToInteger(Object obj) {
        if (obj == null) {
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

    private int countNumberOfItemsBelongToOrder(String orderId) {
        List<OrderDetails> listOrderDetails = orderDetailsRepository.findByOrderId(orderId);
        int numberOfItems = 0;
        for(OrderDetails orderDetails : listOrderDetails) {
            numberOfItems = numberOfItems + (ObjectUtils.isEmpty(orderDetails.getQuantity()) ? 0 : orderDetails.getQuantity());
        }

        return numberOfItems;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> new OrderResponse(
                        order.getOrderId(),
                        order.getStatus(),
                        order.getTotalAmount(),
                        order.getOrderDate(),
                        order.getCustomer().getCustomerId(),
                        order.getEmployee().getEmployeeId(),
                        order.getPayment().getPaymentId()
                ))
                .toList();
    }
}