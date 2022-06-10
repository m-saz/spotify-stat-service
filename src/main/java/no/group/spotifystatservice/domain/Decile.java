package no.group.spotifystatservice.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
public class Decile {
    private BigDecimal min;
    private BigDecimal max;
    private int count;

    public void incrementCount(){
        count++;
    }
}
