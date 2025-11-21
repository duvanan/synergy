package org.example.synergy.dto.response;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ResultPageResponse<T> {
    private Long totalItems;

    private Integer totalPages;

    private Integer pageSize;

    private Integer currentPage;

    private List<T> items;

    public ResultPageResponse(Long totalItems, Integer totalPages, Integer pageSize, Integer currentPage, List<T> items) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.items = items;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}