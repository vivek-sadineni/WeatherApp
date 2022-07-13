package com.bwap.weatherapp.WeatherApp.views;
import com.bwap.weatherapp.WeatherApp.controller.WeatherService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ClassResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@SpringUI(path = "")
public class MainView extends UI {

    private VerticalLayout mainLayout;
    private NativeSelect<String> pollutantSelect;
    private TextField cityName;
    private Button searchButton;
    private HorizontalLayout dashboard;
    private Label nearestCity;
    private Label dominantPollutant;
    private Label airQualityIndex;
    private HorizontalLayout mainDescription;
    private Label dominantPollutantMax;
    private Label O3conc;
    private Label COconc;
    private Label NO2conc;
    private Label PM25conc;
    private Label PM10conc;
    private Label SO2conc;
    private Label uviconc;

    //@Configuration
    //@EnableAutoConfiguration
    //@ComponentScan
    private final WeatherService weatherService;

    public MainView(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        mainLayout();
        setHeader();
        setLogo();
        setForm();
        dashboardTitle();
        //dashboardDetails();
        searchButton.addClickListener(clickEvent -> {
            if(!cityName.getValue().equals("")){
                try {
                    updateUI();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                Notification.show("Please enter a valid city name");
            }
        });
    }
    private void mainLayout() {
        mainLayout = new VerticalLayout();
        mainLayout.setWidth("100%");
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        setContent(mainLayout);

    }
     private void setHeader(){
        HorizontalLayout header = new HorizontalLayout();
        header.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label title = new Label("AQI Monitoring");
        header.addComponent(title);

        mainLayout.addComponents(header);
    }
    private void setLogo(){
        HorizontalLayout logo = new HorizontalLayout();
        logo.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Image img = new Image("",new ClassResource("/static/logo.png"));
        //logo.setWidth("240 px");
        //logo.setHeight("240px");

        logo.addComponent(img);
        mainLayout.addComponents(logo);

    }
    private void setForm(){
        HorizontalLayout formLayout = new HorizontalLayout();
        formLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        formLayout.setMargin(true);
        formLayout.setSpacing(true);

        //City Name
        cityName = new TextField();
        cityName.setWidth("80%");
        formLayout.addComponent(cityName);

        //Search Button
        searchButton = new Button();
        searchButton.setIcon(VaadinIcons.SEARCH);
        formLayout.addComponent(searchButton);


        mainLayout.addComponents(formLayout);

    }
    private void dashboardTitle(){
        dashboard = new HorizontalLayout();
        dashboard.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        //Nearest Location
        nearestCity = new Label("Enter a nearby city");
        nearestCity.addStyleName(ValoTheme.LABEL_H2);
        nearestCity.addStyleName(ValoTheme.LABEL_LIGHT);

        //Dominant Pollutant
        dominantPollutant = new Label("   ");
        dominantPollutant.addStyleName(ValoTheme.LABEL_SUCCESS);
        dominantPollutant.addStyleName(ValoTheme.LABEL_BOLD);

        //Air Quality Index
        airQualityIndex = new Label("Air Quality Index");
        airQualityIndex.addStyleName(ValoTheme.LABEL_LIGHT);
        airQualityIndex.addStyleName(ValoTheme.LABEL_H2);

        dashboard.addComponents(nearestCity,dominantPollutant,airQualityIndex);

        mainLayout.addComponent(dashboard);
    }
//    private void dashboardDetails(){
//        mainDescription = new HorizontalLayout();
//        mainDescription.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
//
//
//        //description of each pollutant( instant AQI)
//        VerticalLayout instantAQI = new VerticalLayout();
//        //add o3
//
//        COconc = new Label("Major Pollutant");
//        COconc.setStyleName(ValoTheme.LABEL_LIGHT);
//
//        instantAQI.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
//
//
//        //dominant pollutant max value in the week
//        dominantPollutantMax = new Label("PM25");
//        dominantPollutantMax.setStyleName(ValoTheme.LABEL_SUCCESS);
//        //reconsider dominant pollutant
//
//        instantAQI.addComponents(COconc);
//        //instantAQI.addComponents(O3conc,PM10conc,PM25conc,COconc,SO2conc,NO2conc,uviconc);
//
//        mainLayout.addComponent(instantAQI);
//    }

    private void updateUI() throws JSONException {
        String city = cityName.getValue();
        nearestCity.setValue("Please enter a nearby city ");
        //String domPollutant;
        weatherService.setCityName(city);
        //city selection function must be changed
        JSONObject mainObject = weatherService.returnData();
        int aqi = mainObject.getInt("aqi");
        String domPol = mainObject.getString("dominentpol");
        dominantPollutant.setValue(domPol);
        airQualityIndex.setValue(aqi+ " ");
        JSONObject mainObject2  = weatherService.returnNearestStation();
        String currCity = mainObject2.getString("name");
        nearestCity.setValue(currCity);
        //JSONObject mainObject3 = weatherService.returnInstantAQI();
        //JSONObject concCO = mainObject3.getJSONObject(domPol);
        //Double conc_co = concCO.getDouble("v");
        //COconc.setValue(domPol+" - "+conc_co);

    }


}
