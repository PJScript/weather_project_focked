package com.example.demo.weather.dto.mid_ta;

import java.util.List;
import lombok.Data;

@Data
public class MidTaItems {
    private List<MidTaItem> item;
    private int pageNo;
    private int numOfRows;
    private int totalCount;
}
