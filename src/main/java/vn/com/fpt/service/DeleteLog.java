package vn.com.fpt.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteLog extends BaseLog {
    public DeleteLog(String tableName, Long id, String userName) {
        super(tableName, id, "", tableName);
    }
}
