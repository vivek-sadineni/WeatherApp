package com.bwap.weatherapp.WeatherApp.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;


import java.io.IOException;
@Service
public class WeatherService {

    private JSONObject Data;
    private OkHttpClient client;
    private Response response;
    private String cityName;
    private String aqi;

    private String API = "1d00b3f8b3135dc19ddd8fd101c84cb524d31486";

    public JSONObject getWeather(){
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.waqi.info/feed/"+cityName+"/?token=1d00b3f8b3135dc19ddd8fd101c84cb524d31486")
                .build();
        ///feed/:city/?token=:token
        try{
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        }
        catch(IOException | JSONException ie){
            ie.printStackTrace();
        }

        return null;
    }

    //Here we are test running for different arrays in the returned JSON array;

    public JSONArray returnWeatherArray() throws JSONException {
        JSONArray WeatherArray = getWeather().getJSONArray("attributions");
        return WeatherArray;
    }

    //Here we are test running for different arrays in the returned JSON object;
    public JSONObject returnData() throws JSONException {
        JSONObject Data = getWeather().getJSONObject("data");
        return Data;
    }


    public JSONObject returnInstantAQI() throws JSONException {
        JSONObject InstantAQI = returnData().getJSONObject("iaqi");
        return InstantAQI;
    }



    public JSONObject returnDominantPollutant() throws JSONException {
        JSONObject DominantPollutant = returnData().getJSONObject("time");
        return DominantPollutant;
    }

    public JSONObject returnNearestStation() throws JSONException {
        JSONObject NearestStation = returnData().getJSONObject("city");
        return NearestStation;
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }
}
