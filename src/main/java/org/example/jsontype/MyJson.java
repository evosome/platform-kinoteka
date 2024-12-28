package org.example.jsontype;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyJson implements Serializable {

    private long number;
    private List<Integer> rowMargins;
    private List<List<Seat>> seatRows;

    @Getter
    @Setter
    public static class Seat implements Serializable {
        private String type;
        private int marginLeft;
    }
}
