package com.redeemerlives.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse <T> {
    private List<T> data;
    private boolean isFirst;
    private boolean isLast;
    private Long totalElements;
    private int totalPages;
}
