package com.redeemerlives.products_service.dto;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PageResponse <T> {
    private List<T> data;
    private Long totalElement;
    private int totalPages;
    private boolean isLast;
    private boolean isFirst;
}
