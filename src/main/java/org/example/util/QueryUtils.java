package org.example.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.core.MultivaluedMap;

import java.util.*;

public class QueryUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static QueryOptions from(MultivaluedMap<String, String> params, String defaultSortField, int defaultTake) {
        QueryOptions options = new QueryOptions();
        options.skip = parseInt(params.getFirst("skip"), 0);
        options.take = parseInt(params.getFirst("take"), defaultTake);

        String sortField = params.getFirst("sortField");
        options.sortField = (sortField != null && !sortField.isBlank()) ? sortField : defaultSortField;
        String sortOrder = params.getFirst("sortOrder");
        options.sortOrder = "-1".equals(sortOrder) ? "desc" : "asc";

        String fParam = params.getFirst("f");
        if (fParam != null && !fParam.isBlank()) {
            options.filters = parseFilters(fParam);
        }
        return options;
    }

    private static int parseInt(String str, int def) {
        try {
            return str != null ? Integer.parseInt(str) : def;
        } catch (Exception e) {
            return def;
        }
    }

    private static List<QueryFilter> parseFilters(String json) {
        try {
            Map<String, Object> map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
            List<QueryFilter> list = new ArrayList<>();
            for (Map.Entry<String, Object> e : map.entrySet()) {
                String path = e.getKey().replace('_', '.');
                Object val = e.getValue();
                if (val instanceof List<?> arr && arr.size() >= 2) {
                    list.add(new QueryFilter(path, arr.get(0).toString(), arr.get(1)));
                } else {
                    list.add(new QueryFilter(path, "=", val));
                }
            }
            return list;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON in parameter `f`", e);
        }
    }

    public static <T> List<T> find(EntityManager em, Class<T> entityClass, QueryOptions options) {
        StringBuilder jpql = new StringBuilder("select e from " + entityClass.getSimpleName() + " e");
        Map<String, Object> params = new HashMap<>();
        if (!options.filters.isEmpty()) {
            jpql.append(" where ");
            int idx = 0;
            for (QueryFilter f : options.filters) {
                if (idx > 0) jpql.append(" and ");
                String param = "p" + idx;
                buildCondition(jpql, f, param, params);
                idx++;
            }
        }
        if (options.sortField != null && !options.sortField.isBlank()) {
            jpql.append(" order by e.").append(options.sortField.replace('_', '.')).append(' ').append(options.sortOrder);
        }

        jakarta.persistence.Query query = em.createQuery(jpql.toString(), entityClass);
        for (var entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        query.setFirstResult(options.skip);
        query.setMaxResults(options.take);
        return query.getResultList();
    }

    private static void buildCondition(StringBuilder jpql, QueryFilter f, String paramName, Map<String, Object> params) {
        String fieldPath = "e." + f.field;
        switch (f.operator) {
            case "=":
                jpql.append(fieldPath).append(" = :").append(paramName);
                params.put(paramName, f.value);
                break;
            case "!=":
                jpql.append(fieldPath).append(" <> :").append(paramName);
                params.put(paramName, f.value);
                break;
            case ">":
            case ">=":
            case "<":
            case "<=":
                jpql.append(fieldPath).append(' ').append(f.operator).append(" :").append(paramName);
                params.put(paramName, f.value);
                break;
            case "%%":
                jpql.append("lower(").append(fieldPath).append(") like :").append(paramName);
                params.put(paramName, "%" + f.value.toString().toLowerCase() + "%");
                break;
            case "!%":
                jpql.append("lower(").append(fieldPath).append(") not like :").append(paramName);
                params.put(paramName, "%" + f.value.toString().toLowerCase() + "%");
                break;
            default:
                throw new IllegalArgumentException("Operator \"" + f.operator + "\" for field \"" + f.field + "\" not supported.");
        }
    }
}
