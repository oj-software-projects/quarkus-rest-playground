package org.example.util;

public class QueryFilter {
    public final String field;
    public final String operator;
    public final Object value;

    public QueryFilter(String field, String operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }
}
