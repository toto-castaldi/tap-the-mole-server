package com.github.totoCastaldi.tapTheMole;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by toto on 24/03/16.
 */
@Singleton
public class Ranking {

    private final List<RankingInfo> ranks;

    public Ranking() {
        this.ranks = Lists.newArrayList();
    }

    public Collection<String> asList() {
        Collections.sort(this.ranks, new Comparator<RankingInfo>() {
            @Override
            public int compare(RankingInfo o1, RankingInfo o2) {
                return o2.getScore() - o1.getScore();
            }
        });
        return Collections2.transform(this.ranks, new Function<RankingInfo, String>() {
            @Nullable
            @Override
            public String apply(@Nullable RankingInfo input) {
                return StringUtils.leftPad(String.valueOf(input.getScore()), 3, "0") + " - " + input.getName();

            }
        });
    }

    public void newRank(String name, Integer score) {
        this.ranks.add(new RankingInfo(name, score));
    }
}
