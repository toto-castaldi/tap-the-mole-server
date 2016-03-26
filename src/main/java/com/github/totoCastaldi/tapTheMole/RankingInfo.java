package com.github.totoCastaldi.tapTheMole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Created by toto on 24/03/16.
 */
@Data
@RequiredArgsConstructor
public class RankingInfo {
    private final String name;
    private final Integer score;
    private Integer position;
}
