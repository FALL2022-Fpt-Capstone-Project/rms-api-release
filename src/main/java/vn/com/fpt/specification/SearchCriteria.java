package vn.com.fpt.specification;

import vn.com.fpt.common.constants.SearchOperation;

public record SearchCriteria(String key, Object value, SearchOperation searchOperation) {

    public static SearchCriteria of(String key, Object value, SearchOperation searchOperation) {
        return new SearchCriteria(key, value, searchOperation);
    }

}
