package vn.com.fpt.common.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import net.logstash.logback.marker.Markers;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Marker;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Component
@Slf4j
public class RequestLogger {
    @Resource
    private ObjectMapper objectMapper;

    @Pointcut("execution(public * vn.com.fpt.controller.*.*(..)))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {

    }

    @AfterReturning(value = "webLog()", returning = "ret")
    public void doAfterReturning(Object ret) {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object response = joinPoint.proceed();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return response;
        }
        HttpServletRequest request = attributes.getRequest();

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        ContentCachingRequestWrapper termRequest = new ContentCachingRequestWrapper(request);

        this.log(joinPoint, null, termRequest, method, response);
        return response;
    }

    private void log(ProceedingJoinPoint joinPoint, SecurityContext securityContext, HttpServletRequest request, Method method, Object response) {
        RequestInfo requestInfo = this.getRequestInfo(joinPoint, request, method, response);
        // Marker for log
        Map<String, Object> logMap = new HashMap<>();
        Map<String, Object> header = new HashMap<>();
        this.addRequestHeadersToMarker(logMap, header, securityContext, request);
        this.addRequestInfoToMarker(logMap, requestInfo);

        LogstashMarker marker = Markers.appendEntries(logMap);
        String requestBody = "";
        try {
            requestBody = request.getReader().lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ResponseEntity<Void> res = (ResponseEntity) response;

        Gson gson = new Gson();
        String jsonStr = gson.toJson(response);
        String message = String.format("Done %s to %s with status %s\nRequest body: \n%s\nResponse body: \n%s",
                requestInfo.getRequestMethod(),
                requestInfo.getRequestPath(),
                requestInfo.getResponseStatus(),
                requestBody,
                jsonStr
        );
        doLog(marker, requestInfo, message);
    }

    public static String[] flattenMap(Map<String, List<String>> m) {
        return m.entrySet().stream().flatMap(x ->
                x.getValue().stream().flatMap(y -> Stream.of(x.getKey(), y)))
                .toArray(String[]::new);
    }

    @NotNull
    private RequestInfo getRequestInfo(
            ProceedingJoinPoint joinPoint,
            HttpServletRequest request,
            Method method,
            Object response
    ) {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setRequestMethod(request.getMethod());
        requestInfo.setRequestQuery(this.getParameter(method, joinPoint.getArgs()));
        requestInfo.setRequestPath(request.getRequestURI());

        if (response instanceof ResponseEntity) {
            ResponseEntity res = (ResponseEntity) response;
            requestInfo.setResponseStatus(res.getStatusCodeValue());

//            requestInfo.setResponseStatus(null);
        }

        return requestInfo;
    }

    private void addRequestInfoToMarker(Map<String, Object> logMap, RequestInfo requestInfo) {
        logMap.put("url", requestInfo.getRequestPath());
        logMap.put("method", requestInfo.getRequestMethod());
        logMap.put("parameter", requestInfo.getRequestQuery());
    }

    private void addRequestHeadersToMarker(
            Map<String, Object> logMarker, Map<String, Object> header,
            SecurityContext securityContext, HttpServletRequest httpServletRequest) {

        var headerName = httpServletRequest.getHeaderNames();
        String headerNameMapping = null;
        logMarker.put("account", "thanhdang");
        header.put("account", "thanhdang");
        if (headerName != null) {
            while (headerName.hasMoreElements()) {
                headerNameMapping = headerName.nextElement();
                logMarker.put(headerNameMapping, httpServletRequest.getHeader(headerNameMapping));
                header.put(headerNameMapping, httpServletRequest.getHeader(headerNameMapping));
            }
        }
    }

    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }

            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();

                if (StringUtils.isNotEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.isEmpty()) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }

    private void doLog(Marker marker, RequestInfo requestInfo, String message) {
        Integer status = requestInfo.getResponseStatus();
        if (status >= 500) {
            log.error(marker, message);
        } else if (status >= 400) {
            log.warn(marker, message);
        } else {
            log.info(marker, message);
        }
    }
}

