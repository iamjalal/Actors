package com.paytouch.jalal.actors.util;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.paytouch.jalal.actors.model.Actor;

import java.util.List;

/**
 * Created by jalals on 1/8/2015.
 */
public class ActorSearch {

    public static List<Actor> filter(List<Actor> actors, final String name, final String location,
                                     final boolean top, final int popularity) {

        Predicate<Actor> byName = new Predicate<Actor>() {
            @Override
            public boolean apply(Actor input) {

                if(name == null || "".equals(name)) {
                    return true;
                }

                return input.name.contains(name);
            }
        };

        Predicate<Actor> byLocation = new Predicate<Actor>() {
            @Override
            public boolean apply(Actor input) {

                if(location == null || "".equals(location)) {
                    return true;
                }

                return input.location.equals(location);
            }
        };

        Predicate<Actor> byTop = new Predicate<Actor>() {
            @Override
            public boolean apply(Actor input) {
                return input.top == top;
            }
        };

        Predicate<Actor> byPopularity = new Predicate<Actor>() {
            @Override
            public boolean apply(Actor input) {
                return input.popularity >= popularity;
            }
        };

        List<Actor> filteredActors = FluentIterable.from(actors)
                .filter(byName)
                .filter(byLocation)
                .filter(byTop)
                .filter(byPopularity)
                .toList();

        return filteredActors;
    }
}
