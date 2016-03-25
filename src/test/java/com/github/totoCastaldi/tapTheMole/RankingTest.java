package com.github.totoCastaldi.tapTheMole;

import com.google.common.collect.Iterables;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by toto on 25/03/16.
 */
public class RankingTest {

    private Ranking ranking;
    private int pageSize;
    private String name;
    private Integer score;

    @Before
    public void setup () {
        this.score = 10;
        this.name = "toto";
        this.pageSize = 3;
        this.ranking = new Ranking(pageSize);
    }

    @Test
    public void onePage() {
        for (int i = 0; i < pageSize; i++) {
            int page = ranking.newRank(name, score);
            assertThat(page, equalTo(0));
        }
    }

    @Test
    public void scoreHigher() {
        for (int i = 0; i < pageSize; i++) {
            ranking.newRank(name, score);
        }

        int page = ranking.newRank(name, score + 1);
        assertThat(page, equalTo(0));

        final RankingInfo info = ranking.asList(0).iterator().next();
        assertThat(info.getScore(), equalTo(score + 1));

        Collection<RankingInfo> page0 = ranking.asList(1);
        for (RankingInfo rankingInfo : page0) {
            assertThat(rankingInfo.getScore(), equalTo(score));
        }
    }

    @Test
    public void scoreLower() {
        for (int i = 0; i < pageSize; i++) {
            ranking.newRank(name, score);
        }

        int page = ranking.newRank(name, score - 1);
        assertThat(page, equalTo(1));

        Collection<RankingInfo> page0 = ranking.asList(0);
        for (RankingInfo rankingInfo : page0) {
            assertThat(rankingInfo.getScore(), equalTo(score));
        }

        final RankingInfo last = Iterables.getLast(ranking.asList(1));
        assertThat(last.getScore(), equalTo(score - 1));
    }

    @Test
    public void all() {
        for (int i = 0; i < pageSize * 2; i++) {
            ranking.newRank(name, score);
        }
        Collection<RankingInfo> all = ranking.all();

        assertThat(all, hasSize(pageSize * 2));
    }

    @Test
    public void pageCount() {
        for (int i = 0; i < pageSize - 1; i++) {
            ranking.newRank(name, score);
        }
        assertThat(ranking.pageCount(), equalTo(1));
        ranking.newRank(name, score);
        assertThat(ranking.pageCount(), equalTo(1));
        for (int i = 0; i < pageSize; i++) {
            ranking.newRank(name, score);
        }
        assertThat(ranking.pageCount(), equalTo(2));
        ranking.newRank(name, score);
        assertThat(ranking.pageCount(), equalTo(3));
    }

}