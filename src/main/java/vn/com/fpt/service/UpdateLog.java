package vn.com.fpt.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UpdateLog extends BaseLog {
    public UpdateLog(String tableName, Long id, String property, String username, String oldValue, String newValue) {
        super(tableName, id, property, username);
        this.newValue = newValue;
        this.oldValue = oldValue;
    }

    private String oldValue;

    private String newValue;


}
