package com.github.totoCastaldi.tapTheMole;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.apache.commons.lang.StringUtils;
import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.GET;
import org.rapidoid.annotation.POST;
import org.rapidoid.http.Req;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

/**
 * Created by toto on 24/03/16.
 */
@Controller
public class RankingController {

    private final Ranking ranking;

    public RankingController() {
        this.ranking = Main.injector.getInstance(Ranking.class);
    }

    @POST
    public Integer endGame(Req req) {
        final Map.Entry<String, Object> param = req.posted().entrySet().iterator().next();
        return ranking.newRank(param.getKey(), Integer.valueOf(String.valueOf(param.getValue())));
    }

    @GET
    public Collection<String> page(Integer pageNumber) {
        return transform(ranking.asList(pageNumber));
    }

    @GET
    public Collection<String> all() {
        return transform(ranking.all());
    }

    @GET
    public Integer pageCount() {
        return ranking.pageCount();
    }

    private Collection<String> transform(Collection<RankingInfo> rankingInfos) {
        return Collections2.transform(rankingInfos, new Function<RankingInfo, String>() {

            @Nullable
            @Override
            public String apply(@Nullable RankingInfo input) {
                return StringUtils.leftPad(String.valueOf(input.getPosition()), 3, "0") + " - " + StringUtils.leftPad(String.valueOf(input.getScore()), 3, "0") + " - " + input.getName();

            }
        });
    }
}
