package com.mkurczuk.crypto.services.report;

import lombok.Data;

@Data
class Range<T> {
    private final T start;
    private final T end;
}