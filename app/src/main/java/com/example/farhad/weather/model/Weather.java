package com.example.farhad.weather.model;

import lombok.Data;

@Data
public class Weather {

    private Query query;


    @Data
    class Query {

        private Results results;

        @Data
        class Results {
            private Channel channel;
        }
    }

    public Channel getChannel() {
        return query.getResults().getChannel();
    }
}