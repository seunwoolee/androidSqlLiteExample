package com.example.memo;

public class Weather {
    private String country;
    private String weather;
    private String temperture;

    public Weather(String country, String weather, String temperture){
        this.country = country;
        this.weather = weather;
        this.temperture = temperture;
    }

    public String getTemperture() {
        return temperture;
    }

    public void setTemperture(String temperture) {
        this.temperture = temperture;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "country='" + country + '\'' +
                ", weather='" + weather + '\'' +
                ", temperture='" + temperture + '\'' +
                '}';
    }
}
