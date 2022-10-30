package vn.com.fpt.common.logger;

import lombok.Data;

@Data
public class RequestInfo {
    private String requestMethod;

    private Object requestQuery;

    private String requestPath;

    private Integer responseStatus;
}