package vn.com.fpt.common.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.com.fpt.security.domain.AccountAuthenticationDetail;

import java.util.Date;
import java.util.Objects;

@UtilityClass
public class Operator {
    public static Long operator() {
        if (Objects.isNull((SecurityContextHolder.getContext().getAuthentication())))
            return -1L;
        return ((AccountAuthenticationDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
    public static String operatorName() {
        if (Objects.isNull((SecurityContextHolder.getContext().getAuthentication())))
            return "undefied";
        return ((AccountAuthenticationDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public static Date time() {
        return DateUtils.now();
    }
}
