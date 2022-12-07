package vn.com.fpt.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddLog extends BaseLog {

    private String newValue;

    public AddLog(String tableName, Long id, String property, String newValue, String username) {
        super(tableName, id, property, username);
        this.newValue = newValue;
    }


}
