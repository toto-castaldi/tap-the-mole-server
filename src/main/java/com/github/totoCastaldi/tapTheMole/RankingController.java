package com.github.totoCastaldi.tapTheMole;

import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.POST;
import org.rapidoid.http.Req;

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
    public Collection<String> endGame(Req req) {
        final Map.Entry<String, Object> param = req.posted().entrySet().iterator().next();
        ranking.newRank(param.getKey(), Integer.valueOf(String.valueOf(param.getValue())));
        return ranking.asList();
    }
}
