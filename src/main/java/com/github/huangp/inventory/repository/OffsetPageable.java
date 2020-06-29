package com.github.huangp.inventory.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OffsetPageable implements Pageable {

    private final Sort sort;
    private int limit = 0;
    private int offset = 0;

    public OffsetPageable(int skip, int limit) {
        if (skip < 0) {
            throw new IllegalArgumentException("Skip must not be less than zero!");
        }

        if (limit < 0) {
            throw new IllegalArgumentException("limit must not be less than zero!");
        }

        this.limit = limit;
        this.offset = skip;
        this.sort = Sort.by(Sort.Direction.ASC, "releaseDate");
    }

    @Override
    public int getPageNumber() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return this;
    }

    @Override
    public Pageable first() {
        return this;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

}
