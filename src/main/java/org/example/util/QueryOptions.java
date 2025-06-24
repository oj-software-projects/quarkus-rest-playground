package org.example.util;

import java.util.ArrayList;
import java.util.List;

public class QueryOptions {
    public int skip;
    public int take;
    public String sortField;
    public String sortOrder;
    public List<QueryFilter> filters = new ArrayList<>();
}
