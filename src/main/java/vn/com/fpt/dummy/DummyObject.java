package vn.com.fpt.dummy;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DummyObject {
    private Integer id;
    private String room;
    private Integer floor;
    private boolean old;
    private boolean duplicate;
}
