package vn.com.fpt.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseSpecification<T> implements Specification<T> {

    private final List<SearchCriteria> list;

    public BaseSpecification() {
        list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicateList = new ArrayList<>();
        for (SearchCriteria criteria : list) {
            String key = criteria.key();
            Object value = criteria.value();
            Path<Object> path = root.get(key);

            switch (criteria.searchOperation()) {
                case GREATER_THAN:
                    if (value instanceof Date date) {
                        predicateList.add(builder.greaterThan(path.as(Date.class), date));
                    } else {
                        predicateList.add(builder.greaterThan(path.as(String.class), value.toString()));
                    }
                    break;
                case LESS_THAN:
                    if (value instanceof Date date) {
                        predicateList.add(builder.lessThan(path.as(Date.class), date));
                    } else {
                        predicateList.add(builder.lessThan(path.as(String.class), value.toString()));
                    }
                    break;
                case GREATER_THAN_EQUAL:
                    if (value instanceof Date date) {
                        predicateList.add(builder.greaterThanOrEqualTo(path.as(Date.class), date));
                    } else {
                        predicateList.add(builder.greaterThanOrEqualTo(path.as(String.class), value.toString()));
                    }
                    break;
                case LESS_THAN_EQUAL:
                    if (value instanceof Date date) {
                        predicateList.add(builder.lessThanOrEqualTo(path.as(Date.class), date));
                    } else {
                        predicateList.add(builder.lessThanOrEqualTo(path.as(String.class), value.toString()));
                    }
                    break;
                case NOT_EQUAL:
                    predicateList.add(builder.notEqual(path, value));
                    break;
                case EQUAL:
                    predicateList.add(builder.equal(path, value));
                    break;
                case MATCH, LIKE:
                    predicateList.add(builder.like(builder.lower(path.as((String.class))), String.format("%%%s%%", value.toString().strip().toLowerCase())));
                    break;
                case MATCH_BEGIN:
                    predicateList.add(builder.like(builder.lower(path.as((String.class))), String.format("%%%s", value.toString().strip().toLowerCase())));
                    break;
                case MATCH_END:
                    predicateList.add(builder.like(builder.lower(path.as((String.class))), String.format("%s%%", value.toString().strip().toLowerCase())));
                    break;
                case IN:
                    CriteriaBuilder.In<Object> in = builder.in(path);
                    List<Object> params = (List<Object>) criteria.value();
                    params.forEach(in::value);
                    params.add(in);
                    break;
                default:
                    break;
            }
        }
        return builder.and(predicateList.toArray(new Predicate[0]));
    }

}
