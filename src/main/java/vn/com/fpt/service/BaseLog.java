package vn.com.fpt.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseLog {
    private String tableName;
    private Long id;
    private String property;
    private String username;

}
