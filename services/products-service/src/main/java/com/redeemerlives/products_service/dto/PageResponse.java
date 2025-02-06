package com.redeemerlives.products_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PageResponse <T> {
    private List<T> data;
    private Long totalElement;
    private int totalPages;
    private boolean isLast;
    private boolean isFirst;
}
