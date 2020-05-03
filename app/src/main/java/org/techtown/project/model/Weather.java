package org.techtown.project.model;

import java.util.ArrayList;

public class Weather {

    public String timezone;
    public Current current;
    public ArrayList<Daily> daily;
    public ArrayList<Hourly> hourly;

    public static class Current {
        public Long dt;
        public Long sunrise;
        public Long sunset;
        public Double temp;
        public Double feels_like;
        public Integer pressure;
        public Integer humidity;
        public Double dew_point;
        public Double uvi;
        public Integer clouds;
        public Integer visibility;
        public Double wind_speed;
        public Integer wind_deg;
    }

    public static class Daily {
        public Long dt;
        public Long sunrise;
        public Long sunset;
        public Temp temp;
    }

    public static class Hourly {
//         "dt": 1588406400,
//                 "temp": 23.99,
//                 "feels_like": 22.92,
//                 "pressure": 1010,
//                 "humidity": 64,
//                 "dew_point": 16.77,
//                 "clouds": 90,
//                 "wind_speed": 4.78,
//                 "wind_deg": 264,
        public Long dt;
        public Double temp;
        public Double feels_like;
    }

    public static class Temp {
        public Double day;
    }
}