package vn.com.fpt.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Gender {

    UNKNOWN(0, "Chưa xác định"),
    MALE(1, "Nam"),
    FEMALE(2, "Nữ");

    private final Integer value;
    private final String name;

    public static Gender of(Integer value) {
        if (Objects.isNull(value)) return UNKNOWN;
        return Stream.of(Gender.values())
                .filter(gender -> gender.getValue().equals(value))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public static final List<Gender> LIST = Arrays.asList(Gender.values());

}