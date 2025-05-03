package net.edigest.journal.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class WeatherResponse{
//    private Request request;
//    private Location location;

    private Current current;

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    @Getter
    @Setter
    public class Current{


//        @JsonProperty("observation_time")
//        private String observationTime;
        private int temperature;

//        @JsonProperty("weather_code")
//        private int weatherCode;
//        @JsonProperty("weather_icons")
//        private List<String> weatherIcons;

        @JsonProperty("weather_descriptions")
        private ArrayList<String> weatherDescriptions;

        public ArrayList<String> getWeatherDescriptions() {
            return weatherDescriptions;
        }

        public void setWeatherDescriptions(ArrayList<String> weatherDescriptions) {
            this.weatherDescriptions = weatherDescriptions;
        }

        public int getFeelslike() {
            return feelslike;
        }

        public void setFeelslike(int feelslike) {
            this.feelslike = feelslike;
        }

        public int getTemperature() {
            return temperature;
        }

        public void setTemperature(int temperature) {
            this.temperature = temperature;
        }
//        @JsonProperty("wind_speed")
//        private int windSpeed;

        private int feelslike;

    }

}




