package com.clothingstore.controller;

import com.clothingstore.dto.request.UpdateOrderStatusRequest;
import com.clothingstore.dto.response.OrderListResponse;
import com.clothingstore.dto.response.OrderResponse;
import com.clothingstore.dto.response.StatusMessageResponse;
import com.clothingstore.entity.Order;
import com.clothingstore.service.AdminOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
@Slf4j
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @GetMapping()
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(adminOrderService.getAllOrders());
    }

    /**
     * Lấy danh sách đơn hàng với filter và sort
     * GET /api/admin/orders?customerId=USER001&status=pending&sortBy=date_desc
     */
    @GetMapping("filter")
    public ResponseEntity<OrderListResponse> getOrders(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "date_desc") String sortBy) {

        log.info("GET /api/admin/orders - customerId: {}, status: {}, sortBy: {}",
                customerId, status, sortBy);

        try {
            OrderListResponse response = adminOrderService.getOrders(customerId, status, sortBy);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(OrderListResponse.builder().totalOrders(0).orders(new ArrayList<>()).build());

        } catch (Exception e) {
            log.error("Error fetching orders", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(OrderListResponse.builder().totalOrders(0).orders(new ArrayList<>()).build());
        }
    }

    /**
     * Cập nhật trạng thái đơn hàng
     * PATCH /api/admin/orders/{orderId}/status
     */
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<StatusMessageResponse> updateOrderStatus(
            @PathVariable String orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request) {

        log.info("PATCH /api/admin/orders/{}/status - newStatus: {}", orderId, request.getStatus());

        try {
            adminOrderService.updateOrderStatus(orderId, request.getStatus());
            return ResponseEntity.ok(
                    StatusMessageResponse.builder().success(true).message("Order status updated to '" + request.getStatus() + "'").build());

        } catch (IllegalArgumentException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(StatusMessageResponse.builder().success(false).message("Cannot cancel an order that has already been delivered.").build());

        } catch (Exception e) {
            log.error("Error updating order status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(StatusMessageResponse.builder().success(false).message("Cannot cancel an order that has already been delivered.").build());
        }
    }

    /**
     * Xóa đơn hàng
     * DELETE /api/admin/orders/{orderId}
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<StatusMessageResponse> deleteOrder(@PathVariable String orderId) {

        log.info("DELETE /api/admin/orders/{}", orderId);

        try {
            adminOrderService.deleteOrder(orderId);
            return ResponseEntity.ok(StatusMessageResponse.builder().success(true).message("Order deleted").build());

        } catch (IllegalArgumentException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(StatusMessageResponse.builder().success(false).message("Order deletion failed").build());

        } catch (Exception e) {
            log.error("Error deleting order", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(StatusMessageResponse.builder().success(false).message("Order deletion failed").build());
        }
    }
}