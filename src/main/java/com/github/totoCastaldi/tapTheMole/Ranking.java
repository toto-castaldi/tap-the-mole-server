package com.github.totoCastaldi.tapTheMole;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import lombok.Synchronized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by toto on 24/03/16.
 */
@Singleton
public class Ranking {

    private final List<RankingInfo> ranks;
    private final int pageSize;

    public Ranking() {
        this(4);
    }

    public Ranking(int pageSize) {
        this.pageSize = pageSize;
        this.ranks = Lists.newArrayList();
    }

    public Collection<RankingInfo> asList(int pageNumber) {
        return this.ranks.stream()
                .skip(pageNumber * pageSize)
                .limit(pageSize)
                .collect(Collectors.toCollection(ArrayList::new));

    }

    @Synchronized
    public int newRank(String name, Integer score) {
        final RankingInfo rankingInfo = new RankingInfo(name, score);
        this.ranks.add(rankingInfo);
        Collections.sort(this.ranks, new Comparator<RankingInfo>() {
            @Override
            public int compare(RankingInfo o1, RankingInfo o2) {
                return o2.getScore() - o1.getScore();
            }
        });
        int index = this.ranks.indexOf(rankingInfo);
        return index / this.pageSize;
    }

    public Collection<RankingInfo> all() {
        return Collections.unmodifiableCollection(this.ranks);
    }

    public Integer pageCount() {
        final double rounded = Math.ceil(this.ranks.size() / this.pageSize);
        final double div = (double) this.ranks.size() / (double) this.pageSize;
        if (rounded == div) {
            return (int) rounded;
        } else {
            return 1 + (int) rounded;
        }
    }
}
