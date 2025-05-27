package net.edigest.journal.service;

import lombok.Getter;
import lombok.Setter;
import net.edigest.journal.api.response.WeatherResponse;
import net.edigest.journal.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Getter
@Setter
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

//    private final static String apiKey="08b85bbcc48d1bb141d60f446515fd08";
//    private static final String API="http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private AppCache appCache;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RedisService redisService;

    public WeatherResponse getWeather(String city)
    {
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if(weatherResponse!=null)
        {
            return weatherResponse;
        }
        else {
            String finalAPI=appCache.app_cache.get("weather_api").replace("<city>",city).replace("<api_key>",apiKey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if(body!=null)
            {
                redisService.set("weather_of_"+city,body,3l);
            }
            return body;
        }
    }
}
